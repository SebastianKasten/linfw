package de.tudarmstadt.linglit.linfw.app.model;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

/**
 * A layer manager holds the layers of a document. It
 * implements the layer provider interface and therefore
 * allows access to layers of a given type. It is mutable
 * and allows the addition and removal of layers.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
public interface LayerManager extends LayerProvider {	
	/**
	 * Adds a layer to the manager and returns the previous layer
	 * of this type if present.
	 * 
	 * @param layer layer to be added
	 * @return the previous layer or <code>Optional.absent()</code> if not existent
	 */
	public <A> Optional<ReadableLayer<A>> addLayer(ReadableLayer<A> layer);
	
	/**
	 * Removes the layer of the given type from the
	 * manager.
	 * 
	 * @param type type of the layer
	 * @return the layer that was removed or <code>Optional.absent()</code> if no layer of that type existed
	 */
	public <A> Optional<ReadableLayer<A>> removeForType(Class<A> type);
	
}
