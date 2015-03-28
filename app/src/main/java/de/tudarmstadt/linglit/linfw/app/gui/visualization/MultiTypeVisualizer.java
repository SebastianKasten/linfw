package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tudarmstadt.linglit.linfw.app.MainController;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class MultiTypeVisualizer  {
	private final Map<Class<?>, Visualizer<?>> factories;
	
	private <A> Visualizer<A> get(Class<A> type) {
		return (Visualizer<A>) this.factories.get(type);
	}
	
	public <A> List<Visualization> createFor(Annotation<A> annotation) {
		List<Visualization> result = new ArrayList<Visualization>();
		Class<A> requiredClass = annotation.type();
		for(Entry<Class<?>, Visualizer<?>> entry : this.factories.entrySet()) {
			Class<?> foundClass = entry.getKey();
			if(foundClass.isAssignableFrom(requiredClass)) {
				Visualizer<A> factory = (Visualizer<A>) entry.getValue();
				result.addAll(factory.visualize(annotation));
			}
		}
		return result;
	}

	public <A> List<LayerVisualization> createFor(Class<A> type) {
		List<LayerVisualization> result = new ArrayList<LayerVisualization>();
		for(Entry<Class<?>, Visualizer<?>> entry : this.factories.entrySet()) {
			Class<?> foundClass = entry.getKey();
			if(foundClass.isAssignableFrom(type)) {
				Visualizer<A> factory = (Visualizer<A>) entry.getValue();
				result.add(factory.visualizeLayer());
			}
		}
		return result;
	}

	public MultiTypeVisualizer(Map<Class<?>, Visualizer<?>> factories) {
		super();
		this.factories = factories;
	}

}
