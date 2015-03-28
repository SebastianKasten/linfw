package de.tudarmstadt.linglit.linfw.app.features;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

public class FeatureGeneratorMapping {
	private Multimap<Class<?>, FeatureGenerator<?>> data = HashMultimap.create();
	
	public <T> void put(Class<T> type, FeatureGenerator<? super T> generator) {
		this.data.put(type, generator);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Set<FeatureGenerator<T>> get(Class<? extends T> type) {
		ImmutableSet.Builder<FeatureGenerator<T>> builder = ImmutableSet.builder();
		final Collection<FeatureGenerator<?>> generators = this.data.get(type);
		for(FeatureGenerator<?> generator : generators)
			builder.add((FeatureGenerator<T>) generator);
		
		return builder.build();
	}
	
	public Set<Class<?>> types() {
		return this.data.keySet();
	}
	
	public int numGenerators() {
		return this.data.values().size();
	}
}
