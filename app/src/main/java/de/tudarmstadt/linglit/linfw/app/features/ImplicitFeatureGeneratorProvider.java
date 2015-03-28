package de.tudarmstadt.linglit.linfw.app.features;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class ImplicitFeatureGeneratorProvider implements
		FeatureGeneratorProvider {
	private final List<FeatureGenerator<?>> generators =
			Lists.newArrayList();
	
	public void put(FeatureGenerator<?> generator) {
		this.generators.add(generator);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Set<FeatureGenerator<? super T>> get(Class<T> type) {
		ImmutableSet.Builder<FeatureGenerator<? super T>> builder = 
				ImmutableSet.builder();
		
		for(FeatureGenerator<?> generator : this.generators)
			if(generator.type().isAssignableFrom(type))
				builder.add((FeatureGenerator<? super T>) generator);
		
		return builder.build();
	}

	@Override
	public Set<FeatureGenerator<?>> all() {
		return ImmutableSet.copyOf(this.generators);
	}	
}
