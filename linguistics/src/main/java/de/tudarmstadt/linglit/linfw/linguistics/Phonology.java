package de.tudarmstadt.linglit.linfw.linguistics;

import java.util.EnumSet;
import java.util.Set;

import de.tudarmstadt.linglit.linfw.linguistics.Phonetics.Consonant;
import de.tudarmstadt.linglit.linfw.linguistics.Phonetics.Phone;

public class Phonology {

	public interface Phoneme {
		public boolean isConsonantal();
		public boolean isSonorant();
		public boolean isSyllabic();
		public boolean isVoice();
		public boolean isSpread_glottis();
		public boolean isConstricted_glottis();
		public boolean isContinuant();
		public boolean isNasal();
		public boolean isStrident();
		public boolean isLateral();
		public boolean isDelayed_release();
		public Set<? extends Phone> realizations();
	}
	
	public static enum EnglishPhoneme implements Phoneme {
		;
		private final boolean consonantal;
		private final boolean sonorant;
		private final boolean syllabic;
		
		private final boolean voice;
		private final boolean spread_glottis;
		private final boolean constricted_glottis;
		
		private final boolean continuant;
		private final boolean nasal;
		private final boolean strident;
		private final boolean lateral;
		private final boolean delayed_release;
		
		private final EnumSet<Consonant> realizations_cons;
		
		private EnglishPhoneme(boolean consonantal, boolean sonorant,
				boolean syllabic, boolean voice, boolean spread_glottis,
				boolean constricted_glottis, boolean continuant, boolean nasal,
				boolean strident, boolean lateral, boolean delayed_release,
				EnumSet<Consonant> realizations_cons) {
			this.consonantal = consonantal;
			this.sonorant = sonorant;
			this.syllabic = syllabic;
			this.voice = voice;
			this.spread_glottis = spread_glottis;
			this.constricted_glottis = constricted_glottis;
			this.continuant = continuant;
			this.nasal = nasal;
			this.strident = strident;
			this.lateral = lateral;
			this.delayed_release = delayed_release;
			this.realizations_cons = realizations_cons;
		}

		@Override
		public boolean isConsonantal() {
			return consonantal;
		}

		@Override
		public boolean isSonorant() {
			return sonorant;
		}

		@Override
		public boolean isSyllabic() {
			return syllabic;
		}

		@Override
		public boolean isVoice() {
			return voice;
		}

		@Override
		public boolean isSpread_glottis() {
			return spread_glottis;
		}

		@Override
		public boolean isConstricted_glottis() {
			return constricted_glottis;
		}

		@Override
		public boolean isContinuant() {
			return continuant;
		}

		@Override
		public boolean isNasal() {
			return nasal;
		}

		@Override
		public boolean isStrident() {
			return strident;
		}

		@Override
		public boolean isLateral() {
			return lateral;
		}

		@Override
		public boolean isDelayed_release() {
			return delayed_release;
		}

		@Override
		public Set<? extends Phone> realizations() {
			return realizations_cons;
		}
	}
}
