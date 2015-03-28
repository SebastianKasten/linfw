package de.tudarmstadt.linglit.linfw.app.plugin;
import java.util.Set;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.features.FeatureGenerator;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualizer;


public interface Plugin {
	public String id();
	public String name();
	public Optional<String> description();
	public Set<Plugin> dependencies();
	
	public ClassLoader classLoader();
	public Set<AnnotatorSupplier> annotatorSuppliers();
	public Set<Visualizer<?>> visualizers();
	public Set<? extends FeatureGenerator<?>> featureGenerators();
}
