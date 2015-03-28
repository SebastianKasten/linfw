package de.tudarmstadt.linglit.linfw.linguistics;

import com.google.common.base.Optional;

public enum ISO6395 {
	AUSTRO_ASIATIC(Messages.getString("ISO6395.aav"), "aav", "aav"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	AFRO_ASIATIC(Messages.getString("ISO6395.afa"), "afa", "afa"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ALGONQUIAN(Messages.getString("ISO6395.alg"), "nai:aql:alg", "alg"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ATLANTIC_CONGO(Messages.getString("ISO6395.alv"), "nic:alv", "alv"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	APACHE(Messages.getString("ISO6395.apa"), "nai:xnd:ath:apa", "apa"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ALACALUFAN(Messages.getString("ISO6395.aqa"), "sai:aqa", "aqa"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ALGIC(Messages.getString("ISO6395.aql"), "nai:aql", "aql"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ARTIFICIAL(Messages.getString("ISO6395.art"), "art", "art"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ATHAPASCAN(Messages.getString("ISO6395.ath"), "nai:xnd:ath", "ath"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ARAUAN(Messages.getString("ISO6395.auf"), "sai:awd:auf", "auf"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	AUSTRALIAN(Messages.getString("ISO6395.aus"), "aus", "aus"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ARAWAKAN(Messages.getString("ISO6395.awd"), "sai:awd", "awd"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	UTO_AZTECAN(Messages.getString("ISO6395.azc"), "nai:azc", "azc"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	BANDA(Messages.getString("ISO6395.bad"), "nic:alv:bad", "bad"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	BAMILEKE(Messages.getString("ISO6395.bai"), "nic:alv:bai", "bai"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	BALTIC(Messages.getString("ISO6395.bat"), "ine:bat", "bat"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	BERBER(Messages.getString("ISO6395.ber"), "afa:ber", "ber"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	BANTU(Messages.getString("ISO6395.bnt"), "nic:alv:bnt", "bnt"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	BATAK(Messages.getString("ISO6395.btk"), "map:poz:pqw:btk", "btk"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CENTRAL_AMERICAN_INDIAN(Messages.getString("ISO6395.cai"), "cai", "cai"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CAUCASIAN(Messages.getString("ISO6395.cau"), "cau", "cau"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CHIBCHAN(Messages.getString("ISO6395.cba"), "sai:cba", "cba"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	NORTH_CAUCASIAN(Messages.getString("ISO6395.ccn"), "cau:ccn", "ccn"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SOUTH_CAUCASIAN(Messages.getString("ISO6395.ccs"), "cau:ccs", "ccs"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CHADIC(Messages.getString("ISO6395.cdc"), "afa:cdc", "cdc"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CADDOAN(Messages.getString("ISO6395.cdd"), "nai:cdd", "cdd"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CELTIC(Messages.getString("ISO6395.cel"), "ine:cel", "cel"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CHAMIC(Messages.getString("ISO6395.cmc"), "map:poz:pqw:cmc", "cmc"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CREOLES_AND_PIDGINS_ENGLISH_BASED(
			Messages.getString("ISO6395.cpe"), "crp:cpe", "cpe"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CREOLES_AND_PIDGINS_FRENCH_BASED(
			Messages.getString("ISO6395.cpf"), "crp:cpf", "cpf"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CREOLES_AND_PIDGINS_PORTUGUESE_BASED(
			Messages.getString("ISO6395.cpp"), "crp:cpp", "cpp"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CREOLES_AND_PIDGINS(Messages.getString("ISO6395.crp"), "crp", "crp"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CENTRAL_SUDANIC(Messages.getString("ISO6395.csu"), "ssa:csu", "csu"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CUSHITIC(Messages.getString("ISO6395.cus"), "afa:cus", "cus"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	LAND_DAYAK(Messages.getString("ISO6395.day"), "day", "day"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	MANDE(Messages.getString("ISO6395.dmn"), "nic:dmn", "dmn"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	DRAVIDIAN(Messages.getString("ISO6395.dra"), "dra", "dra"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	EGYPTIAN(Messages.getString("ISO6395.egx"), "afa:egx", "egx"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ESKIMO_ALEUT(Messages.getString("ISO6395.esx"), "esx", "esx"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	BASQUE(Messages.getString("ISO6395.euq"), "euq", "euq"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	FINNO_UGRIAN(Messages.getString("ISO6395.fiu"), "urj:fiu", "fiu"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	FORMOSAN(Messages.getString("ISO6395.fox"), "map:fox", "fox"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	GERMANIC(Messages.getString("ISO6395.gem"), "ine:gem", "gem"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	EAST_GERMANIC(Messages.getString("ISO6395.gme"), "ine:gem:gme", "gme"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	NORTH_GERMANIC(Messages.getString("ISO6395.gmq"), "ine:gem:gmq", "gmq"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	WEST_GERMANIC(Messages.getString("ISO6395.gmw"), "ine:gem:gmw", "gmw"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	GREEK(Messages.getString("ISO6395.grk"), "ine:grk", "grk"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	HMONG_MIEN(Messages.getString("ISO6395.hmx"), "hmx", "hmx"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	HOKAN(Messages.getString("ISO6395.hok"), "nai:hok", "hok"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ARMENIAN(Messages.getString("ISO6395.hyx"), "ine:hyx", "hyx"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	INDO_IRANIAN(Messages.getString("ISO6395.iir"), "ine:iir", "iir"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	IJO(Messages.getString("ISO6395.ijo"), "nic:alv:ijo", "ijo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	INDIC(Messages.getString("ISO6395.inc"), "ine:iir:inc", "inc"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	INDO_EUROPEAN(Messages.getString("ISO6395.ine"), "ine", "ine"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	IRANIAN(Messages.getString("ISO6395.ira"), "ine:iir:ira", "ira"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	IROQUOIAN(Messages.getString("ISO6395.iro"), "nai:iro", "iro"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ITALIC(Messages.getString("ISO6395.itc"), "ine:itc", "itc"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	JAPANESE(Messages.getString("ISO6395.jpx"), "jpx", "jpx"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	KAREN(Messages.getString("ISO6395.kar"), "sit:tbq:kar", "kar"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	KORDOFANIAN(Messages.getString("ISO6395.kdo"), "nic:kdo", "kdo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	KHOISAN(Messages.getString("ISO6395.khi"), "khi", "khi"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	KRU(Messages.getString("ISO6395.kro"), "nic:alv:kro", "kro"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	AUSTRONESIAN(Messages.getString("ISO6395.map"), "map", "map"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	MON_KHMER(Messages.getString("ISO6395.mkh"), "aav:mkh", "mkh"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	MANOBO(Messages.getString("ISO6395.mno"), "map:poz:pqw:phi:mno", "mno"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	MUNDA(Messages.getString("ISO6395.mun"), "aav:mun", "mun"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	MAYAN(Messages.getString("ISO6395.myn"), "cai:myn", "myn"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	NAHUATL(Messages.getString("ISO6395.nah"), "nai:azc:nah", "nah"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	NORTH_AMERICAN_INDIAN(Messages.getString("ISO6395.nai"), "nai", "nai"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	TRANS_NEW_GUINEA(Messages.getString("ISO6395.ngf"), "paa:ngf", "ngf"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	NIGER_CONGO(Messages.getString("ISO6395.nic"), "nic", "nic"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	NUBIAN(Messages.getString("ISO6395.nub"), "ssa:sdv:nub", "nub"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	OTO_MANGUEAN(Messages.getString("ISO6395.omq"), "cai:omq", "omq"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	OMOTIC(Messages.getString("ISO6395.omv"), "afa:omv", "omv"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	OTOMIAN(Messages.getString("ISO6395.oto"), "cai:omq:oto", "oto"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	PAPUAN(Messages.getString("ISO6395.paa"), "paa", "paa"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	PHILIPPINE(Messages.getString("ISO6395.phi"), "map:poz:pqw:phi", "phi"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CENTRAL_MALAYO_POLYNESIAN(
			Messages.getString("ISO6395.plf"), "map:poz:plf", "plf"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	MALAYO_POLYNESIAN(Messages.getString("ISO6395.poz"), "map:poz", "poz"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	EASTERN_MALAYO_POLYNESIAN(
			Messages.getString("ISO6395.pqe"), "map:poz:pqe", "pqe"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	WESTERN_MALAYO_POLYNESIAN(
			Messages.getString("ISO6395.pqw"), "map:poz:pqw", "pqw"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	PRAKRIT(Messages.getString("ISO6395.pra"), "ine:iir:inc:pra", "pra"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	QUECHUAN(Messages.getString("ISO6395.qwe"), "sai:qwe", "qwe"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ROMANCE(Messages.getString("ISO6395.roa"), "ine:itc:roa", "roa"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SOUTH_AMERICAN_INDIAN(Messages.getString("ISO6395.sai"), "sai", "sai"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SALISHAN(Messages.getString("ISO6395.sal"), "nai:sal", "sal"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	EASTERN_SUDANIC(Messages.getString("ISO6395.sdv"), "ssa:sdv", "sdv"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SEMITIC(Messages.getString("ISO6395.sem"), "afa:sem", "sem"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SIGN(Messages.getString("ISO6395.sgn"), "sgn", "sgn"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SIOUAN(Messages.getString("ISO6395.sio"), "nai:sio", "sio"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SINO_TIBETAN(Messages.getString("ISO6395.sit"), "sit", "sit"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SLAVIC(Messages.getString("ISO6395.sla"), "ine:sla", "sla"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SAMI(Messages.getString("ISO6395.smi"), "urj:fiu:smi", "smi"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SONGHAI(Messages.getString("ISO6395.son"), "ssa:son", "son"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ALBANIAN(Messages.getString("ISO6395.sqj"), "ine:sqj", "sqj"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	NILO_SAHARAN(Messages.getString("ISO6395.ssa"), "ssa", "ssa"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SAMOYEDIC(Messages.getString("ISO6395.syd"), "urj:syd", "syd"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	TAI(Messages.getString("ISO6395.tai"), "tai", "tai"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	TIBETO_BURMAN(Messages.getString("ISO6395.tbq"), "sit:tbq", "tbq"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	TURKIC(Messages.getString("ISO6395.trk"), "tut:trk", "trk"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	TUPI(Messages.getString("ISO6395.tup"), "sai:tup", "tup"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ALTAIC(Messages.getString("ISO6395.tut"), "tut", "tut"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	TUNGUS(Messages.getString("ISO6395.tuw"), "tut:tuw", "tuw"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	URALIC(Messages.getString("ISO6395.urj"), "urj", "urj"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	WAKASHAN(Messages.getString("ISO6395.wak"), "nai:wak", "wak"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SORBIAN(Messages.getString("ISO6395.wen"), "ine:sla:zlw:wen", "wen"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	MONGOLIAN(Messages.getString("ISO6395.xgn"), "tut:xgn", "xgn"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	NA_DENE(Messages.getString("ISO6395.xnd"), "nai:xnd", "xnd"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	YUPIK(Messages.getString("ISO6395.ypk"), "esx:ypk", "ypk"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	CHINESE(Messages.getString("ISO6395.zhx"), "sit:zhx", "zhx"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	EAST_SLAVIC(Messages.getString("ISO6395.zle"), "ine:sla:zle", "zle"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	SOUTH_SLAVIC(Messages.getString("ISO6395.zls"), "ine:sla:zls", "zls"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	WEST_SLAVIC(Messages.getString("ISO6395.zlw"), "ine:sla:zlw", "zlw"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	ZANDE(Messages.getString("ISO6395.znd"), "nic:alv:znd", "znd"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	;
	private final String name;
	private final String hierarchy;
	private final String code;
	private final Optional<String> broader;

	/**
	 * Returns the local name of this family.
	 * 
	 * @return the local name of this family
	 */
	public String localName() {
		return this.name;
	}

	/**
	 * Returns the hierarchy String of this family
	 * separated by colons.
	 * 
	 * @return the hierarchy String
	 */
	public final String hierarchy() {
		return this.hierarchy;
	}
	
	/**
	 * Returns the broader linguistic categorization for
	 * this language family.
	 * 
	 * @return the broader family
	 */
	public final Optional<ISO6395> broader() {
		if(this.broader.isPresent())
			return Languages.familyForCode(this.broader.get());
		
		return Optional.absent();
	}
	
	private static Optional<String> parseBroader(String hierarchy) {
		String[] parts = hierarchy.split(":"); //$NON-NLS-1$

		if(parts.length<=1) return Optional.absent();
		return Optional.of(parts[parts.length-2]);
	}
	
	/**
	 * Returns the three-letter code of this
	 * family.
	 * 
	 * @return the three-letter code
	 */
	public final String code() {
		return this.code;
	}

	private ISO6395(final String name, final String hierarchy,
			final String code) {
		this.name = name;
		this.hierarchy = hierarchy;
		this.code = code;
		this.broader = parseBroader(hierarchy);
	}
}