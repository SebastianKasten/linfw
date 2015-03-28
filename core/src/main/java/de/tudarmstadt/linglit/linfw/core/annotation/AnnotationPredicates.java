package de.tudarmstadt.linglit.linfw.core.annotation;

import java.util.Arrays;

import com.google.common.base.Predicate;

import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory.Aspect;

public class AnnotationPredicates {
//	@SafeVarargs
//	public static <A> AnnotationPredicate<A> or(final AnnotationPredicate<A> first, final AnnotationPredicate<A>...next) {
//		return new AnnotationPredicate<A>() {
//			@Override
//			public boolean accepts(CharSequence text, Annotation<A> annotation) {
//				if(first.accepts(text, annotation)) return true;
//				for(AnnotationPredicate<A> then : next)
//					if(then.accepts(text, annotation)) return true;
//				return false;
//			}
//		};
//	}
//
//	public static <A> AnnotationPredicate<A> and(final AnnotationPredicate<A> first, final AnnotationPredicate<A>...next) {
//		return new AnnotationPredicate<A>() {
//			@Override
//			public boolean accepts(CharSequence text, Annotation<A> annotation) {
//				if(!first.accepts(text, annotation)) return false;
//				for(AnnotationPredicate<A> then : next)
//					if(!then.accepts(text, annotation)) return false;
//				return true;
//			}
//		};
//	}
//
//	public static <A> AnnotationPredicate<A> not(final AnnotationPredicate<A> predicate) {
//		return new AnnotationPredicate<A>() {
//			@Override
//			public boolean accepts(CharSequence text, Annotation<A> annotation) {
//				return !predicate.accepts(text, annotation);
//			}
//		};
//	}
//	
//	public static <A> Predicate<Annotation<A>> all() {
//		return new AnnotationPredicate<A>() {
//			@Override
//			public boolean accepts(CharSequence text, Annotation<A> annotation) {
//				return true;
//			}
//		};
//	}
//	
//	public static <A> AnnotationPredicate<A> none() {
//		return new AnnotationPredicate<A>() {
//			@Override
//			public boolean accepts(CharSequence text, Annotation<A> annotation) {
//				return false;
//			}
//		};
//	}
//	
	private AnnotationPredicates() {}
}
