package de.tudarmstadt.linglit.linfw.app.annotation;

public class Unicode {
	private final char character;
	private final int codepoint;
	
	public char character() {
		return this.character;
	}
	
	public int codepoint() {
		return this.codepoint;
	}
	
	public Unicode(final char character, final int codepoint) {
		this.character = character;
		this.codepoint = codepoint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codepoint;
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
		Unicode other = (Unicode) obj;
		if (codepoint != other.codepoint)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Codepoint [character=" + character + ", codepoint=" + codepoint
				+ "]";
	}
}
