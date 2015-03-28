package de.tudarmstadt.linglit.linfw.app.annotator;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Multimap;

import de.tudarmstadt.linglit.linfw.app.annotation.FeatureVector;
import de.tudarmstadt.linglit.linfw.app.annotation.Token;
import de.tudarmstadt.linglit.linfw.app.features.DefaultFeatureGenerator;
import de.tudarmstadt.linglit.linfw.app.features.ExplicitFeatureGeneratorProvider;
import de.tudarmstadt.linglit.linfw.app.features.FeatureGenerator;
import de.tudarmstadt.linglit.linfw.app.features.FeatureGeneratorMapping;
import de.tudarmstadt.linglit.linfw.app.features.FeatureGeneratorProvider;
import de.tudarmstadt.linglit.linfw.core.Consumer;
import de.tudarmstadt.linglit.linfw.core.Producer;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;
import de.tudarmstadt.linglit.linfw.core.annotator.SerialAnnotator;
import de.tudarmstadt.linglit.linfw.core.layer.AppendableLayer;
import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

@Producer(type=FeatureVector.class)
public class FeatureVectorAnnotator extends
SerialAnnotator<FeatureVector> {
	private ExplicitFeatureGeneratorProvider provider;
	
	private <T> FeatureVector generate(Class<T> type, LayerProvider layerprovider, FeatureVector vector) {
		FeatureVector result = vector;
		Set<FeatureGenerator<? super T>> generators = this.provider.get(type);

		for(FeatureGenerator<? super T> generator : generators) {
			ReadableLayer<T> layer = layerprovider.forType(type);

			Iterator<? extends Annotation<T>> iterator = layer.sequential();

			while(iterator.hasNext()) 
				result = result.plus(generator.generate("", iterator.next().value())); //$NON-NLS-1$
		}

		return result;
	}

	@Override
	protected void process(CharSequence text, LayerProvider layerprovider,
			AppendableLayer<FeatureVector> layer) {
		FeatureVector vector = FeatureVector.empty();
		for(Class<?> type : this.provider.supportedTypes())
			vector = generate(type, layerprovider, vector);

		layer.appendAnnotation(new Annotation<>(TextSpan.allOf(text), vector,FeatureVector.class));
	}


	public FeatureVectorAnnotator(AnnotatorExecutor executor, ExplicitFeatureGeneratorProvider provider) {
		super(executor, FeatureVector.class);
		this.provider = provider;
	}

}
