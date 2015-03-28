package de.tudarmstadt.linglit.linfw.app.plugin;

import java.util.Set;

import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;

public class DelegatingAnnotatorSupplier extends AbstractAnnotatorSupplier {
	private final Annotator<?> annotator;
	
	public DelegatingAnnotatorSupplier(Annotator<?> annotator, String name, Class<?> produces,
			Set<Class<?>> consumes) {
		super(name, produces, consumes);
		this.annotator = annotator;
	}
	
	public static DelegatingAnnotatorSupplier initialize(AnnotatorSupplier supplier) {
		return new DelegatingAnnotatorSupplier(supplier.get(), 
				supplier.name(), supplier.produces(), supplier.consumes());
	}

	@Override
	public Annotator<?> get() {
		return this.annotator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.annotator == null) ? 0 : this.annotator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DelegatingAnnotatorSupplier other = (DelegatingAnnotatorSupplier) obj;
		if (this.annotator == null) {
			if (other.annotator != null)
				return false;
		} else if (!this.annotator.equals(other.annotator))
			return false;
		return true;
	}

	@Override
	public AnnotatorSupplier initialized() {
		return this;
	}

}
