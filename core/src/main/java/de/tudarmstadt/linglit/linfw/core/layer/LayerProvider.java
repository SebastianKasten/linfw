package de.tudarmstadt.linglit.linfw.core.layer;



/**
 * A class that can provide layers of a given annotation
 * type.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
public interface LayerProvider extends Iterable<ReadableLayer<?>> {
	/**
	 * Checks if this provider has a layer of the given
	 * type.
	 * 
	 * @param type type of the desired layer
	 * @return true, if this provider has a layer with the given type;
	 * false, if not
	 */
	public boolean hasType(Class<?> type);
	
	/**
	 * Returns the layer for the given annotation type.
	 * 
	 * @param type annotation type
	 * @return the layer for this type
	 */
	public <A> ReadableLayer<A> forType(Class<A> type);
}
