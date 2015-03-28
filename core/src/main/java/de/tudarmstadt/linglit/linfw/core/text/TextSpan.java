package de.tudarmstadt.linglit.linfw.core.text;

import com.google.common.base.Preconditions;

/**
 * A text span is an excerpt that covers one coherent 
 * span of text that has a start and an end index. It
 * includes every character 
 * @author Sebastian
 *
 */
public class TextSpan implements TextArea {
	private final CharSequence context;
	private final int start;
	private final int end;
	
	public int start() {
		return this.start;
	}

	public int end() {
		return this.end;
	}

	@Override
	public int length() {
		return this.end - this.start + 1;
	}
	
	public CharSequence context() {
		return this.context;
	}
	
	public CharSequence coveredText() {
		if(this.context==null)
			return ""; //$NON-NLS-1$
		
		return this.context.subSequence(this.start, this.end+1);
	}

	@Override
	public TextSpan subArea(int start, int end) {
		if(start==this.start && end==this.end) return this;
		
		return new TextSpan(this.context, this.start+start, this.start+end);
	}

	@Override
	public boolean covers(int index) {
		return index >= this.start && index <= this.end;
	}

	private TextSpan(CharSequence context, int start, int end) {
		this.context = context;
		this.start = start;
		this.end = end;
	}
	
	public static TextSpan between(CharSequence context, 
			int start, int end) {
		return new TextSpan(context, start, end);
	}
	
	public static TextSpan allOf(CharSequence context) {
		return new TextSpan(context, 0, context.length()-1);
	}
	
	public static TextSpan empty() {
		return new TextSpan(null,0,0);
	}

	@Override
	public TextSpan closure() {
		return this;
	}
	
	@Override
	public String toString() {
		return "TextSpan [start=" + this.start + ", end=" + this.end + "]";
	}
}
