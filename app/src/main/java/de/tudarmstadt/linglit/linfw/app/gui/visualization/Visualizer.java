package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.List;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

/**
 * The visualizer creates Java Swing components to visualize the properties of certain
 * annotations.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 * @param <A>
 */
public interface Visualizer<A> {
	/**
	 * Creates a visualization for one single annotation on a given text.
	 * 
	 * @param annotation annotation to visualize
	 * @return a visualization for that annotation
	 */
	public List<Visualization> visualize(Annotation<? extends A> annotation);
	
	/**
	 * Creates a visualization for a whole layer of annotations on a given text.
	 * 
	 * @return a visualization for that layer
	 */
	public LayerVisualization visualizeLayer();
	
	/**
	 * Returns the type of the annotations this visualizer can
	 * visualize.
	 * 
	 * @return type of annotations to visualize
	 */
	public Class<A> type();
}
