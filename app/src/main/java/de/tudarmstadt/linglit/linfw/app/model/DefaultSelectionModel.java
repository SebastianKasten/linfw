package de.tudarmstadt.linglit.linfw.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceResource;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceResourceSelectionModel;

public class DefaultSelectionModel implements CorpusSelectionModel,
		WorkspaceResourceSelectionModel {
	private WorkspaceResource selectedResource;
	private Corpus corpus;
	private CorpusDocument document;
	private Class<?> annotationType;
	private final List<ChangeListener> listeners = new ArrayList<>();
	
	public synchronized void addListener(ChangeListener listener) {
		this.listeners.add(listener);
	}
	
	public synchronized void removeListener(ChangeListener listener) {
		this.listeners.remove(listener);
	}	
	
	public void setSelectedWorkspaceNode(WorkspaceNode node) {
		this.selectedResource = node;
		this.corpus = null; // Corpus is not in selection path
		this.document = null; // Document is not in selection path
		this.annotationType = null; // Type is not in selection path
		
		notifyChange();
	}
	
	@Override
	public Optional<WorkspaceResource> selectedResource() {
		return Optional.fromNullable(this.selectedResource);
	}
	
	public void setSelectedCorpusNode(CorpusNode node) {
		this.selectedResource = node;
		this.corpus = node;
		this.document = null; // Document is not in selection path
		this.annotationType = null; // Type is not in selection path
		
		notifyChange();
	}

	@Override
	public Optional<Corpus> selectedCorpus() {
		return Optional.fromNullable(this.corpus);
	}

	
	public void setSelectedDocumentNode(DocumentNode node) {
		this.selectedResource = node;
		this.corpus = node.corpus(); // Select the document's corpus
		this.document = node; 
		this.annotationType = null; // Type is not in selection path
		
		notifyChange();
	}

	@Override
	public Optional<CorpusDocument> selectedDocument() {
		return Optional.fromNullable(this.document);
	}
	
	public void setSelectedLayerNode(LayerNode<?> node) {
		this.selectedResource = node;
		this.corpus = node.getParent().corpus(); // Select the layer's corpus
		this.document = node.getParent(); // ...and document
		this.annotationType = node.layer().type();
		
		notifyChange();
	}
	
	public void setAnnotationType(Class<?> annotationType) {
		this.annotationType = annotationType;
		
		notifyChange();
	}

	@Override
	public Optional<? extends Class<?>> selectedAnnotationType() {
		return Optional.fromNullable(this.annotationType);
	}
	
	private void notifyChange() {
		for(ChangeListener listener : this.listeners)
			listener.stateChanged(new ChangeEvent(this));
	}
	
	
}
