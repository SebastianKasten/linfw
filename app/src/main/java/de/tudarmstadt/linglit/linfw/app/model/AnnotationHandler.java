package de.tudarmstadt.linglit.linfw.app.model;

import java.util.List;

import de.tudarmstadt.linglit.linfw.app.LayerController;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public interface AnnotationHandler<A> {
	public void process(Object controller,
			ReadableLayer<A> layer,
			CharSequence text, List<Annotation<A>> annotations);
	public void finish(Object controller);
}
