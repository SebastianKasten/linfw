package de.tudarmstadt.linglit.linfw.app.model;

import javax.swing.event.ChangeListener;

import com.google.common.base.Optional;

public interface CorpusSelectionModel {
	public Optional<Corpus> selectedCorpus();
	public Optional<CorpusDocument> selectedDocument();
	public Optional<? extends Class<?>> selectedAnnotationType();
	
	public void addListener(ChangeListener listener);
}
