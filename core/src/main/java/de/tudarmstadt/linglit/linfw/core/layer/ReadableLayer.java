package de.tudarmstadt.linglit.linfw.core.layer;

import java.util.Iterator;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;

/**
 * A layer is a collection of consecutive non overlapping annotations
 * of the same type on a text.
 * 
 * @author Sebastian Kasten
 *
 * @param <A> annotation type
 */
public interface ReadableLayer<A>  {
	/**
	 * Returns the annotation at the given index of
	 * the context or <code>Optional.absent()</code>
	 * if this index is not covered by this layer.
	 * 
	 * If the index is out of context, this method will
	 * throw an <code>IllegalArgument</code> exception.
	 * 
	 * @param index index of the annotated character in the
	 * context
	 * @return the annotation at this index or <code>Optional.absent()</code>
	 * if not covered.
	 */
	public Optional<Annotation<A>> annotationAt(int index);

	/**
	 * Returns the index of the last coherent annotation.
	 * 
	 * @return the index of the last coherent annotation.
	 */
	public int progress();
	
	/**
	 * Returns an iterator in sequential order on
	 * the annotations on this layer. It is guaranteed
	 * that once an annotation on a certain text area
	 * appears as the next item, there will be no following
	 * annotation on a text area before that.
	 * 
	 * @return an iterator in sequential order
	 */
	public Iterator<Annotation<A>> sequential();
	
	/**
	 * Returns an iterator in arbitrary order on
	 * the annotations on this layer.
	 * 
	 * @return an iterator in arbitrary order
	 */
	public Iterator<Annotation<A>> arbitrary();
	
	public boolean finished();
	
	public Class<A> type();
}
