package de.tudarmstadt.linglit.linfw.core.layer;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;

public interface AppendableLayer<A> extends AutoCloseable {
	public int appendAnnotation(Annotation<A> annotation);
	public void close();
}
