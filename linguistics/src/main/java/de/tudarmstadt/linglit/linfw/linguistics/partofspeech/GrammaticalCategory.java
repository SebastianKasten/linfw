package de.tudarmstadt.linglit.linfw.linguistics.partofspeech;

/**
 * This class holds enums of all kinds of grammatical
 * categories.
 * 
 * @author Sebastian Kasten
 */
public class GrammaticalCategory {
	public enum Clusivity  {
		INCLUSIVE,
		EXCLUSIVE,
		UNKNOWN,
		NONE
	}
	
	public enum Definiteness {
		DEFINITE,
		INDEFINITE,
		CONSTRUCT,
		UNKNOWN,
		NONE
	}
	
	public enum Case {
		ADESSIVE,
		APUDESSIVE,
		INESSIVE,
		INTRATIVE,
		LOCATIVE,
		PERTINGENT,
		SUBESSIVE,
		SUPERESSIVE,
		ABLATIVE,
		DELATIVE,
		EGRESSIVE,
		ELATIVE,
		INITIATIVE,
		ALLATIVE,
		ILLATIVE,
		LATIVE,
		SUBLATIVE,
		TERMINATIVE,
		PERLATIVE,
		PROLATIVE,
		PROSECUTIVE,
		VIALIS,
		TEMPORAL,
		ACCUSATIVE,
		ESSIVE,
		ABSOLUTIVE,
		ERGATIVE,
		ERGATIVE_GENITIVE,
		INSTRUMENTAL,
		INSTRUMENTAL_COMINATIVE,
		NOMINATIVE,
		OBJECTIVE,
		OBLIQUE,
		INTRANSITIVE,
		PEGATIVE,
		AVERSIVE,
		BENEFACTIVE,
		CAUSAL,
		CAUSAL_FINAL,
		COMINATIVE,
		DATIVE,
		DISTRIBUTIVE,
		DISTRIBUTIVE_TEMPORAL,
		GENITIVE,
		ORNATIVE,
		POSSESSED,
		POSSESSIVE,
		PRIVATIVE,
		SEMBLATIVE,
		SOCIATIVE,
		PARTIVE,
		PREPOSITIONAL,
		VOCATIVE,
		ABESSIVE,
		ADVERBIAL,
		COMPARATIVE,
		EQUATIVE,
		ESSIVE_FORMAL,
		ESSIVE_MODAL,
		EXESSIVE,
		FORMAL,
		IDENTICAL,
		ORIENTATIVE,
		REVERTIVE,
		TRANSLATIVE,
		
		UNKNOWN,
		NONE
	}
	
	public enum Tense {
		REMOTE_PAST,
		PAST,
		NON_PAST,
		PRESENT,
		FUTURE,
		REMOTE_FUTURE,
		
		UNKOWN,
		NONE
	}
	
	/**
	 * This enum contains the aspects of a verb.
	 * 
	 * @author Sebastian
	 */
	public enum Aspect {
		/** Not progressive and not perfect aspect, e.g. "I eat" */
		NON_PROGRESSIVE_NON_PERFECT,
		/** Progressive and not perfect aspect, e.g. "I am eating" */
		PROGRESSIVE_NON_PERFECT,
		/** Not progressive and perfect aspect, e.g. "I have gone" */
		NON_PROGRESSIVE_PERFECT,
		/** Progressive and perfect aspect, e.g. "I have been going" */
		PROGRESSIVE_PERFECT,
		/** Progressive aspect, e.g. "I am swimming" */
		PROGRESSIVE,
		/** Not progressive aspect, e.g. "She went" */
		NON_PROGRESSIVE,
		/** Perfect aspect, e.g. "He has ordered" */
		PERFECT,
		/** Not perfect aspect, e.g. "We will become" */
		NON_PERFECT,
		/** Unknown aspect */
		UNKNOWN,
		/** No aspect for this lexical category */
		NONE
	}
	
	/**
	 * This enum contains the degree of an adjective or adverb.
	 * 
	 * @author Sebastian Kasten
	 */
	public enum Degree {
		/** Positive, e.g. good */
		POSITIVE,
		/** Comparative, e.g. better */
		COMPARATIVE,
		/** Superlative, e.g. best */
		SUPERLATIVE,
		/** Unknown degree */
		UNKNOWN,
		/** No degree for this lexical category */
		NONE
	}
	
	/**
	 * This enum contains all kinds of grammatical numbers.
	 * 
	 * @author Sebastian Kasten
	 */
	public enum Number {
		/** Singular number, e.g. the tree */
		SINGULAR,
		/** Plural number, e.g. the mice */
		PLURAL,
		/** Dual number, used to reference two instances */
		DUAL,
		/** Trial number, used to reference three instances */
		TRIAL,
		/** Quadral number, used to reference four instances */
		QUADRAL,
		/** Paucal number, used to reference a few instances */
		PAUCAL,
		/** Distributive number, used for many independent instances */
		DISTRIBUTIVE,
		/** Unknown number */
		UNKNOWN,
		/** No number for this lexical category */
		NONE
	}
}
