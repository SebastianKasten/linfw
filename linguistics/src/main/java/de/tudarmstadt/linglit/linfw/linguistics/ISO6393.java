package de.tudarmstadt.linglit.linfw.linguistics;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.linguistics.Languages.Language;

/**
 * The instances of this class are part of the ISO639-3 standard.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
public class ISO6393 implements Language {
	/**
	 * The scope of an ISO 639-3 language as defined in that standard.
	 * 
	 * See: {@link "http://www.sil.org/iso639-3/scope.asp"}
	 */
	public static enum Scope {
		/**
		 * An individual atomic language.
		 */
		INDIVIDUAL,
		/**
		 * In defining some of its language codes, some are defined as
		 * macrolanguages covering either borderline cases between strongly
		 * divergent dialects and very closely related languages (dialect
		 * continuums), or speech varieties that are considered to be either the
		 * same or different languages for ethnic or political rather than
		 * linguistic reasons. There are fifty-six languages in ISO 639-2 that
		 * are considered to be macrolanguages in ISO 639-3.
		 */
		MACROLANGUAGE,
		/**
		 * Languages that are not existent or not applicable.
		 */
		SPECIAL
	}

	/**
	 * The type of an ISO 639-3 language as defined in that standard.
	 * 
	 * See: {@link "http://www.sil.org/iso639-3/types.asp"}
	 */
	public static enum Type {
		/**
		 * A language is listed as living when there are people still living who
		 * learned it as a first language.
		 */
		LIVING,
		/**
		 * A language is listed as extinct if it has gone
		 * extinct in recent times. (e.g. in the last few centuries). The
		 * criteria for identifying distinct languages in these case are based
		 * on intelligibility (as defined for individual languages).
		 */
		EXTINCT,
		/**
		 * A language is listed as ancient if it went extinct in ancient times
		 * (e.g. more than a millennium ago). In the case of ancient languages,
		 * a criterion based on intelligibility would be ideal, but in the final
		 * analysis, identifiers are assigned to ancient languages which have a
		 * distinct literature and are treated distinctly by the scholarly
		 * community. In order to qualify for inclusion in ISO 639-3, the
		 * language must have an attested literature or be well-documented as a
		 * language known to have been spoken by some particular community at
		 * some point in history; it may not be a reconstructed language
		 * inferred from historical-comparative analysis.
		 */
		ANCIENT,
		/**
		 * A language is listed as historic when it is considered to be distinct
		 * from any modern languages that are descended from it; for instance,
		 * Old English and Middle English. Here, too, the criterion is that the
		 * language have a literature that is treated distinctly by the
		 * scholarly community.
		 */
		HISTORIC,
		/**
		 * This part of ISO 639 also includes identifiers that denote
		 * constructed (or artificial) languages. In order to qualify for
		 * inclusion the language must have a literature and it must be designed
		 * for the purpose of human communication. Specifically excluded are
		 * reconstructed languages and computer programming languages.
		 */
		CONSTRUCTED
	}

	private final String code;
	private final Scope scope;
	private final Type type;
	private final Optional<String> threeLetterCodeNative;
	private final Optional<ISO6391> iso6391equivalent;
	private final String englishName;

	/**
	 * Returns the ISO 639-1 equivalent of this language if existent.
	 * 
	 * @return a ISO6391 representation of this language or Optional.absent() if
	 *         non-existent.
	 */
	public Optional<ISO6391> iso6391Equivalent() {
		return this.iso6391equivalent;
	}

	/** {@inheritDoc} */
	@Override
	public String iso6393code() {
		return this.code;
	}

	/**
	 * Returns the ISO639-3 code for this language.
	 * 
	 * @return the ISO639-3 code
	 */
	public String code() {
		return this.code;
	}

	/**
	 * Returns the scope of this language.
	 * 
	 * @return the scope of this language
	 */
	public Scope scope() {
		return this.scope;
	}

	/**
	 * Returns the type of this language.
	 * 
	 * @return the type of this language
	 */
	public Type type() {
		return this.type;
	}

	/**
	 * Returns the three letter code derived from the native
	 * name of this language.
	 * 
	 * @return the three letter code in the native language
	 */
	public Optional<String> threeLetterCodeNative() {
		return this.threeLetterCodeNative;
	}

	/**
	 * Returns the language family this language is part of.
	 * 
	 * @return the language family
	 */
	public Optional<ISO6395> family() {
		if(this.iso6391equivalent.isPresent())
			return Optional.of(this.iso6391equivalent.get().family());
		
		return Optional.absent();
	}

	/**
	 * Returns the English name of this language.
	 * 
	 * @return the English name of this language
	 */
	public String englishName() {
		return this.englishName;
	}

	ISO6393(final String code, final Scope scope, final Type type,
			final Optional<String> threeLetterCodeNative,
			final Optional<ISO6391> iso6391equivalent,
			final String englishName) {
		this.code = code;
		this.scope = scope;
		this.type = type;
		this.threeLetterCodeNative = threeLetterCodeNative;
		this.iso6391equivalent = iso6391equivalent;
		this.englishName = englishName;
	}

	/**
	 * Parses a line of the tab-separated file provided by SIL.
	 * 
	 * @param line
	 *            SIL file line
	 * @param mapping
	 *            Corresponding line from the family mapping file by SIL
	 * @return the language as ISO6393 object
	 */
	static ISO6393 parse(final String line, final Optional<String> mapping) {
		final String[] lineTokens = line.split("\t"); //$NON-NLS-1$

		final String code = lineTokens[0];
		Optional<String> threeLetterCodeNative;
		if (lineTokens[1].isEmpty())
			threeLetterCodeNative = Optional.absent();
		else
			threeLetterCodeNative = Optional.of(lineTokens[1]);

		Optional<ISO6391> iso6391equivalent;
		if (lineTokens[3].isEmpty())
			iso6391equivalent = Optional.absent();
		else
			iso6391equivalent = Languages.forISO6391Code(lineTokens[3]);

		final Scope scope;
		switch(lineTokens[4].charAt(0)) {
		case 'I': scope=Scope.INDIVIDUAL; break;
		case 'M': scope=Scope.MACROLANGUAGE; break;
		default: scope=Scope.SPECIAL;
		}
		
		final Type type;
		switch(lineTokens[5].charAt(0)) {
		case 'L': type = Type.LIVING;break;
		case 'E':type=Type.EXTINCT;break;
		case 'A':type=Type.ANCIENT;break;
		case 'H':type=Type.HISTORIC;break;
		default: type=Type.CONSTRUCTED;
		}
		
		final String englishName = lineTokens[6];
		return new ISO6393(code, scope,type,threeLetterCodeNative, iso6391equivalent, englishName);
	}

	@Override
	public String toString() {
		return "ISO6393 [code=" + this.code + ", scope=" + this.scope + ", type=" + this.type //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ ", threeLetterCodeNative=" + this.threeLetterCodeNative //$NON-NLS-1$
				+ ", iso6391equivalent=" + this.iso6391equivalent + ", family=" //$NON-NLS-1$ //$NON-NLS-2$
				+ family() + ", englishName=" + this.englishName + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Creates an ISO639-3 language for the given generic language.
	 * 
	 * @param language generic language
	 * @return the ISO639-3 equivalent of the given language
	 * @throws IllegalArgumentException throws an illegal argument exception if the ISO639-3 code of the language is invalid
	 */
	public static ISO6393 fromLanguage(Language language) throws IllegalArgumentException {
		if(language instanceof ISO6393) return (ISO6393)language;
		Optional<ISO6393> result = Languages.forISO6393Code(language.iso6393code());
		if(result.isPresent())
		return result.get();
		throw new IllegalArgumentException(language.iso6393code()+" is not a valid ISO639-3 code"); //$NON-NLS-1$
	}
}