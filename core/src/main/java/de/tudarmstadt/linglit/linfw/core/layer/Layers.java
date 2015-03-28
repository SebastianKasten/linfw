package de.tudarmstadt.linglit.linfw.core.layer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;

public class Layers {
	public static <A> Iterator<List<Annotation<A>>> ngramIterator(final ReadableLayer<A> layer, final int n) {
		final Iterator<? extends Annotation<A>> iterator = layer.sequential();
		
		return new Iterator<List<Annotation<A>>>() {
			private final LinkedList<Annotation<A>> buffer = new LinkedList<>();
			
			private void fillBuffer() {
				while(iterator.hasNext() && this.buffer.size()<n)
					this.buffer.addLast(iterator.next());
			}
			
			@Override
			public boolean hasNext() {
				return this.buffer.size()==n;
			}

			@Override
			public List<Annotation<A>> next() {
				List<Annotation<A>> result = Lists.newArrayList(this.buffer);
				this.buffer.removeFirst();
				fillBuffer();
				return result;
			}

			{
				// Constructor
				fillBuffer();
			}
		};
	}
	
	public static <A> Iterator<List<Annotation<A>>> maxngramIterator(final ReadableLayer<A> layer, final int maxN) {
		List<Iterator<List<Annotation<A>>>> iterators = new ArrayList<Iterator<List<Annotation<A>>>>();
		for(int n=1; n<=maxN; n++)
			iterators.add(ngramIterator(layer, n));
		
		final Iterator<Iterator<List<Annotation<A>>>> cycle = Iterators.cycle(iterators);
		
		return new Iterator<List<Annotation<A>>>() {
			private Iterator<List<Annotation<A>>> next = cycle.next();
			
			@Override
			public boolean hasNext() {
				return this.next.hasNext();
			}

			@Override
			public List<Annotation<A>> next() {
				List<Annotation<A>> result = this.next.next();
				this.next = cycle.next();
				
				return result;
			}
		};
	}
	
	public static <A> Iterator<Annotation<A>> sequentialBetween(ReadableLayer<A> layer, final int start, final int end) {
		final PeekingIterator<Annotation<A>> iterator =
				Iterators.peekingIterator(
						Iterators.filter(layer.sequential(), new Predicate<Annotation<A>>() {
							@Override
							public boolean apply(Annotation<A> input) {
								return input.area().closure().start() >= start;
							}
						}));

		return new Iterator<Annotation<A>>() {

			@Override
			public boolean hasNext() {
				if(!iterator.hasNext())
					return false;
				else {
					Annotation<A> annotation = iterator.peek();
					if(annotation.area().closure().start() > end)
						return false;
				}
				
				return true;
			}

			@Override
			public Annotation<A> next() {
				if(hasNext()) return iterator.next();
				throw new NoSuchElementException();
			}

		};
	}

	private Layers() {}
}
