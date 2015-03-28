package de.tudarmstadt.linglit.linfw.core.layer;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

public abstract class AbstractLayer<A> implements ReadableLayer<A> {
	private int progress = 0;
	private boolean finished = false;
	private final ConcurrentSkipListMap<Integer, Annotation<A>> annotations = new ConcurrentSkipListMap<>();
	private final Class<A> type;
	
	protected final int addAndNotify(final Annotation<A> annotation) {
		final TextSpan closure = annotation.area().closure();
		this.annotations.put(closure.start(), annotation);
		synchronized (this) {
			notifyAll();
		}

		return closure.end();
	}

	protected final ConcurrentSkipListMap<Integer, Annotation<A>> annotations() {
		return this.annotations;
	}

	@Override
	public Optional<Annotation<A>> annotationAt(final int index) {
		Annotation<A> floor;
		if (this.annotations.containsKey(index))
			floor = this.annotations.get(index);
		else {
			final Entry<Integer, Annotation<A>> entry = this.annotations
					.lowerEntry(index);
			if (entry == null)
				return Optional.absent();
			floor = entry.getValue();
		}
		if (floor == null || !floor.area().covers(index))
			return Optional.absent();

		return Optional.of(floor);
	}

	@Override
	public int progress() {
		return this.progress;
	}

	protected final void setProgress(final int progress) {
		this.progress = progress;
	}

	public void close() {
		synchronized (this) {
			notifyAll();
		}
		this.finished = true;
	}

	@Override
	public boolean finished() {
		return this.finished;
	}
	
	@Override
	public Class<A> type() {
		return this.type;
	}

	public AbstractLayer(Class<A> type) {
		this.type = type;
	}

}
