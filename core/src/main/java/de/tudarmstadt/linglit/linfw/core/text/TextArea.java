package de.tudarmstadt.linglit.linfw.core.text;


/**
 * <p>
 * A text area defines a coverage of characters in a certain context.
 * 
 * I.e. the number of characters covered by the excerpt must
 * be equal or less the number of its context excerpt.
 * </p><p>
 * Its natural order is defined by its position in the context.
 * A excerpt <i>e<sub>1</sub></i> is considered smaller than another 
 * excerpt <i>e<sub>2</sub></i> if <i>e<sub>1</sub></i> starts before 
 * <i>e<sub>2</sub></i>. If both excerpts have the same start position
 * the excerpt with the smaller ending position is considered smaller.
 * Finally, if starting and ending position is equal, the length decides
 * which excerpt is smaller.
 * </p>
 * 
 * @author Sebastian Kasten
 */
public interface TextArea {
	/**
	 * Puts the area in a certain context and returns the resulting
	 * character sequence. The context must be sufficiently large so
	 * that the text area can cover portions of it.
	 * 
	 * <p>E.g. the context of an excerpt "dog cat" could be "the <b>dog</b>
	 * chases the <b>cat</b>"</p>
	 * 
	 * @return the character sequence that this area covers in the given
	 * context.
	 */

	/**
	 * Returns the context of this text area.
	 * 
	 * @return the context of this text area
	 */
	public CharSequence context();
	
	/**
	 * Returns the part of the context covered by this area.
	 * 
	 * @return the covered text
	 */
	public CharSequence coveredText();
	
	/**
	 * Checks if part of the excerpt appears at a given position in
	 * a context. 
	 * 
	 * <p>E.g. for the context "the <b>dog</b> chases the <b>cat</b>" the 
	 * excerpt "dog cat" covers the context at position 4 or 20 but not at
	 * position 8.</p>
	 * 
	 * @param index position to be checked
	 * @return true, if the excerpt appears at the position; false, if not
	 */
	public boolean covers(int index);
	
	/**
	 * Returns the smallest text span (coherent excerpt) that covers the 
	 * complete excerpt.
	 * 
	 * <p>E.g. for the excerpt "dog cat" in the context "the <b>dog</b> chases
	 * the <b>cat</b>" the closure would be "dog chases the dog"</p>
	 * 
	 * @return the smallest text span that covers the complete excerpt
	 */
	public TextSpan closure();
	
	/**
	 * Returns the length of this text area, i.e. the number of characters
	 * it covers in any context.
	 * 
	 * @return the length of the covered text
	 */
	public int length();
	
	/**
	 * Returns a smaller text area inside this text area. It starts at
	 * a given start index and ends at a given end index.
	 * 
	 * <p>E.g. for for the excerpt "dog cat" in the context "the <b>dog</b> chases
	 * the <b>cat</b>" <code>subArea(2,5)</code> would yield "g c" in the same
	 * context.
	 * 
	 * @param start start index of the sub area
	 * @param end end index of the sub area
	 * @return the sub area of this area 
	 */
	public TextArea subArea(int start, int end);
}
