package de.tudarmstadt.linglit.linfw.app.features;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

public class ExplicitFeatureGeneratorProvider implements
		FeatureGeneratorProvider {
	private Multimap<Class<?>, FeatureGenerator<?>> data = HashMultimap.create();
	
	public <T> void put(Class<T> type, FeatureGenerator<? super T> generator) {
		this.data.put(type, generator);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> Set<FeatureGenerator<? super T>> get(Class<T> type) {
		ImmutableSet.Builder<FeatureGenerator<? super T>> builder = ImmutableSet.builder();
		final Collection<FeatureGenerator<?>> generators = this.data.get(type);
		for(FeatureGenerator<?> generator : generators)
			builder.add((FeatureGenerator<? super T>) generator);
		
		return builder.build();
	}
	
	public Set<Class<?>> supportedTypes() {
		return this.data.keySet();
	}

	@Override
	public Set<FeatureGenerator<?>> all() {
		return ImmutableSet.copyOf(this.data.values());
	}

	@Override
	public String toString() {
		return "ExplicitFeatureGeneratorProvider [data=" + data + "]";
	}
}
