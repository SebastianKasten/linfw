package de.tudarmstadt.linglit.linfw.core.layer;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;


public interface WritableLayer<A> extends AutoCloseable {
	public void write(Annotation<A> annotation);
	public void close();
}
