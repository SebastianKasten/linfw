package de.tudarmstadt.linglit.linfw.core.layer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.text.TextAreas;

public class AlignedLayer<A, R> implements ReadableLayer<A> {
	private final Class<A> type;
	private final DefaultAbitraryLayer<A> base;
	private final List<Annotation<R>> alignments = new ArrayList<Annotation<R>>();
	private final Set<Integer> finishedSublayers = new ConcurrentSkipListSet<>();
	private int lastFinished = -1;
	private boolean preparedFinish = false;

	public void prepareFinish() {
		this.preparedFinish = true;
		if(AlignedLayer.this.lastFinished == alignments.size()-1) {
			synchronized (finishedSublayers) {
				AlignedLayer.this.finish();	
			}
		}

	}

	public void finish() {
		this.base.close();
	}

	@Override
	public boolean finished() {
		return this.base.finished();
	}

	@Override
	public Optional<Annotation<A>> annotationAt(int index) {
		return this.base.annotationAt(index);
	}

	@Override
	public int progress() {
		if(this.lastFinished>-1)
			return AlignedLayer.this.alignments.get(AlignedLayer.this.lastFinished).area().closure().end();
		return 0;
	}

	@Override
	public Iterator<Annotation<A>> arbitrary() {
		return this.base.arbitrary();
	}

	private class Sublayer implements WritableLayer<A> {
		private final Annotation<R> alignment;
		private final int index;

		@Override
		public void write(Annotation<A> annotation) {
			if(TextAreas.inside(annotation.area(),this.alignment.area()))
				AlignedLayer.this.base.write(annotation);
		}

		@Override
		public void close() {
			AlignedLayer.this.finishedSublayers.add(this.index);
			if(this.index == AlignedLayer.this.lastFinished + 1) {
				synchronized(AlignedLayer.this.finishedSublayers) {
					if(this.index == AlignedLayer.this.lastFinished + 1) {
						while(AlignedLayer.this.finishedSublayers.contains(AlignedLayer.this.lastFinished + 1) 
								&& AlignedLayer.this.lastFinished < AlignedLayer.this.alignments.size() - 1)
							AlignedLayer.this.lastFinished++;
						AlignedLayer.this.finishedSublayers.notifyAll();

						if(AlignedLayer.this.preparedFinish && AlignedLayer.this.lastFinished == alignments.size()-1)
						{
							AlignedLayer.this.finish();
						}
					}
				}
			}
		}

		public Sublayer(Annotation<R> alignment, int index) {
			super();
			this.alignment = alignment;
			this.index = index;
		}
	}

	public Sublayer align(Annotation<R> annotation) {
		int lastIndex;
		synchronized(this.alignments) {
			this.alignments.add(annotation);
			lastIndex = this.alignments.size()-1;
		}
		return new Sublayer(annotation, lastIndex);
	}

	private class SequentialIterator implements Iterator<Annotation<A>> {
		private int progress = 0;

		public void waiting() {
			synchronized (AlignedLayer.this.finishedSublayers) {
				if(!AlignedLayer.this.finished() && this.progress >= AlignedLayer.this.progress())
					try {
						AlignedLayer.this.finishedSublayers.wait();
					} catch (InterruptedException e) {
						// So what?
					}
			}
		}

		@Override
		public boolean hasNext() {
			while(true) {
				waiting();
				if(this.progress < AlignedLayer.this.progress() && AlignedLayer.this.base.annotations().ceilingEntry(this.progress) != null) 
					return true;
				if(AlignedLayer.this.finished()) return false;
			}
		}

		@Override
		public Annotation<A> next() {
			while(true) {
				waiting();
				if(this.progress<AlignedLayer.this.progress()) {
					final Entry<Integer, Annotation<A>> entry = AlignedLayer.this.base.annotations().ceilingEntry(this.progress);
					if(entry==null) throw new NoSuchElementException();
					final Annotation<A> value = entry.getValue();
					this.progress = value.area().closure().end()+1;
					return value;
				}

				if(AlignedLayer.this.finished()) throw new NoSuchElementException();
			}
		}
	}

	@Override
	public Iterator<Annotation<A>> sequential() {
		return new SequentialIterator();
	}

	@Override
	public Class<A> type() {
		return this.type;
	}

	public AlignedLayer(Class<A> type) {
		this.type = type;
		this.base = new DefaultAbitraryLayer<>(this.type);
	}
}
