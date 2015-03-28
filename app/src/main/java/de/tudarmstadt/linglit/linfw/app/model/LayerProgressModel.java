package de.tudarmstadt.linglit.linfw.app.model;

import java.util.List;

import javax.swing.DefaultBoundedRangeModel;

import de.tudarmstadt.linglit.linfw.app.LayerController;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class LayerProgressModel extends DefaultBoundedRangeModel implements AnnotationHandler<Object> {
	@Override
	public void process(Object controller,
			ReadableLayer<Object> layer, CharSequence text, List<Annotation<Object>> annotations) {
		int value = (int) (((double)layer.progress() / text.length())*100);
		setValue(value);
	}

	@Override
	public void finish(Object controller) {
		setValue(100);
	}
}
