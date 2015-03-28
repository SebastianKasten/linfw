package de.tudarmstadt.linglit.linfw.app.annotator;

import org.apache.tika.language.LanguageIdentifier;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.core.Producer;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;
import de.tudarmstadt.linglit.linfw.core.annotator.SerialAnnotator;
import de.tudarmstadt.linglit.linfw.core.layer.AppendableLayer;
import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;
import de.tudarmstadt.linglit.linfw.linguistics.Languages;
import de.tudarmstadt.linglit.linfw.linguistics.Languages.Language;

/**
 * <p>
 * This annotator uses the language identifier from the Apache Tika project to
 * predict the language of the document.
 * </p>
 *
 * <p>
 * The following languages are predictable:
 * <ul>
 * <li>Belarusian</li>
 * <li>Catalan</li>
 * <li>Danish</li>
 * <li>Dutch</li>
 * <li>English</li>
 * <li>Esperanto</li>
 * <li>Estonian</li>
 * <li>Finish</li>
 * <li>French</li>
 * <li>Galician</li>
 * <li>German</li>
 * <li>Greek</li>
 * <li>Hungarian</li>
 * <li>Icelandic</li>
 * <li>Italian</li>
 * <li>Latvian</li>
 * <li>Norwegian</li>
 * <li>Persian</li>
 * <li>Polish</li>
 * <li>Portuguese</li>
 * <li>Romanian</li>
 * <li>Russian</li>
 * <li>Slovak</li>
 * <li>Slovene</li>
 * <li>Spanish</li>
 * <li>Swedish</li>
 * <li>Thai</li>
 * <li>Ukrainian</li>
 * </ul>
 * </p>
 *
 * <p>
 * The annotator creates one annotation for the whole text. Multilingual texts
 * are not supported.
 * </p>
 *
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
@Producer(type = Language.class)
public class TikaLanguageAnnotator extends SerialAnnotator<Language> {
	private final boolean mustBeCertain;

	/**
	 * Creates a new Tika language detector.
	 *
	 * @param executor
	 *            executor of this annotator (not used)
	 */
	public TikaLanguageAnnotator(final AnnotatorExecutor executor) {
		super(executor, Language.class);
		this.mustBeCertain = false;
	}

	/**
	 * Creates a new Tika language detector.
	 *
	 * @param executor
	 *            executor of this annotator (not used)
	 * @param mustBeCertain
	 *            true, if annotator should only annotate languages with a
	 *            reasonable certainty; false, if it should any language
	 *            predicted
	 */
	public TikaLanguageAnnotator(final AnnotatorExecutor executor,
			final boolean mustBeCertain) {
		super(executor, Language.class);
		this.mustBeCertain = mustBeCertain;
	}

	/** {@inheritDoc} */
	@Override
	protected void process(final CharSequence text,
			final LayerProvider layerprovider,
			final AppendableLayer<Language> layer) {
		final LanguageIdentifier identifier = new LanguageIdentifier(
				text.toString());

		if (!this.mustBeCertain || identifier.isReasonablyCertain()) {
			final String code = identifier.getLanguage();
			final Optional<? extends Language> language = Languages
					.forISO6391Code(code);

			if (language.isPresent())
				layer.appendAnnotation(new Annotation<>(TextSpan.allOf(text), 
						language.get(), Language.class));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.mustBeCertain ? 1231 : 1237);
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
		TikaLanguageAnnotator other = (TikaLanguageAnnotator) obj;
		if (this.mustBeCertain != other.mustBeCertain)
			return false;
		return true;
	}

}
