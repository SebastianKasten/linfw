package de.tudarmstadt.linglit.linfw.app.plugin;
import java.util.Set;

import com.google.common.base.Supplier;

import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;


public interface AnnotatorSupplier extends Supplier<Annotator<?>> {
	public AnnotatorSupplier initialized();
	public Class<?> produces();
	public Set<Class<?>> consumes();
	
	public String name();
}
