package de.tudarmstadt.linglit.linfw.app.model;

import de.tudarmstadt.linglit.linfw.app.model.workspace.Annotable;


/**
 * A corpus document is a document inside a corpus
 * with a content. It also is a layer manager and allows
 * access to the annotation layers of the document.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
public interface CorpusDocument extends Named, LayerManager, Annotable {
	/**
	 * Returns the corpus this document is included.
	 * 
	 * @return the corpus of this document
	 */
	public Corpus corpus();
	
	/**
	 * Returns the content of this document as text.
	 * 
	 * @return the content of this document
	 */
	public CharSequence text();
}
