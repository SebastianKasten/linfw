package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import de.tudarmstadt.linglit.linfw.app.model.AnnotationHandler;

public class LayerVisualization {
	public static final LayerVisualization EMPTY = new LayerVisualization((AnnotationHandler)null);
	
	private final AnnotationHandler handler;
	private final ImmutableList<Visualization> rows;
	private final Map<String, Visualization> mapping;
	
	public Optional<AnnotationHandler> handler() {
		return Optional.fromNullable(this.handler);
	}
	
	public ImmutableList<Visualization> rows() {
		return this.rows;
	}
	
	public Optional<Visualization> rowFor(final String id) {
		return Optional.fromNullable(this.mapping.get(id));
	}
	
	public LayerVisualization(Visualization... visualizations) {
		this(null,visualizations);
	}
	
	public LayerVisualization(Iterable<Visualization> visualizations) {
		this(null,visualizations);
	}
	
	public LayerVisualization(AnnotationHandler handler, Iterable<? extends Visualization> visualizations) {
		this(handler, Iterables.toArray(visualizations, Visualization.class));
	}
	
	public LayerVisualization(AnnotationHandler handler, Visualization... visualizations) {
		this.handler = handler;
		
		ImmutableList.Builder<Visualization> rowsBuilder = ImmutableList.builder();
		ImmutableMap.Builder<String, Visualization> mappingBuilder = ImmutableMap.builder();
		
		for(Visualization visualization : visualizations) {
			rowsBuilder.add(visualization);
			mappingBuilder.put(visualization.id(), visualization);
		}
		
		this.rows = rowsBuilder.build();
		this.mapping = mappingBuilder.build();
	}
}
