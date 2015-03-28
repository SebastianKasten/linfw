package de.tudarmstadt.linglit.linfw.app.annotator;

import de.tudarmstadt.linglit.linfw.app.annotation.Word;
import de.tudarmstadt.linglit.linfw.core.Consumer;
import de.tudarmstadt.linglit.linfw.core.Producer;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;
import de.tudarmstadt.linglit.linfw.core.annotator.ParallelAnnotator;
import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.layer.WritableLayer;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.CoarsePartOfSpeech;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.PartOfSpeech;

/**
 * This annotator creates word annotations from every text area that is not classified
 * as a punctuation is terms of the basic part-of-speech scheme.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 */
@Producer(type=Word.class)
@Consumer(types={PartOfSpeech.class})
public class NonPunctuationWordAnnotator extends ParallelAnnotator<Word, PartOfSpeech> {

	/**
	 * Creates a new annotator for the given executor.
	 * 
	 * @param executor executor to execute this annotator on
	 */
	public NonPunctuationWordAnnotator(AnnotatorExecutor executor) {
		super(PartOfSpeech.class, executor, Word.class);
	}

	@Override
	protected void process(CharSequence document, LayerProvider layerprovider,
			Annotation<PartOfSpeech> annotation, WritableLayer<Word> layer) {
		if(annotation.value().coarseForm()!=CoarsePartOfSpeech.PUNCTUATION)
			layer.write(new Annotation<>(annotation.area(), 
					new Word(annotation.area().coveredText().toString().toLowerCase()), Word.class));
	}

}
