package de.tudarmstadt.linglit.linfw.app.model;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

/**
 * This layer provider blocks as long as there is no suitable
 * layer for a certain type available.
 * 
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 */
public class BlockingLayerManager implements LayerManager {
	private final Map<Class<?>, ReadableLayer<?>> layers =
			new ConcurrentHashMap<>();

	/** {@inheritDoc}  */
	@Override
	public <A> Optional<ReadableLayer<A>> addLayer(ReadableLayer<A> layer) {
		synchronized(this.layers) {
			@SuppressWarnings("unchecked")
			ReadableLayer<A> result = (ReadableLayer<A>)this.layers.put(layer.type(), layer);
			this.layers.notifyAll();
			return Optional.fromNullable(result);
		}
	}

	/** {@inheritDoc}
	 * 
	 * <p><b>Note:</b> This method will block until a layer
	 * of the given type is available. It never returns <code>null</code></p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> ReadableLayer<A> forType(Class<A> type) {
		synchronized(this.layers) {
			if(this.layers.containsKey(type)) 
				return (ReadableLayer<A>)this.layers.get(type);
			try {
				this.layers.wait();
				return forType(type);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p><b>Note:</b> This implementation returns an
	 * iterator on a immutable copy of the set of layers.</p>
	 */
	@Override
	public Iterator<ReadableLayer<?>> iterator() {
		return ImmutableSet.copyOf(this.layers.values()).iterator();
	}

	/** {@inheritDoc}
	 * 
	 * <p><b>Note:</b> This method will <i>not</i> block.
	 */
	@Override
	public boolean hasType(Class<?> type) {
		return this.layers.containsKey(type);
	}

	@Override
	public <A> Optional<ReadableLayer<A>> removeForType(Class<A> type) {
		if(this.hasType(type)) {
			@SuppressWarnings("unchecked")
			final ReadableLayer<A> removed = (ReadableLayer<A>)this.layers.remove(type);
			return Optional.of(removed);
			
		}
		return Optional.absent();
	}
}
