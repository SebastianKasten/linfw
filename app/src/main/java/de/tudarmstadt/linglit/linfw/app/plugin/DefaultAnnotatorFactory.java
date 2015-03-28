package de.tudarmstadt.linglit.linfw.app.plugin;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Set;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;

import de.tudarmstadt.linglit.linfw.core.Consumer;
import de.tudarmstadt.linglit.linfw.core.Producer;
import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;


public class DefaultAnnotatorFactory extends AbstractAnnotatorSupplier {
	private final Class<? extends Annotator<?>> clazz;
	private final AnnotatorExecutor executor;
	
	@Override
	public Annotator<?> get() {
		try {
			return this.clazz.getConstructor(AnnotatorExecutor.class).newInstance(executor);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	private DefaultAnnotatorFactory(Class<? extends Annotator<?>> clazz, String name, Class<?> produces, Set<Class<?>> consumes,
			AnnotatorExecutor executor) {
		super(name,produces,consumes);
		this.clazz = clazz;
		this.executor = executor;
	}
	
	public static DefaultAnnotatorFactory fromConstructor(Class<? extends Annotator<?>> clazz, String name, AnnotatorExecutor executor) {
		Producer producer = clazz.getAnnotation(Producer.class);
		Consumer consumer = clazz.getAnnotation(Consumer.class);
		
		if(producer==null) throw new RuntimeException("Cannot load annotator "+clazz+". @Producer annotation is missing!");
		
		return new DefaultAnnotatorFactory(clazz, name, producer.type(), (consumer==null) ? Collections.<Class<?>>emptySet() : 
			ImmutableSet.copyOf(consumer.types()), executor);
	}

	@Override
	public AnnotatorSupplier initialized() {
		return DelegatingAnnotatorSupplier.initialize(this);
	}
}
