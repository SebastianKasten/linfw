package de.tudarmstadt.linglit.linfw.core.annotation;

import com.google.common.base.Equivalence;

public class AnnotationValueEquivalence<A> extends Equivalence<Annotation<A>> {
	
	@Override
	protected boolean doEquivalent(Annotation<A> a, Annotation<A> b) {
		return a.value().equals(b.value());
	}

	@Override
	protected int doHash(Annotation<A> t) {
		return t.value().hashCode();
	}

}
