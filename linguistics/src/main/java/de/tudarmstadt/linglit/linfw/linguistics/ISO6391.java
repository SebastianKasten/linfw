package de.tudarmstadt.linglit.linfw.linguistics;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.linguistics.Languages.Language;

/**
 * Enum holding all languages that are classified by the ISO 639-1 norm.
 * 
 * @author Sebastian Kasten
 */
public enum ISO6391 implements Language {
	ABKHAZ(ISO6395.NORTH_CAUCASIAN,
			Messages.getString("ISO6391.ab"), "аҧсшәа", "ab", "abk", "abk"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	AFAR(ISO6395.CUSHITIC,
			Messages.getString("ISO6391.aa"), "Afaraf", "aa", "aar", "aar"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	AFRIKAANS(
			ISO6395.WEST_GERMANIC,
			Messages.getString("ISO6391.af"), "Afrikaans", "af", "afr", "afr"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	AKAN(ISO6395.ATLANTIC_CONGO,
			Messages.getString("ISO6391.ak"), "Akan", "ak", "aka", "aka"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ALBANIAN(ISO6395.INDO_EUROPEAN,
			Messages.getString("ISO6391.sq"), "Shqip", "sq", "sqi", "alb"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	AMHARIC(ISO6395.SEMITIC,
			Messages.getString("ISO6391.am"), "አማርኛ", "am", "amh", "amh"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ARABIC(ISO6395.SEMITIC,
			Messages.getString("ISO6391.ar"), "العربية", "ar", "ara", "ara"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ARAGONESE(
			ISO6395.ROMANCE,
			Messages.getString("ISO6391.an"), "Aragonés", "an", "arg", "arg"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ARMENIAN(ISO6395.INDO_EUROPEAN,
			Messages.getString("ISO6391.hy"), "Հայերեն", "hy", "hye", "arm"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ASSAMESE(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.as"), "অসমীয়া", "as", "asm", "asm"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	AVARIC(
			ISO6395.NORTH_CAUCASIAN,
			Messages.getString("ISO6391.av"), "авар мацӀ, магӀарул мацӀ", "av", "ava", "ava"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	AVESTAN(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.ae"), "avesta", "ae", "ave", "ave"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	AYMARA(
			ISO6395.QUECHUAN,
			Messages.getString("ISO6391.ay"), "aymar aru", "ay", "aym", "aym"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	AZERBAIJANI(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.az"), "azərbaycan dili", "az", "aze", "aze"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BAMBARA(
			ISO6395.MANDE,
			Messages.getString("ISO6391.bm"), "bamanankan", "bm", "bam", "bam"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BASHKIR(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.ba"), "башҡорт теле", "ba", "bak", "bak"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BASQUE(
			ISO6395.BASQUE,
			Messages.getString("ISO6391.eu"), "euskara, euskera", "eu", "eus", "baq"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BELARUSIAN(
			ISO6395.EAST_SLAVIC,
			Messages.getString("ISO6391.be"), "беларуская", "be", "bel", "bel"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BENGALI(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.bn"), "বাংলা", "bn", "ben", "ben"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BIHARI(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.bh"), "भोजपुरी", "bh", "bih", "bih"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BISLAMA(ISO6395.CREOLES_AND_PIDGINS_ENGLISH_BASED, Messages
			.getString("ISO6391.bi"), "Bislama", "bi", "bis", "bis"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BOSNIAN(
			ISO6395.SOUTH_SLAVIC,
			Messages.getString("ISO6391.bs"), "bosanski jezik", "bs", "bos", "bos"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BRETON(
			ISO6395.CELTIC,
			Messages.getString("ISO6391.br"), "brezhoneg", "br", "bre", "bre"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BULGARIAN(
			ISO6395.SOUTH_SLAVIC,
			Messages.getString("ISO6391.bg"), "български език", "bg", "bul", "bul"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	BURMESE(ISO6395.TIBETO_BURMAN,
			Messages.getString("ISO6391.my"), "ဗမာစာ", "my", "mya", "bur"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CATALAN(ISO6395.ROMANCE,
			Messages.getString("ISO6391.ca"), "Català", "ca", "cat", "cat"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CHAMORRO(ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.ch"), "Chamoru", "ch", "cha", "cha"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CHECHEN(
			ISO6395.NORTH_CAUCASIAN,
			Messages.getString("ISO6391.ce"), "нохчийн мотт", "ce", "che", "che"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CHICHEWA(
			ISO6395.BANTU,
			Messages.getString("ISO6391.ny"), "chiCheŵa, chinyanja", "ny", "nya", "nya"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CHINESE(
			ISO6395.SINO_TIBETAN,
			Messages.getString("ISO6391.zh"), "中文 (Zh�?ngwén), 汉语, 漢語", "zh", "zho", "chi"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CHUVASH(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.cv"), "чӑваш чӗлхи", "cv", "chv", "chv"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CORNISH(
			ISO6395.CELTIC,
			Messages.getString("ISO6391.kw"), "Kernewek", "kw", "cor", "cor"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CORSICAN(
			ISO6395.ROMANCE,
			Messages.getString("ISO6391.co"), "corsu, lingua corsa", "co", "cos", "cos"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CREE(
			ISO6395.ALGONQUIAN,
			Messages.getString("ISO6391.cr"), "ᓀᐦᐃᔭᐍᐏᐣ", "cr", "cre", "cre"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CROATIAN(
			ISO6395.SOUTH_SLAVIC,
			Messages.getString("ISO6391.hr"), "hrvatski", "hr", "hrv", "hrv"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	CZECH(
			ISO6395.WEST_SLAVIC,
			Messages.getString("ISO6391.cs"), "český", "cs", "ces", "cze"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	DANISH(ISO6395.NORTH_GERMANIC,
			Messages.getString("ISO6391.da"), "dansk", "da", "dan", "dan"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	DIVEHI(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.dv"), "ދިވެހި", "dv", "div", "div"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	DUTCH(
			ISO6395.WEST_GERMANIC,
			Messages.getString("ISO6391.nl"), "Nederlands, Vlaams", "nl", "nld", "dut"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	DZONGKHA(ISO6395.TIBETO_BURMAN,
			Messages.getString("ISO6391.dz"), "རྫོང་ཁ", "dz", "dzo", "dzo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ENGLISH(ISO6395.WEST_GERMANIC,
			Messages.getString("ISO6391.en"), "English", "en", "eng", "eng"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ESPERANTO(
			ISO6395.ARTIFICIAL,
			Messages.getString("ISO6391.eo"), "Esperanto", "eo", "epo", "epo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ESTONIAN(
			ISO6395.FINNO_UGRIAN,
			Messages.getString("ISO6391.et"), "eesti, eesti keel", "et", "est", "est"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	EWE(ISO6395.ATLANTIC_CONGO,
			Messages.getString("ISO6391.ee"), "Eʋegbe", "ee", "ewe", "ewe"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	FAROESE(
			ISO6395.NORTH_GERMANIC,
			Messages.getString("ISO6391.fo"), "føroyskt", "fo", "fao", "fao"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	FIJIAN(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.fj"), "vosa Vakaviti", "fj", "fij", "fij"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	FINNISH(
			ISO6395.FINNO_UGRIAN,
			Messages.getString("ISO6391.fi"), "suomi, suomen kieli", "fi", "fin", "fin"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	FRENCH(
			ISO6395.ROMANCE,
			Messages.getString("ISO6391.fr"), "français, langue française", "fr", "fra", "fre"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	FULA(
			ISO6395.ATLANTIC_CONGO,
			Messages.getString("ISO6391.ff"), "Fulfulde, Pulaar, Pular", "ff", "ful", "ful"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	GALICIAN(ISO6395.ROMANCE,
			Messages.getString("ISO6391.gl"), "Galego", "gl", "glg", "glg"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	GEORGIAN(
			ISO6395.SOUTH_CAUCASIAN,
			Messages.getString("ISO6391.ka"), "ქართული", "ka", "kat", "geo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	GERMAN(ISO6395.WEST_GERMANIC,
			Messages.getString("ISO6391.de"), "Deutsch", "de", "deu", "ger"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	GREEK(
			ISO6395.GREEK,
			Messages.getString("ISO6391.el"), "Ελληνικά", "el", "ell", "gre"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	GUARANÍ(ISO6395.TUPI,
			Messages.getString("ISO6391.gn"), "Avañe'ẽ", "gn", "grn", "grn"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	GUJARATI(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.gu"), "ગુજરાતી", "gu", "guj", "guj"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	HAITIAN(ISO6395.CREOLES_AND_PIDGINS_FRENCH_BASED, Messages
			.getString("ISO6391.ht"), "Kreyòl ayisyen", "ht", "hat", "hat"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	HAUSA(
			ISO6395.CHADIC,
			Messages.getString("ISO6391.ha"), "هَوُسَ", "ha", "hau", "hau"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MODERN_HEBREW(ISO6395.SEMITIC,
			Messages.getString("ISO6391.he"), "עברית", "he", "heb", "heb"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	HERERO(
			ISO6395.BANTU,
			Messages.getString("ISO6391.hz"), "Otjiherero", "hz", "her", "her"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	HINDI(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.hi"), "हिन्दी", "hi", "hin", "hin"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	HIRI_MOTU(
			ISO6395.AUSTRONESIAN,
			Messages.getString("ISO6391.ho"), "Hiri Motu", "ho", "hmo", "hmo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	HUNGARIAN(ISO6395.URALIC,
			Messages.getString("ISO6391.hu"), "Magyar", "hu", "hun", "hun"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	INTERLINGUA(
			ISO6395.ARTIFICIAL,
			Messages.getString("ISO6391.ia"), "Interlingua", "ia", "ina", "ina"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	INDONESIAN(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.id"), "Bahasa Indonesia", "id", "ind", "ind"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	INTERLINGUE(
			ISO6395.ARTIFICIAL,
			Messages.getString("ISO6391.ie"), "Originally called Occidental; then Interlingue after WWII", "ie", "ile", "ile"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	IRISH(ISO6395.CELTIC,
			Messages.getString("ISO6391.ga"), "Gaeilge", "ga", "gle", "gle"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	IGBO(
			ISO6395.ATLANTIC_CONGO,
			Messages.getString("ISO6391.ig"), "Asụsụ Igbo", "ig", "ibo", "ibo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	INUPIAQ(
			ISO6395.ESKIMO_ALEUT,
			Messages.getString("ISO6391.ik"), "Iñupiaq, Iñupiatun", "ik", "ipk", "ipk"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	IDO(ISO6395.ARTIFICIAL,
			Messages.getString("ISO6391.io"), "Ido", "io", "ido", "ido"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ICELANDIC(
			ISO6395.NORTH_GERMANIC,
			Messages.getString("ISO6391.is"), "Íslenska", "is", "isl", "ice"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ITALIAN(
			ISO6395.ROMANCE,
			Messages.getString("ISO6391.it"), "Italiano", "it", "ita", "ita"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	INUKTITUT(
			ISO6395.ESKIMO_ALEUT,
			Messages.getString("ISO6391.iu"), "ᐃᓄᒃᑎᑐᑦ", "iu", "iku", "iku"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	JAPANESE(
			ISO6395.JAPANESE,
			Messages.getString("ISO6391.ja"), "日本語", "ja", "jpn", "jpn"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	JAVANESE(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.jv"), "basa Jawa", "jv", "jav", "jav"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KALAALLISUT(
			ISO6395.ESKIMO_ALEUT,
			Messages.getString("ISO6391.kl"), "kalaallisut, kalaallit oqaasii", "kl", "kal", "kal"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KANNADA(ISO6395.DRAVIDIAN,
			Messages.getString("ISO6391.kn"), "ಕನ್ನಡ", "kn", "kan", "kan"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KANURI(ISO6395.NILO_SAHARAN,
			Messages.getString("ISO6391.kr"), "Kanuri", "kr", "kau", "kau"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KASHMIRI(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.ks"), "कश्मीरी‎", "ks", "kas", "kas"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KAZAKH(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.kk"), "Қазақ тілі", "kk", "kaz", "kaz"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KHMER(
			ISO6395.AUSTRO_ASIATIC,
			Messages.getString("ISO6391.km"), "ខ្មែរ", "km", "khm", "khm"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KIKUYU(ISO6395.BANTU,
			Messages.getString("ISO6391.ki"), "Gĩkũyũ", "ki", "kik", "kik"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	RWANDA(
			ISO6395.BANTU,
			Messages.getString("ISO6391.rw"), "Ikinyarwanda", "rw", "kin", "kin"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KIRGHIZ(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.ky"), "кыргыз тили", "ky", "kir", "kir"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KOMI(
			ISO6395.URALIC,
			Messages.getString("ISO6391.kv"), "коми кыв", "kv", "kom", "kom"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KONGO(ISO6395.BANTU,
			Messages.getString("ISO6391.kg"), "KiKongo", "kg", "kon", "kon"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KOREAN(
			ISO6395.ALTAIC,
			Messages.getString("ISO6391.ko"), "한국어", "ko", "kor", "kor"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KURDISH(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.ku"), "Kurdî, كوردی‎", "ku", "kur", "kur"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	KWANYAMA(
			ISO6395.BANTU,
			Messages.getString("ISO6391.kj"), "Kuanyama", "kj", "kua", "kua"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	LATIN(
			ISO6395.ITALIC,
			Messages.getString("ISO6391.la"), "latine, lingua latina", "la", "lat", "lat"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	LUXEMBOURGISH(
			ISO6395.WEST_GERMANIC,
			Messages.getString("ISO6391.lb"), "Lëtzebuergesch", "lb", "ltz", "ltz"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	GANDA(ISO6395.BANTU,
			Messages.getString("ISO6391.lg"), "Ganda", "lg", "lug", "lug"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	LIMBURGISH(
			ISO6395.WEST_GERMANIC,
			Messages.getString("ISO6391.li"), "Limburgs", "li", "lim", "lim"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	LINGALA(ISO6395.BANTU,
			Messages.getString("ISO6391.ln"), "Lingála", "ln", "lin", "lin"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	LAO(ISO6395.TAI,
			Messages.getString("ISO6391.lo"), "ພາສາລາວ", "lo", "lao", "lao"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	LITHUANIAN(
			ISO6395.BALTIC,
			Messages.getString("ISO6391.lt"), "lietuvių kalba", "lt", "lit", "lit"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	LUBA_KATANGA(ISO6395.BANTU,
			Messages.getString("ISO6391.lu"), "Tshiluba", "lu", "lub", "lub"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	LATVIAN(
			ISO6395.BALTIC,
			Messages.getString("ISO6391.lv"), "latviešu valoda", "lv", "lav", "lav"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MANX(
			ISO6395.CELTIC,
			Messages.getString("ISO6391.gv"), "Gaelg, Gailck", "gv", "glv", "glv"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MACEDONIAN(
			ISO6395.SOUTH_SLAVIC,
			Messages.getString("ISO6391.mk"), "македонски јазик", "mk", "mkd", "mac"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MALAGASY(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.mg"), "Malagasy fiteny", "mg", "mlg", "mlg"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MALAY(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.ms"), "bahasa Melayu, بهاس ملايو‎", "ms", "msa", "may"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MALAYALAM(ISO6395.DRAVIDIAN,
			Messages.getString("ISO6391.ml"), "മലയാളം", "ml", "mal", "mal"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MALTESE(ISO6395.SEMITIC,
			Messages.getString("ISO6391.mt"), "Malti", "mt", "mlt", "mlt"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MĀORI(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.mi"), "te reo Māori", "mi", "mri", "mao"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MARATHI(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.mr"), "मराठी", "mr", "mar", "mar"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MARSHALLESE(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.mh"), "Kajin M̧ajeļ", "mh", "mah", "mah"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	MONGOLIAN(ISO6395.MONGOLIAN,
			Messages.getString("ISO6391.mn"), "монгол", "mn", "mon", "mon"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NAURU(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.na"), "Ekakairũ Naoero", "na", "nau", "nau"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NAVAJO(
			ISO6395.ATHAPASCAN,
			Messages.getString("ISO6391.nv"), "Diné bizaad", "nv", "nav", "nav"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NORWEGIAN_BOKMÅL(ISO6395.NORTH_GERMANIC, Messages
			.getString("ISO6391.nb"), "Norsk bokmål", "nb", "nob", "nob"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NORTH_NDEBELE(
			ISO6395.BANTU,
			Messages.getString("ISO6391.nd"), "isiNdebele", "nd", "nde", "nde"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NEPALI(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.ne"), "नेपाली", "ne", "nep", "nep"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NDONGA(ISO6395.BANTU,
			Messages.getString("ISO6391.ng"), "Owambo", "ng", "ndo", "ndo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NORWEGIAN_NYNORSK(ISO6395.NORTH_GERMANIC, Messages
			.getString("ISO6391.nn"), "Norsk nynorsk", "nn", "nno", "nno"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NORWEGIAN(ISO6395.NORTH_GERMANIC,
			Messages.getString("ISO6391.no"), "Norsk", "no", "nor", "nor"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NUOSU(
			ISO6395.TIBETO_BURMAN,
			Messages.getString("ISO6391.ii"), "ꆈꌠ꒿", "ii", "iii", "iii"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SOUTH_NDEBELE(
			ISO6395.BANTU,
			Messages.getString("ISO6391.nr"), "isiNdebele", "nr", "nbl", "nbl"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	OCCITAN(ISO6395.ROMANCE,
			Messages.getString("ISO6391.oc"), "Occitan", "oc", "oci", "oci"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	OJIBWE(
			ISO6395.ALGONQUIAN,
			Messages.getString("ISO6391.oj"), "ᐊᓂᔑᓈᐯᒧᐎᓐ", "oj", "oji", "oji"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	OLD_CHURCH_SLAVONIC(
			ISO6395.SOUTH_SLAVIC,
			Messages.getString("ISO6391.cu"), "ѩзыкъ словѣньскъ", "cu", "chu", "chu"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	OROMO(
			ISO6395.CUSHITIC,
			Messages.getString("ISO6391.om"), "Afaan Oromoo", "om", "orm", "orm"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ORIYA(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.or"), "ଓଡ଼ିଆ", "or", "ori", "ori"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	OSSETIAN(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.os"), "ирон æвзаг", "os", "oss", "oss"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	PANJABI(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.pa"), "ਪੰਜਾਬੀ, پنجابی‎", "pa", "pan", "pan"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	PĀLI(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.pi"), "पाऴि", "pi", "pli", "pli"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	PERSIAN(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.fa"), "فارسی", "fa", "fas", "per"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	POLISH(ISO6395.WEST_SLAVIC,
			Messages.getString("ISO6391.pl"), "polski", "pl", "pol", "pol"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	PASHTO(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.ps"), "پښتو", "ps", "pus", "pus"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	PORTUGUESE(
			ISO6395.ROMANCE,
			Messages.getString("ISO6391.pt"), "Português", "pt", "por", "por"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	QUECHUA(
			ISO6395.QUECHUAN,
			Messages.getString("ISO6391.qu"), "Runa Simi, Kichwa", "qu", "que", "que"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ROMANSH(
			ISO6395.ROMANCE,
			Messages.getString("ISO6391.rm"), "rumantsch grischun", "rm", "roh", "roh"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	RUNDI(
			ISO6395.BANTU,
			Messages.getString("ISO6391.rn"), "Ikirundi", "rn", "run", "run"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ROMANIAN(ISO6395.ROMANCE,
			Messages.getString("ISO6391.ro"), "română", "ro", "ron", "rum"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	RUSSIAN(
			ISO6395.EAST_SLAVIC,
			Messages.getString("ISO6391.ru"), "русский язык", "ru", "rus", "rus"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SANSKRIT(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.sa"), "संस्कृतम्", "sa", "san", "san"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SARDINIAN(ISO6395.ROMANCE,
			Messages.getString("ISO6391.sc"), "sardu", "sc", "srd", "srd"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SINDHI(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.sd"), "सिन्धी‎", "sd", "snd", "snd"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	NORTHERN_SAMI(
			ISO6395.SAMI,
			Messages.getString("ISO6391.se"), "Davvisámegiella", "se", "sme", "sme"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SAMOAN(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.sm"), "gagana fa'a Samoa", "sm", "smo", "smo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SANGO(
			ISO6395.CREOLES_AND_PIDGINS,
			Messages.getString("ISO6391.sg"), "yângâ tî sängö", "sg", "sag", "sag"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SERBIAN(
			ISO6395.SOUTH_SLAVIC,
			Messages.getString("ISO6391.sr"), "српски језик", "sr", "srp", "srp"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SCOTTISH_GAELIC(
			ISO6395.CELTIC,
			Messages.getString("ISO6391.gd"), "Gàidhlig", "gd", "gla", "gla"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SHONA(
			ISO6395.BANTU,
			Messages.getString("ISO6391.sn"), "chiShona", "sn", "sna", "sna"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SINHALA(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.si"), "සිංහල", "si", "sin", "sin"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SLOVAK(
			ISO6395.WEST_SLAVIC,
			Messages.getString("ISO6391.sk"), "sloven�?ina", "sk", "slk", "slo"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SLOVENE(
			ISO6395.SOUTH_SLAVIC,
			Messages.getString("ISO6391.sl"), "slovenš�?ina", "sl", "slv", "slv"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SOMALI(
			ISO6395.CUSHITIC,
			Messages.getString("ISO6391.so"), "Soomaaliga, af Soomaali", "so", "som", "som"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SOUTHERN_SOTHO(ISO6395.BANTU,
			Messages.getString("ISO6391.st"), "Sesotho", "st", "sot", "sot"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SPANISH(
			ISO6395.ROMANCE,
			Messages.getString("ISO6391.es"), "español, castellano", "es", "spa", "spa"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SUNDANESE(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.su"), "Basa Sunda", "su", "sun", "sun"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SWAHILI(
			ISO6395.BANTU,
			Messages.getString("ISO6391.sw"), "Kiswahili", "sw", "swa", "swa"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SWATI(ISO6395.BANTU,
			Messages.getString("ISO6391.ss"), "SiSwati", "ss", "ssw", "ssw"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	SWEDISH(ISO6395.NORTH_GERMANIC,
			Messages.getString("ISO6391.sv"), "svenska", "sv", "swe", "swe"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TAMIL(ISO6395.DRAVIDIAN,
			Messages.getString("ISO6391.ta"), "தமிழ்", "ta", "tam", "tam"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TELUGU(
			ISO6395.DRAVIDIAN,
			Messages.getString("ISO6391.te"), "తెలుగు", "te", "tel", "tel"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TAJIK(
			ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.tg"), "тоҷикӣ", "tg", "tgk", "tgk"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	THAI(ISO6395.TAI,
			Messages.getString("ISO6391.th"), "ไทย", "th", "tha", "tha"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TIGRINYA(ISO6395.SEMITIC,
			Messages.getString("ISO6391.ti"), "ትግርኛ", "ti", "tir", "tir"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TIBETAN_STANDARD(ISO6395.TIBETO_BURMAN,
			Messages.getString("ISO6391.bo"), "བོད་ཡིག", "bo", "bod", "tib"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TURKMEN(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.tk"), "Türkmen", "tk", "tuk", "tuk"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TAGALOG(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.tl"), "Wikang Tagalog", "tl", "tgl", "tgl"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TSWANA(
			ISO6395.BANTU,
			Messages.getString("ISO6391.tn"), "Setswana", "tn", "tsn", "tsn"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TONGA(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.to"), "faka Tonga", "to", "ton", "ton"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TURKISH(ISO6395.TURKIC,
			Messages.getString("ISO6391.tr"), "Türkçe", "tr", "tur", "tur"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TSONGA(
			ISO6395.BANTU,
			Messages.getString("ISO6391.ts"), "Xitsonga", "ts", "tso", "tso"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TATAR(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.tt"), "татарча‎", "tt", "tat", "tat"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TWI(ISO6395.ATLANTIC_CONGO,
			Messages.getString("ISO6391.tw"), "Twi", "tw", "twi", "twi"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	TAHITIAN(
			ISO6395.MALAYO_POLYNESIAN,
			Messages.getString("ISO6391.ty"), "Reo Tahiti", "ty", "tah", "tah"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	UIGHUR(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.ug"), "Uyƣurqə, ئۇيغۇرچە‎", "ug", "uig", "uig"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	UKRAINIAN(
			ISO6395.EAST_SLAVIC,
			Messages.getString("ISO6391.uk"), "українська мова", "uk", "ukr", "ukr"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	URDU(ISO6395.INDO_IRANIAN,
			Messages.getString("ISO6391.ur"), "اردو", "ur", "urd", "urd"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	UZBEK(
			ISO6395.TURKIC,
			Messages.getString("ISO6391.uz"), "O'zbek‎", "uz", "uzb", "uzb"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	VENDA(
			ISO6395.BANTU,
			Messages.getString("ISO6391.ve"), "Tshivenḓa", "ve", "ven", "ven"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	VIETNAMESE(
			ISO6395.AUSTRO_ASIATIC,
			Messages.getString("ISO6391.vi"), "Tiếng Việt", "vi", "vie", "vie"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	VOLAPÜK(ISO6395.ARTIFICIAL,
			Messages.getString("ISO6391.vo"), "Volapük", "vo", "vol", "vol"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	WALLOON(ISO6395.ROMANCE,
			Messages.getString("ISO6391.wa"), "Walon", "wa", "wln", "wln"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	WELSH(ISO6395.CELTIC,
			Messages.getString("ISO6391.cy"), "Cymraeg", "cy", "cym", "wel"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	WOLOF(ISO6395.ATLANTIC_CONGO,
			Messages.getString("ISO6391.wo"), "Wollof", "wo", "wol", "wol"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	WESTERN_FRISIAN(ISO6395.WEST_GERMANIC,
			Messages.getString("ISO6391.fv"), "Frysk", "fy", "fry", "fry"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	XHOSA(
			ISO6395.BANTU,
			Messages.getString("ISO6391.xh"), "isiXhosa", "xh", "xho", "xho"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	YIDDISH(ISO6395.WEST_GERMANIC,
			Messages.getString("ISO6391.yi"), "ייִדיש", "yi", "yid", "yid"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	YORUBA(ISO6395.ATLANTIC_CONGO,
			Messages.getString("ISO6391.yo"), "Yorùbá", "yo", "yor", "yor"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ZHUANG(
			ISO6395.TAI,
			Messages.getString("ISO6391.za"), "Saɯ cueŋƅ, Saw cuengh", "za", "zha", "zha"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	ZULU(ISO6395.BANTU,
			Messages.getString("ISO6391.zu"), "isiZulu", "zu", "zul", "zul"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	;
	private final ISO6395 family;
	private final String name;
	private final String nativeName;
	private final String twoLetterCode;
	private final String threeLetterCodeEnglish;
	private final String threeLetterCodeNative;

	public final ISO6395 family() {
		return this.family;
	}
	
	public final ISO6393 iso6393Equivalent() {
		return Languages.forISO6393Code(iso6393code()).get();
	}

	public final String localName() {
		return this.name;
	}

	public final String nativeName() {
		return this.nativeName;
	}

	public final String twoLetterCode() {
		return this.twoLetterCode;
	}

	public final String threeLetterCodeEnglish() {
		return this.threeLetterCodeEnglish;
	}

	public final String threeLetterCodeNative() {
		return this.threeLetterCodeNative;
	}

	ISO6391(final ISO6395 family, final String name,
			final String nativeName, final String twoLetterCode,
			final String threeLetterCodeNative,
			final String threeLetterCodeEnglish) {
		this.family = family;
		this.name = name;
		this.nativeName = nativeName;
		this.twoLetterCode = twoLetterCode;
		this.threeLetterCodeEnglish = threeLetterCodeEnglish;
		this.threeLetterCodeNative = threeLetterCodeNative;
	}

	/** {@inheritDoc} */
	@Override
	public String iso6393code() {
		return threeLetterCodeNative();
	}
	
	/**
	 * Tries to find a ISO639-1 language for the given generic language.
	 * 
	 * @param language generic language
	 * @return the ISO639-1 equivalent of the given language or <code>Optional.absent()</code> if the language cannot be classified as ISO639-1
	 */
	public static Optional<ISO6391> fromLanguage(Language language) {
		final Optional<ISO6393> iso6393 = Languages.forISO6393Code(language
				.iso6393code());
		if (iso6393.isPresent()) {
			return iso6393.get().iso6391Equivalent();
		}
		
		return Optional.absent();
	}
}