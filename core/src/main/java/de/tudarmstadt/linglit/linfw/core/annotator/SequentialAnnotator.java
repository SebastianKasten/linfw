package de.tudarmstadt.linglit.linfw.core.annotator;

import java.util.Iterator;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.AppendableLayer;
import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.core.layer.WritableLayer;

public abstract class SequentialAnnotator<A,R> extends SerialAnnotator<A> {
	private final Class<R> required;
	
	
	protected abstract void process(CharSequence document, LayerProvider layerprovider,
			Annotation<R> annotation, AppendableLayer<A> layer);
	
	public SequentialAnnotator(Class<R> required, AnnotatorExecutor executor, Class<A> type) {
		super(executor, type);
		this.required = required;
	}

	@Override
	protected void process(CharSequence text, LayerProvider layerprovider,
			AppendableLayer<A> layer) {
		ReadableLayer<R> requiredLayer = layerprovider.forType(this.required);
		Iterator<Annotation<R>> iterator = requiredLayer.sequential();
		while(iterator.hasNext())
			process(text, layerprovider, iterator.next(), layer);
	}

}
