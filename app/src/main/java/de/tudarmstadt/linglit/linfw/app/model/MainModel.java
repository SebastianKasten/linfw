package de.tudarmstadt.linglit.linfw.app.model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.annotation.Metadata;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.LayerVisualization;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.MetadataVisualizer;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.MultiTypeVisualizer;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualization;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualizer;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceTreeModel;
import de.tudarmstadt.linglit.linfw.app.model.workspace.directory.DirectoryCorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.directory.DirectoryWorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.directory.FileDocumentNode;
import de.tudarmstadt.linglit.linfw.app.plugin.AnnotatorSupplier;
import de.tudarmstadt.linglit.linfw.app.plugin.BuiltIn;
import de.tudarmstadt.linglit.linfw.app.plugin.Plugin;
import de.tudarmstadt.linglit.linfw.app.plugin.PluginManager;
import de.tudarmstadt.linglit.linfw.app.plugin.Plugins;
import de.tudarmstadt.linglit.linfw.app.plugin.XMLPluginManager;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;
import de.tudarmstadt.linglit.linfw.core.annotator.ForwardingAnnotatorExecutor;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class MainModel {
	private static final LayerProgressModel PROGRESSMODEL = new LayerProgressModel();
	private static final AnnotatorExecutor EXECUTOR;
	static {
		AnnotatorExecutor executor = new ForwardingAnnotatorExecutor();
		EXECUTOR = executor;
	}
	
	private final PluginManager pluginManager;
	private final DirectoryWorkspaceNode workspace;

	private DirectoryCorpusNode corpus;
	private FileDocumentNode document;
	private LayerManager layerManager;
	private ReadableLayer<?> layer;
	private Class<?> annotationType;
	private Annotation<?> annotation;
	private DocumentModel styledDocument;
	private AnnotatorSupplier activatedAnnotatorSupplier;
	private AnnotatorSupplier deactivatedAnnotatorSupplier;
	private Plugin plugin;
	private List<?> selectedAnnotationValues;

	private final WorkspaceTreeModel hierarchyModel;
	private final MultiTypeVisualizer visualizer;

	public void clearSelectedAnnotationValues() {
		this.selectedAnnotationValues = null;
	}

	public void setSelectedAnnotationValues(List<?> selectedAnnotationValues) {
		this.selectedAnnotationValues = selectedAnnotationValues;
	}

	public Optional<? extends List<?>> selectedAnnotationValues() {
		return Optional.fromNullable(selectedAnnotationValues);
	}

	public LayerProgressModel progressModel() {
		return PROGRESSMODEL;
	}

	public Optional<Plugin> plugin() {
		return Optional.fromNullable(this.plugin);
	}

	public void setPlugin(final Plugin plugin) {
		this.plugin = plugin;
	}

	public void clearPlugin() {
		this.plugin = null;
	}

	public Optional<AnnotatorSupplier> activatedAnnotatorSupplier() {
		return Optional.fromNullable(this.activatedAnnotatorSupplier);
	}

	public void setActivatedAnnotatorSupplier(final AnnotatorSupplier annotatorSupplier) {
		this.activatedAnnotatorSupplier = annotatorSupplier;
	}

	public void clearDeactivatedAnnotatorSupplier() {
		this.activatedAnnotatorSupplier = null;
	}

	public Optional<AnnotatorSupplier> deactivatedAnnotatorSupplier() {
		return Optional.fromNullable(this.activatedAnnotatorSupplier);
	}

	public void setDeactivatedAnnotatorSupplier(final AnnotatorSupplier annotatorSupplier) {
		this.activatedAnnotatorSupplier = annotatorSupplier;
	}

	public void clearActivatedAnnotatorSupplier() {
		this.activatedAnnotatorSupplier = null;
	}

	public Optional<FileDocumentNode> document() {
		return Optional.fromNullable(this.document);
	}

	public AnnotatorExecutor executor() {
		return EXECUTOR;
	}

	public de.tudarmstadt.linglit.linfw.app.plugin.PluginManager pluginManager() {
		return pluginManager;
	}
	
	public void clearCorpus() {
		this.corpus = null;
	}

	public void setCorpus(final DirectoryCorpusNode corpus) {
		this.corpus = corpus;
	}

	public Optional<DirectoryCorpusNode> corpus() {
		return Optional.fromNullable(this.corpus);
	}

	public DirectoryWorkspaceNode workspace() {
		return workspace;
	}

	public void setDocument(final FileDocumentNode document) {
		clearLayer();
		if(document != this.document) {
			this.document = document;
			this.styledDocument = new DocumentModel(document.text());
		}
	}

	public void setLayerManager(final LayerManager layerManager) {
		this.layerManager = layerManager;
	}

	public Optional<LayerManager> layerManager() {
		return Optional.fromNullable(this.layerManager);
	}

	public void clearLayerManager() {
		this.layerManager = null;
	}

	public void clearDocument() {
		this.document = null;
	}

	public Optional<DocumentModel> styledDocument() {
		return Optional.fromNullable(this.styledDocument);
	}

	public Optional<? extends ReadableLayer<?>> layer() {
		return Optional.fromNullable(this.layer);
	}

	public void setLayer(final ReadableLayer<?> layer) {
		if(layer != this.layer) {
			setAnnotationType(layer.type());
			this.layer = layer;
			if(this.styledDocument != null) 
				this.styledDocument.clearHighlights();
		}
	}

	public void setAnnotationType(Class<?> type) {
		this.annotationType = type;
	}
	
	public void clearAnnotationType() {
		this.annotationType = null;
	}
	
	public Optional<? extends Class<?>> annotationType() {
		return Optional.fromNullable(this.annotationType);
	}

	public WorkspaceTreeModel hierarchy() {
		return this.hierarchyModel;
	}

	public void clearLayer() {
		this.progressModel().setValue(0);
		this.layer = null;
		if(this.styledDocument != null)
			this.styledDocument.clearHighlights();
	}

	public Optional<? extends Annotation<?>> annotation() {
		return Optional.fromNullable(this.annotation);
	}

	private int caretLine = -1;
	private int caretColumn = -1;
	private AnnotationTreeModel annotationTreeModel;
	public void setPosition(int position) {
		this.caretLine =  this.styledDocument.getDefaultRootElement().getElementIndex(position);
		this.caretColumn = position - this.styledDocument.getDefaultRootElement().getElement(caretLine()).getStartOffset();
		if(layer().isPresent())
			this.annotation = layer().get().annotationAt(position).orNull();
		else
			this.annotation = null;
	}


	public int caretLine() {
		return this.caretLine;
	}

	public int caretColumn() {
		return this.caretColumn;
	}

	public void setAnnotation(final Annotation<?> annotation) {
		this.annotation = annotation;
	}

	public void clearAnnotation() {
		this.annotation = null;
	}

	public List<LayerVisualization> layerVisualizations() {
		if(this.annotationType!=null)
			return this.visualizer.createFor(this.annotationType);
		return Collections.emptyList();
	}

	public List<Visualization> annotationVisualizations() {
		if(this.annotation!=null)
			return this.visualizer.createFor(this.annotation);
		return Collections.emptyList();
	}

	public Optional<AnnotationTreeModel> annotationTreeModel() {
		return Optional.fromNullable(this.annotationTreeModel);
	}


	public void setAnnotationTreeModel(AnnotationTreeModel annotationTreeModel) {
		this.annotationTreeModel = annotationTreeModel;
	}

	public MainModel() {
		DirectoryWorkspaceNode workspace = null;
		PluginManager manager = null;
		try {
			manager = new XMLPluginManager(Paths.get("plugins"), EXECUTOR, new BuiltIn(this));
			workspace = new DirectoryWorkspaceNode(Paths.get("workspace"), manager.classLoader());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IOException | SAXException e) {
			e.printStackTrace();
		}
		this.pluginManager = manager;
		this.workspace = workspace;
		Map<Class<?>,Visualizer<?>> visualizers = new HashMap<>();
		visualizers.put(Metadata.class, new MetadataVisualizer());
		for(Plugin plugin : pluginManager().plugins())
			for(Visualizer<?> visualizer : plugin.visualizers()) {
				visualizers.put(visualizer.type(), visualizer);
				System.out.println(Plugins.pluginOf(visualizer.getClass()));
			}

		
		this.visualizer = new MultiTypeVisualizer(visualizers);
		this.hierarchyModel = new WorkspaceTreeModel(workspace);
	}
}
