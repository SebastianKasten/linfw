package de.tudarmstadt.linglit.linfw.core.annotation;

import de.tudarmstadt.linglit.linfw.core.text.TextArea;


public class Annotation<A> {
	private final TextArea area;
	private final A value;
	private final Class<A> type;
	
	/**
	 * Returns the area this annotation covers.
	 * 
	 * @return the area this annotation covers.
	 */
	public TextArea area() {
		return this.area;
	}

	/**
	 * Returns the annotated value.
	 * 
	 * @return the annotated value.
	 */
	public A value() {
		return this.value;
	}
	
	public Class<A> type() {
		return this.type;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Annotation [area=" + area + ", value=" + value
				+ "]";
	}
	
	/**
	 * Creates a new annotation with the given text area and annotation
	 * value.
	 * 
	 * @param area text area
	 * @param value value
	 */
	public Annotation(TextArea area, A value, Class<A> type) {
		super();
		this.area = area;
		this.value = value;
		this.type = type;
	}
}
