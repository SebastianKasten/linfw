package de.tudarmstadt.linglit.linfw.linguistics;

public class Phonetics {
	public static enum Manner {
		PLOSIVE,
		FRICATIVE,
		AFFRICATIVE,
		NASAL,
		CENTRAL_APPROXIMANT,
		LATERAL,
		ROLL,
		FLAP,
		TRILL,
		SEMIVOWEL,
		EJECTIVE,
		IMPLOSIVE,
		CLICK
	}
	
	public static enum Place {
		BILABIAL,
		LABIODENTAL,
		DENTAL,
		ALVEOLAR,
		POSTALVEOLAR,
		PALATOALVEOLAR,
		RETROFLEX,
		PALATAL,
		VELAR,
		UVULAR,
		PHARYNGEAL,
		EPIGLOTTAL,
		GLOTTAL
	}
	
	public static interface Phone {};
	
	public static enum Consonant implements Phone {
		BILABIAL_NASAL("m","m","m",Manner.NASAL,Place.BILABIAL,true),
		VOICELESS_BILABIAL_PLOSIVE("p","p","p",Manner.PLOSIVE,Place.BILABIAL,false),
		VOICED_BILABIAL_PLOSIVE("b","b","b",Manner.PLOSIVE,Place.BILABIAL,true),
		VOICELESS_BILABIAL_FRICATIVE("ɸ","p\\","P",Manner.FRICATIVE,Place.BILABIAL,false),
		VOICED_BILABIAL_FRICATIVE("β","B","B",Manner.FRICATIVE,Place.BILABIAL,true),
		BILABIAL_TRILL("ʙ","B\\","b<trl>",Manner.TRILL,Place.BILABIAL,true),
		BILABIAL_FLAP("ⱱ̟",null,null,Manner.FLAP,Place.BILABIAL,true),
		LABIODENTAL_NASAL("ɱ","F","M",Manner.NASAL,Place.LABIODENTAL,true),
		VOICELESS_LABIODENTAL_PLOSIVE("p̪","p_d","p[",Manner.PLOSIVE,Place.LABIODENTAL,false),
		VOICED_LABIODENTAL_PLOSIVE("b̪","b_d","b[",Manner.PLOSIVE,Place.LABIODENTAL,true),
		VOICELESS_LABIODENTAL_FRICATIVE("f","f","f",Manner.FRICATIVE,Place.LABIODENTAL,false),
		VOICED_LABIODENTAL_FRICATIVE("v","v","v",Manner.FRICATIVE,Place.LABIODENTAL,true),
		LABIODENTAL_APPROXIMANT("ʋ","v\\","r<lbd>",Manner.CENTRAL_APPROXIMANT,Place.LABIODENTAL,true),
		LABIODENTAL_FLAP("ⱱ",null,null,Manner.FLAP,Place.LABIODENTAL,true),
		DENTAL_NASAL("n̪","n_d","n[",Manner.NASAL,Place.DENTAL,true),
		ALVEOLAR_NASAL("n","n","n",Manner.NASAL,Place.ALVEOLAR,true),
		
		;
		
		private Consonant(String ipa, String xsampa, String kirshenbaum,
				Manner manner, Place place, boolean voiced) {
			this.ipa = ipa;
			this.xsampa = xsampa;
			this.kirshenbaum = kirshenbaum;
			this.manner = manner;
			this.place = place;
			this.voiced = voiced;
		}
		private final String ipa;
		private final String xsampa;
		private final String kirshenbaum;
		
		private final Manner manner;
		private final Place place;
		private final boolean voiced;
	}
}
