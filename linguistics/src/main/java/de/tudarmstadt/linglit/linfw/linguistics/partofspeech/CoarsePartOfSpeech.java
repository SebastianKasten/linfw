package de.tudarmstadt.linglit.linfw.linguistics.partofspeech;

import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Aspect;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Degree;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Number;

/**
 * Enum containing a very coarse basic part-of-speech classification
 * that only consists of the part-of-speeches that exist in almost
 * every language.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 */
public enum CoarsePartOfSpeech implements PartOfSpeech {
	/** Lexical or named noun or pronoun **/
	NOUN(Aspect.NONE, Degree.NONE, Number.UNKNOWN, true),
	/** Verb **/
	VERB(Aspect.UNKNOWN, Degree.NONE, Number.UNKNOWN, true),
	/** Adjective **/
	ADJECTIVE(Aspect.NONE, Degree.UNKNOWN, Number.UNKNOWN, true),
	/** Adverb **/
	ADVERB(Aspect.NONE, Degree.UNKNOWN, Number.NONE, true),
	/** Conjunction, Interjection, ... */
	MISC(Aspect.UNKNOWN, Degree.UNKNOWN, Number.UNKNOWN, false),
	/** Punctuation like ".,;-" **/
	PUNCTUATION(Aspect.NONE, Degree.NONE, Number.NONE, false)
	;

	private final GrammaticalCategory.Aspect aspect;
	private final GrammaticalCategory.Degree degree;
	private final GrammaticalCategory.Number number;
	private final boolean open;

	@Override
	public CoarsePartOfSpeech coarseForm() {
		return this;
	}
	
	@Override
	public Aspect aspect() {
		return this.aspect;
	}
	
	@Override
	public Degree degree() {
		return this.degree;			
	}
	
	@Override
	public GrammaticalCategory.Number number() {
		return this.number;
	}
	
	@Override
	public boolean isOpen() {
		return this.open;
	}
	
	CoarsePartOfSpeech(final Aspect aspect,
			final Degree degree,
			final GrammaticalCategory.Number number,
			final boolean open) {
		this.aspect = aspect;
		this.degree = degree;
		this.number = number;
		this.open = open;
	}
}