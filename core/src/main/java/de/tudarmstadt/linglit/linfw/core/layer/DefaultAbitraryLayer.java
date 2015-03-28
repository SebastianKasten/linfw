package de.tudarmstadt.linglit.linfw.core.layer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.Ordering;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;

public class DefaultAbitraryLayer<A> extends AbstractLayer<A> implements WritableLayer<A> {
	private class IteratorImpl implements Iterator<Annotation<A>> {
		private int index = 0;
		private final List<Annotation<A>> annotations;

		@Override
		public boolean hasNext() {
			if(index<this.annotations.size())
				return true;

			if(DefaultAbitraryLayer.this.finished())
				return false;

			try {
				synchronized(DefaultAbitraryLayer.this) {
					DefaultAbitraryLayer.this.wait();
				}
				return hasNext();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public Annotation<A> next() {
			synchronized(this.annotations) {
				if(index<this.annotations.size())
					return this.annotations.get(index++);
			}
			if(DefaultAbitraryLayer.this.finished())
				throw new NoSuchElementException();

			try {
				synchronized(DefaultAbitraryLayer.this) {
					DefaultAbitraryLayer.this.wait();
				}
				return next();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		public IteratorImpl(final List<Annotation<A>> annotations) {
			this.annotations = annotations;
		}
	}

	private final List<Annotation<A>> annotations =  
			new ArrayList<Annotation<A>>();
	private List<Annotation<A>> sortedAnnotations = null;
	private Object finishedLock = new Object();

	@Override
	public Iterator<Annotation<A>> sequential() {
		if(this.finished())
			return new IteratorImpl(this.sortedAnnotations);
		else {
			synchronized(this.finishedLock) {
				if(this.finished())
					return new IteratorImpl(this.sortedAnnotations);

				try {
					while(!this.finished())
						this.finishedLock.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				return new IteratorImpl(this.sortedAnnotations);
			}
		}
	}

	@Override
	public Iterator<Annotation<A>> arbitrary() {
		return new IteratorImpl(this.annotations);
	}

	@Override
	public void write(Annotation<A> annotation) {
		synchronized(this.annotations) {
			this.annotations.add(annotation);
		}
		addAndNotify(annotation);
	}

	private final static Ordering<Annotation<?>> startingOrdering = Ordering.from(new Comparator<Annotation<?>>() {
		@Override
		public int compare(Annotation<?> o1, Annotation<?> o2) {
			return o1.area().closure().start()-o2.area().closure().start();
		}
	});

	@Override
	public void close() {
		super.close();
		this.sortedAnnotations = startingOrdering.sortedCopy(this.annotations);

		synchronized(this.finishedLock) {
			this.finishedLock.notifyAll();
		}
	}

	public DefaultAbitraryLayer(Class<A> type) {
		super(type);
	}

}
