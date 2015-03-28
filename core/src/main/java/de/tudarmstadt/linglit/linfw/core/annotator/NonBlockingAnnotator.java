package de.tudarmstadt.linglit.linfw.core.annotator;

/**
 * <p>A non-blocking annotator processes the annotation through
 * a given executor.</p>
 * 
 * <p><b>Note:</b> Every implementation of this class must guarantee that calling the <code>process</code>
 * will not block the current thread.</p>
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 * @param <A> Produced annotation type
 */
public abstract class NonBlockingAnnotator<A> implements Annotator<A> {
	private AnnotatorExecutor executor;
	private final Class<A> type;

	/**
	 * Returns the executor this annotator processes on
	 * @return the executor this annotator processes on
	 */
	public final AnnotatorExecutor executor() {
		return this.executor;
	}

	@Override
	public Class<A> type() {
		return this.type;
	}

	/**
	 * Creates a new non-blocking annotator with the given executor.
	 * 
	 * @param executor executor this annotator processes on
	 * @param type type of the annotations this annotator creates
	 */
	public NonBlockingAnnotator(final AnnotatorExecutor executor, final Class<A> type) {
		this.executor = executor;
		this.type = type;
	}

}