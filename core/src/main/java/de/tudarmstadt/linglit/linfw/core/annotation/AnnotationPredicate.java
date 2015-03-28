package de.tudarmstadt.linglit.linfw.core.annotation;

/**
 * An annotation predicate accepts or rejects certain annotations.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 * @param <A> annotation type
 */
public interface AnnotationPredicate<A> {
	/**
	 * Determines whether this predicate accepts or rejects the
	 * given annotation in the given context.
	 * 
	 * @param text context of the annotation
	 * @param annotation annotation to be checked
	 * @return true, if this predicate accepts the annotation; false if not
	 */
	public boolean accepts(CharSequence text, Annotation<A> annotation);
}
