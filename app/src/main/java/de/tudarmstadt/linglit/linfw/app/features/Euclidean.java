package de.tudarmstadt.linglit.linfw.app.features;

import java.util.Set;

import com.google.common.collect.Sets;

import de.tudarmstadt.linglit.linfw.app.annotation.FeatureVector;

public class Euclidean implements DistanceFunction<FeatureVector> {

	@Override
	public double distance(FeatureVector from, FeatureVector to) {
		Set<Object> features = Sets.union(from.features(), to.features());
		
		double sum = 0.0d;
		for(Object feature : features) {
			double value = from.value(feature) - to.value(feature);
			sum += value * value;
		}
		
		return Math.sqrt(sum);
	}

}
