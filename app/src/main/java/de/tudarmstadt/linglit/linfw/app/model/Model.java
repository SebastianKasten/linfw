package de.tudarmstadt.linglit.linfw.app.model;

import javax.swing.event.ChangeListener;

import com.google.common.base.Optional;

public interface Model {
	/**
	 * Returns the current corpus of this model.
	 * 
	 * @return the current corpus or <code>Optional.absent()</code>
	 * if it is not set
	 */
	public Optional<Corpus> corpus();
	
	/**
	 * Sets the model corpus to the given corpus.
	 * 
	 * @param corpus corpus to be set
	 */
	public void setCorpus(Corpus corpus);
	
	/**
	 * Clears the model corpus.
	 */
	public void clearCorpus();
	
	/**
	 * Returns the current document of this model.
	 * 
	 * @return the current corpus or <code>Optional.absent()</code>
	 * if it is not set
	 */
	public Optional<CorpusDocument> document();
	
	/**
	 * Adds a change listener to this model. It is always called
	 * when the state of this model changes.
	 * 
	 * @param listener change listener
	 */
	public void addChangeListener(ChangeListener listener);
}
