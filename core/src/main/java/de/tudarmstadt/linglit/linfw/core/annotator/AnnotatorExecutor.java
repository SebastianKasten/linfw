package de.tudarmstadt.linglit.linfw.core.annotator;



public interface AnnotatorExecutor {
	public <A> void execute(final Runnable runnable, final Annotator<A> annotator);
	public void stop();
}
