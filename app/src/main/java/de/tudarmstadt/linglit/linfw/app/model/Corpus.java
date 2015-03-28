package de.tudarmstadt.linglit.linfw.app.model;


import java.util.List;

import de.tudarmstadt.linglit.linfw.app.annotator.AnnotatorManager;
import de.tudarmstadt.linglit.linfw.app.model.workspace.Annotable;

/**
 * A corpus is a named collection of documents and 
 * annotators.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
public interface Corpus extends Named, Annotable {
	/**
	 * Returns a list of documents in this corpus.
	 * 
	 * @return a list of documents
	 */
	public List<? extends CorpusDocument> documents();
	
	/**
	 * Returns the annotator manager of this corpus.
	 * 
	 * @return the annotator manager
	 */
	public AnnotatorManager annotators();
	
	/**
	 * Annotate this corpus using its own annotators.
	 * 
	 * @return true, if the annotation was successful; false,
	 * if not
	 */
	public boolean annotate();
}
