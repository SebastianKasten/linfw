package de.tudarmstadt.linglit.linfw.linguistics.partofspeech;

import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Aspect;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Case;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Clusivity;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Definiteness;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Degree;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Number;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Tense;

/**
 * The PartOfSpeechBuilder class uses the builder pattern to
 * create new Part-of-Speech objects from their grammatical categories.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 */
public class PartOfSpeechBuilder {
	private static class ComposePartOfSpeech implements PartOfSpeech {
		private final CoarsePartOfSpeech coarseForm;
		private final GrammaticalCategory.Aspect aspect;
		private final GrammaticalCategory.Degree degree;
		private final GrammaticalCategory.Number number;
		private final boolean open;
		private final Case grammaticalCase;
		private final Tense tense;
		private final Definiteness definiteness;
		private final Clusivity clusitivity;
		
		@Override
		public boolean isOpen() {
			return this.open;
		}
		
		@Override
		public CoarsePartOfSpeech coarseForm() {
			return this.coarseForm;
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
		public Number number() {
			return this.number;
		}
		
		public Case grammaticalCase() {
			return this.grammaticalCase;
		}
		
		public Tense tense() {
			return this.tense;
		}
		
		public Definiteness definiteness() {
			return this.definiteness;
		}
		
		public Clusivity clusivity() {
			return this.clusitivity;
		}
		
		ComposePartOfSpeech(CoarsePartOfSpeech coarseForm,
				Aspect aspect, Degree degree, Number number, boolean open, Clusivity clusivity, Definiteness definiteness, Tense tense, Case grammaticalCase) {
			this.coarseForm = coarseForm;
			this.aspect = aspect;
			this.degree = degree;
			this.number = number;
			this.open = open;
			this.grammaticalCase = grammaticalCase;
			this.clusitivity = clusivity;
			this.definiteness = definiteness;
			this.tense = tense;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((this.aspect == null) ? 0 : this.aspect.hashCode());
			result = prime * result
					+ ((this.clusitivity == null) ? 0 : this.clusitivity.hashCode());
			result = prime * result
					+ ((this.coarseForm == null) ? 0 : this.coarseForm.hashCode());
			result = prime * result
					+ ((this.definiteness == null) ? 0 : this.definiteness.hashCode());
			result = prime * result
					+ ((this.degree == null) ? 0 : this.degree.hashCode());
			result = prime
					* result
					+ ((this.grammaticalCase == null) ? 0 : this.grammaticalCase
							.hashCode());
			result = prime * result
					+ ((this.number == null) ? 0 : this.number.hashCode());
			result = prime * result + (this.open ? 1231 : 1237);
			result = prime * result + ((this.tense == null) ? 0 : this.tense.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ComposePartOfSpeech other = (ComposePartOfSpeech) obj;
			if (this.aspect != other.aspect)
				return false;
			if (this.clusitivity != other.clusitivity)
				return false;
			if (this.coarseForm != other.coarseForm)
				return false;
			if (this.definiteness != other.definiteness)
				return false;
			if (this.degree != other.degree)
				return false;
			if (this.grammaticalCase != other.grammaticalCase)
				return false;
			if (this.number != other.number)
				return false;
			if (this.open != other.open)
				return false;
			if (this.tense != other.tense)
				return false;
			return true;
		}		
	}
	
	private CoarsePartOfSpeech coarseForm = CoarsePartOfSpeech.MISC;
	private GrammaticalCategory.Aspect aspect = Aspect.UNKNOWN;
	private GrammaticalCategory.Degree degree = Degree.UNKNOWN;
	private GrammaticalCategory.Number number = GrammaticalCategory.Number.UNKNOWN;
	private boolean open = false;
	private Case grammaticalCase = Case.UNKNOWN;
	private Tense tense = Tense.UNKOWN;
	private Definiteness definiteness = Definiteness.UNKNOWN;
	private Clusivity clusivity = Clusivity.UNKNOWN;
	
	/**
	 * Creates a new part-of-speech object from this builder with the same values
	 * for each grammatical category.
	 * 
	 * @return a new part-of-speech object from this builder
	 */
	public PartOfSpeech build() {
		return new ComposePartOfSpeech(this.coarseForm, 
				this.aspect, this.degree, this.number, this.open, 
				this.clusivity, this.definiteness, this.tense, 
				this.grammaticalCase);
	}
	
	/**
	 * Creates a new builder with all grammatical categories set to
	 * an unknown value.
	 * 
	 * @return a new part-of-speech builder with unknown grammatical categories
	 */
	public static PartOfSpeechBuilder createAllUnknown() {
		return new PartOfSpeechBuilder();
	}
	
	/**
	 * Creates a new builder with the same values of the grammatical categories
	 * as the given part-of-speech.
	 * 
	 * @param pos part-of-speech this builder is based on
	 * @return a builder based on the given part-of-speech
	 */
	public static PartOfSpeechBuilder basedOn(PartOfSpeech pos) {
		PartOfSpeechBuilder builder = new PartOfSpeechBuilder();
		builder.aspect = pos.aspect();
		builder.coarseForm = pos.coarseForm();
		builder.degree = pos.degree();
		builder.number = pos.number();
		builder.open = pos.isOpen();
		return builder;
	}
	
	/**
	 * Returns this builder with a changed aspect value.
	 * 
	 * @param aspect new aspect value
	 * @return this builder
	 */
	public PartOfSpeechBuilder withAspect(Aspect aspect) {
		this.aspect = aspect;
		return this;
	}
	
	/**
	 * Returns this builder with a changed coarse form.
	 * 
	 * @param coarseForm new coarse form value
	 * @return this builder
	 */
	public PartOfSpeechBuilder withCoarseForm(CoarsePartOfSpeech coarseForm) {
		this.coarseForm = coarseForm;
		return this;
	}
	
	/**
	 * Returns this builder with a changed degree value.
	 * 
	 * @param degree new degree value
	 * @return this builder
	 */
	public PartOfSpeechBuilder withDegree(Degree degree) {
		this.degree = degree;
		return this;
	}
	
	/**
	 * Returns this builder with a changed number value.
	 * 
	 * @param number new number value
	 * @return this builder
	 */
	public PartOfSpeechBuilder withNumber(GrammaticalCategory.Number number) {
		this.number = number;
		return this;
	}
	
	/**
	 * Returns this builder with a changed category (open or closed) value.
	 * 
	 * @param open true, if the new value is open; false, for closed
	 * @return this builder
	 */
	public PartOfSpeechBuilder withCategory(boolean open) {
		this.open = open;
		return this;
	}
	
	/**
	 * Returns this builder with a changed grammatical case value.
	 * 
	 * @param grammaticalCase new grammatical case value
	 * @return this builder
	 */
	public PartOfSpeechBuilder withCase(Case grammaticalCase) {
		this.grammaticalCase = grammaticalCase;
		return this;
	}
	
	/**
	 * Returns this builder with a changed tense value.
	 * 
	 * @param tense new tense value
	 * @return this builder
	 */
	public PartOfSpeechBuilder withTense(Tense tense) {
		this.tense = tense;
		return this;
	}
	
	/**
	 * Returns this builder with a changed definiteness value.
	 * 
	 * @param definiteness new definiteness value
	 * @return this builder
	 */
	public PartOfSpeechBuilder withDefiniteness(Definiteness definiteness) {
		this.definiteness = definiteness;
		return this;
	}
	
	/**
	 * Returns this builder with a changed clusivity value.
	 * 
	 * @param clusivity new clusivity value
	 * @return this builder
	 */
	public PartOfSpeechBuilder withClusivity(Clusivity clusivity) {
		this.clusivity = clusivity;
		return this;
	}
	
	private PartOfSpeechBuilder() {}
}
