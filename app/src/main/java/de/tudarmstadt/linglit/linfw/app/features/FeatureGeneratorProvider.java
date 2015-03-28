package de.tudarmstadt.linglit.linfw.app.features;

import java.util.Set;

/**
 * This provider creates feature generator objects that
 * can generate feature vectors from given types.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 */
public interface FeatureGeneratorProvider {
	/**
	 * Creates a set of feature generators for the given type.
	 * 
	 * @param type type the generator can generates features for
	 * @return a set of feature generators for the given type
	 */
	public <T> Set<FeatureGenerator<? super T>> get(Class<T> type);
	
	/**
	 * Returns a set of all feature generators this provider contains.
	 * 
	 * @return a set of all feature generators
	 */
	public Set<FeatureGenerator<?>> all();
}
