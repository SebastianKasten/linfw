package de.tudarmstadt.linglit.linfw.app.features;

import de.tudarmstadt.linglit.linfw.app.annotation.FeatureVector;

public interface FeatureGenerator<T> {
	public FeatureVector generate(String namespace, T object);
	public Class<T> type();
}
