package de.tudarmstadt.linglit.linfw.app.annotation;

/**
 * <p>A token is a usually short span of text in a document
 * that is most likely between two whitespace characters.</p>
 * 
 * <p>Examples for tokens are words or punctuation characters.</p>
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
public class Token {
	private final String lexicalization;
	
	/**
	 * Returns the lexicalization of this token as String.
	 * This is usually the same text that is covered by the
	 * text area of the annotation.
	 * 
	 * @return the lexicalization of this token
	 */
	public String lexicalization() {
		return this.lexicalization;
	}

	/**
	 * Creates a new token with the given lexicalization, i.e.
	 * its String representation.
	 * 
	 * @param lexicalization String representation of this
	 * token.
	 */
	public Token(String lexicalization) {
		this.lexicalization = lexicalization;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.lexicalization == null) ? 0 : this.lexicalization.hashCode());
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
		Token other = (Token) obj;
		if (this.lexicalization == null) {
			if (other.lexicalization != null)
				return false;
		} else if (!this.lexicalization.equals(other.lexicalization))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [lexicalization=" + this.lexicalization + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
