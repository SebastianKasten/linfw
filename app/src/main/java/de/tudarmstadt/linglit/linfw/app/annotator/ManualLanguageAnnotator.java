package de.tudarmstadt.linglit.linfw.app.annotator;

import javax.swing.JOptionPane;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.core.Producer;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;
import de.tudarmstadt.linglit.linfw.core.annotator.SerialAnnotator;
import de.tudarmstadt.linglit.linfw.core.layer.AppendableLayer;
import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;
import de.tudarmstadt.linglit.linfw.linguistics.ISO6393;
import de.tudarmstadt.linglit.linfw.linguistics.Languages;
import de.tudarmstadt.linglit.linfw.linguistics.Languages.Language;

@Producer(type=Language.class)
public class ManualLanguageAnnotator extends SerialAnnotator<Language> {

	public ManualLanguageAnnotator(AnnotatorExecutor executor) {
		super(executor, Language.class);
	}

	@Override
	protected void process(CharSequence text, LayerProvider layerprovider,
			AppendableLayer<Language> layer) {
		String code = JOptionPane.showInputDialog("Type in the ISO639-3 language code for this text.");
		
		Optional<ISO6393> language = Languages.forISO6393Code(code);
		if(language.isPresent())
			layer.appendAnnotation(new Annotation<>(TextSpan.allOf(text), 
					language.get(), Language.class));
	}

}
