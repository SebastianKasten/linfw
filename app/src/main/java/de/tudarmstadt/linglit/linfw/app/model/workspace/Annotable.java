package de.tudarmstadt.linglit.linfw.app.model.workspace;

import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

/**
 * Classes implementing this interface are able to receive
 * an annotator. It may change the state of the object after
 * the receipt.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 * 
 */
public interface Annotable {
	/**
	 * Annotates this annotable.
	 * 
	 * @param annotator annotator to annotate this annotable
	 */
	public void annotate(Annotator<?> annotator);
}
