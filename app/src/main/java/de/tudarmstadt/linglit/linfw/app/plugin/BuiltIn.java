package de.tudarmstadt.linglit.linfw.app.plugin;

import java.util.Collections;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.annotator.CharacterAnnotator;
import de.tudarmstadt.linglit.linfw.app.annotator.FeatureVectorAnnotator;
import de.tudarmstadt.linglit.linfw.app.annotator.FeatureVectorAnnotatorFactory;
import de.tudarmstadt.linglit.linfw.app.annotator.ManualLanguageAnnotator;
import de.tudarmstadt.linglit.linfw.app.annotator.NonPunctuationWordAnnotator;
import de.tudarmstadt.linglit.linfw.app.annotator.TikaLanguageAnnotator;
import de.tudarmstadt.linglit.linfw.app.features.DefaultFeatureGenerator;
import de.tudarmstadt.linglit.linfw.app.features.FeatureGenerator;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.CharacterVisualizer;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.DefaultVisualizer;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.FeatureVectorVisualizer;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.LanguageVisualizer;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.PartOfSpeechVisualizer;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualizer;
import de.tudarmstadt.linglit.linfw.app.model.MainModel;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;

public final class BuiltIn implements Plugin {
	private final AnnotatorExecutor executor;
	private final MainModel model;
	
	@Override
	public String id() {
		return "builtin"; //$NON-NLS-1$
	}

	@Override
	public String name() {
		return Messages.getString("BuiltIn.0"); //$NON-NLS-1$
	}

	@Override
	public Optional<String> description() {
		return Optional.of(Messages.getString("BuiltIn.1")); //$NON-NLS-1$
	}

	@Override
	public Set<Plugin> dependencies() {
		return Collections.emptySet();
	}

	@Override
	public ClassLoader classLoader() {
		return ClassLoader.getSystemClassLoader();
	}

	@Override
	public Set<AnnotatorSupplier> annotatorSuppliers() {
		ImmutableSet.Builder<AnnotatorSupplier> result = ImmutableSet.builder();
		
		result.add(DefaultAnnotatorFactory.fromConstructor(CharacterAnnotator.class, Messages.getString("BuiltIn.2"), this.executor)); //$NON-NLS-1$
		result.add(DefaultAnnotatorFactory.fromConstructor(TikaLanguageAnnotator.class, Messages.getString("BuiltIn.3"), this.executor));  //$NON-NLS-1$
		result.add(DefaultAnnotatorFactory.fromConstructor(ManualLanguageAnnotator.class, Messages.getString("BuiltIn.4"), this.executor)); //$NON-NLS-1$
		result.add(DefaultAnnotatorFactory.fromConstructor(NonPunctuationWordAnnotator.class, Messages.getString("BuiltIn.5"), this.executor)); //$NON-NLS-1$
		result.add(new FeatureVectorAnnotatorFactory(this.model));
		return result.build();
	}

	@Override
	public Set<Visualizer<?>> visualizers() {
		ImmutableSet.Builder<Visualizer<?>> result = ImmutableSet.builder();
		
		result.add(new CharacterVisualizer());
		result.add(new DefaultVisualizer());
		result.add(new PartOfSpeechVisualizer());
		result.add(new LanguageVisualizer());
		result.add(new FeatureVectorVisualizer());
		
		return result.build();
	}
	
	@Override
	public Set<? extends FeatureGenerator<?>> featureGenerators() {
		return Collections.singleton(DefaultFeatureGenerator.instance());
	}

	public BuiltIn(MainModel model) {
		this.executor = model.executor();
		this.model = model;
	}

}
