package de.tudarmstadt.linglit.linfw.app;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class CorpusContext<A> {
	private final CharSequence text;
	private final ReadableLayer<A> layer;
	private final Annotation<A> annotation;
	
	public CharSequence text() {
		return this.text;
	}
	
	public ReadableLayer<A> layer() {
		return this.layer;
	}
	
	public Annotation<A> annotation() {
		return this.annotation;
	}
	
	public CorpusContext(CharSequence text, ReadableLayer<A> layer,
			Annotation<A> annotation) {
		this.text = text;
		this.layer = layer;
		this.annotation = annotation;
	}
}
