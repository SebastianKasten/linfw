package de.tudarmstadt.linglit.linfw.core.layer;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import com.google.common.base.Preconditions;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

public class DefaultSequentialLayer<A> extends AbstractLayer<A> implements
ReadableLayer<A>, AppendableLayer<A> {
	protected class BlockingIterator implements Iterator<Annotation<A>> {
		private int index = -1;
		
		@Override
		public boolean hasNext() {
			Entry<Integer,Annotation<A>> ceiling = DefaultSequentialLayer.this.annotations().higherEntry(index);
			if(ceiling == null) {
				if(DefaultSequentialLayer.this.finished()) return false;
				synchronized(DefaultSequentialLayer.this) {
					try {
						DefaultSequentialLayer.this.wait();
						return hasNext();
					} catch (InterruptedException e) {
					}
				}
			}
			
			return true;
		}

		@Override
		public Annotation<A> next() {
			if(!hasNext()) throw new NoSuchElementException();
			Annotation<A> ceiling = DefaultSequentialLayer.this.annotations().higherEntry(index).getValue();
			index = ceiling.area().closure().end();
			return ceiling;
		}
	}

	/** 
	 * {@inheritDoc}
	 * 
	 * <b>Note:</b> This implementation blocks as long as the next annotation
	 * is not available yet but will be in future.
	 */
	@Override
	public Iterator<Annotation<A>> sequential() {
		return new BlockingIterator();
	}
	
	@Override
	public Iterator<Annotation<A>> arbitrary() {
		return new BlockingIterator();
	}

	@Override
	public int appendAnnotation(Annotation<A> annotation) {
		final TextSpan closure = annotation.area().closure();
		Preconditions.checkArgument(closure.start()>=progress());
		
		int progress = addAndNotify(annotation);
		setProgress(progress);
		return progress;
	}
	public DefaultSequentialLayer(Class<A> type) {
		super(type);
	}
}
