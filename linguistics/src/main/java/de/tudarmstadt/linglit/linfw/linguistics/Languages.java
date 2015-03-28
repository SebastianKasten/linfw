package de.tudarmstadt.linglit.linfw.linguistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;

public class Languages {
	private static final Map<String,ISO6393> iso6393cache =
			new HashMap<>();
	
	static {
		final Map<String,String> familyMapping =
				new HashMap<>();
		// ISO 639-3 initialization
		try(BufferedReader familyReader = Files.newBufferedReader(Paths.get("res", "mapping.tab"), Charsets.UTF_8)) {  //$NON-NLS-1$//$NON-NLS-2$
			while(familyReader.ready()) {
				final String line = familyReader.readLine();
				familyMapping.put(line.split("\t")[0], line); //$NON-NLS-1$
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try(BufferedReader iso6393Reader = Files.newBufferedReader(Paths.get("res", "iso6393.tab"), Charsets.UTF_8)) {  //$NON-NLS-1$//$NON-NLS-2$
			while(iso6393Reader.ready()) {
				final String line = iso6393Reader.readLine();
				final Optional<String> mappingStr = Optional.fromNullable(familyMapping.get(line.split("\t")[0])); //$NON-NLS-1$
				final ISO6393 language = ISO6393.parse(line, mappingStr);
				
				iso6393cache.put(language.code(), language);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static interface Language {
		/**
		 * Returns the three-letter code as defined in ISO 639-3 the most
		 * complete standard consisting of all known natural languages.
		 * 
		 * @return the ISO 639-3 code
		 */
		public String iso6393code();
	}

	public static Optional<ISO6395> familyForCode(String code) {
		for (final ISO6395 family : ISO6395.values())
			if (family.code().toLowerCase().equals(code))
				return Optional.of(family);
		
		return Optional.absent();
	}
	
	public static Optional<ISO6391> forLocale(final Locale locale) {
		final String twoLetterForm = locale.getLanguage().toLowerCase();
		for (final ISO6391 language : ISO6391.values())
			if (language.twoLetterCode().toLowerCase().equals(twoLetterForm))
				return Optional.of(language);

		return Optional.absent();
	}

	public static Optional<ISO6391> forISO6391Code(String code) {
		final String lowerCode = code.toLowerCase();
		if (lowerCode.length() == 2) {
			for (final ISO6391 language : ISO6391.values())
				if (language.twoLetterCode().toLowerCase().equals(lowerCode))
					return Optional.of(language);
		} else if (lowerCode.length() == 3)
			for (final ISO6391 language : ISO6391.values())
				if (language.threeLetterCodeNative().toLowerCase().equals(lowerCode))
					return Optional.of(language);

		return Optional.absent();
	}
	
	public static Optional<ISO6393> forISO6393Code(String code) {
		final String lowerCode = code.toLowerCase();
		return Optional.fromNullable(iso6393cache.get(lowerCode));
	}
	
	public static void main(String[] args)  {
		System.out.println(forISO6393Code("lim"));
	}
}
