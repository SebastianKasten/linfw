package de.tudarmstadt.linglit.linfw.linguistics.partofspeech;

import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Aspect;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Degree;



/**
 * This class provides enums that contain different parts-of-speech.
 * 
 * @author Sebastian Kasten
 */
public interface PartOfSpeech {
	/**
	 * Checks if this part-of-speech belongs is an open
	 * word class.
	 * 
	 * @return true, if part-of-speech is open; false, if not
	 */
	public boolean isOpen();
	
	/**
	 * Returns the coarse form of this PennTreebank style part-of-speech.
	 * 
	 * @return the coarse form of this part-of-speech
	 */
	public CoarsePartOfSpeech coarseForm();
	
	/**
	 * Returns the aspect for this part-of-speech,
	 * <code>Aspect.NONE</code>, if the part-of-speech
	 * is not a verb, or <code>Aspect.UNKNOWN</code>, if
	 * a categorization is imposible.
	 * 
	 * @return the aspect of this part-of-speech
	 */
	public Aspect aspect();
	
	/**
	 * Returns the degree for this part-of-speech,
	 * <code>Aspect.NONE</code>, if the part-of-speech
	 * is not a verb, or <code>Aspect.UNKNOWN</code>, if
	 * a categorization is imposible.
	 * 
	 * @return the degree of this part-of-speech
	 */
	public Degree degree();
	
	/**
	 * Returns the grammatical number of this part-of-speech.
	 * 
	 * @return the grammatical number of this part-of-speech
	 */
	public de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Number number();
	
}
