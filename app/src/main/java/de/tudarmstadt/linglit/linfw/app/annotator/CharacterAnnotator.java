package de.tudarmstadt.linglit.linfw.app.annotator;

import de.tudarmstadt.linglit.linfw.app.annotation.Unicode;
import de.tudarmstadt.linglit.linfw.core.Producer;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;
import de.tudarmstadt.linglit.linfw.core.annotator.SerialAnnotator;
import de.tudarmstadt.linglit.linfw.core.layer.AppendableLayer;
import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

@Producer(type=Unicode.class)
public class CharacterAnnotator extends SerialAnnotator<Unicode> {

	public CharacterAnnotator(AnnotatorExecutor executor) {
		super(executor, Unicode.class);
	}

	@Override
	protected void process(CharSequence text, LayerProvider layerprovider, AppendableLayer<Unicode> layer) {
		for(int i=0; i<text.length(); i++) {
			Unicode unicode = new Unicode(text.charAt(i),Character.codePointAt(text, i));
			layer.appendAnnotation(new Annotation<>(TextSpan.between(text, i, i),	unicode,Unicode.class));
		}
	}

}
