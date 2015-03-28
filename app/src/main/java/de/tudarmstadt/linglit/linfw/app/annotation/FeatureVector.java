package de.tudarmstadt.linglit.linfw.app.annotation;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;

public class FeatureVector {
	private final static FeatureVector empty = new FeatureVector(Collections.<Object,Double>emptyMap());
	private final Map<Object, Double> data;
	private final double length;
	
	public Set<Object> features() {
		return this.data.keySet();
	}
	
	public double value(Object feature) {
		if(this.data.containsKey(feature)) 
			return this.data.get(feature).doubleValue();
		
		return 0.0d;
	}
	
	public FeatureVector plus(FeatureVector featureVector) {
		ImmutableMap.Builder<Object, Double> builder = ImmutableMap.builder();
		Set<Object> features = Sets.union(features(), featureVector.features());
		for(Object feature : features)
			builder.put(feature, Double.valueOf(value(feature)+featureVector.value(feature)));
			
		return new FeatureVector(builder.build());
	}
	
	public double length() {
		return this.length;
	}
	
	public FeatureVector normalize() {
		ImmutableMap.Builder<Object, Double> builder = ImmutableMap.builder();
		for(Object feature : features())
			if(value(feature)!=0.0)
				builder.put(feature, Double.valueOf(value(feature)/this.length));
		return new FeatureVector(builder.build());
	}
	
	public static FeatureVector fromMap(Map<? extends Object, Double> map) {
		if(map instanceof SortedMap) 
			return new FeatureVector(ImmutableSortedMap.copyOf(map));
		
		return new FeatureVector(ImmutableMap.copyOf(map));
	}
	
	public static FeatureVector empty() {
		return empty;
	}
	

	private FeatureVector(Map<Object, Double> data) {
		this.data = data;
		double length = 0.0d;
		for(Double value : data.values())
			length += value;
		this.length = length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeatureVector other = (FeatureVector) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FeatureVector [data=" + data + "]";
	}
}
