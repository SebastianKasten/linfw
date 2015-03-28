package de.tudarmstadt.linglit.linfw.app.plugin;

import java.util.Set;

public abstract class AbstractAnnotatorSupplier implements AnnotatorSupplier {

	protected final String name;
	protected final Class<?> produces;
	protected final Set<Class<?>> consumes;

	public AbstractAnnotatorSupplier(final String name,
			final Class<?> produces, final Set<Class<?>> consumes) {
		this.name = name;
		this.produces = produces;
		this.consumes = consumes;
	}

	@Override
	public Class<?> produces() {
		return this.produces;
	}

	@Override
	public Set<Class<?>> consumes() {
		return this.consumes;
	}

	@Override
	public String name() {
		return this.name;
	}

}