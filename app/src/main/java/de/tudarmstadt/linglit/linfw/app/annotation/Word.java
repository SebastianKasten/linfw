package de.tudarmstadt.linglit.linfw.app.annotation;

public class Word {
private final String lexicalization;
	
	public String lexicalization() {
		return this.lexicalization;
	}

	public Word(String lexicalization) {
		this.lexicalization = lexicalization;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lexicalization == null) ? 0 : lexicalization.hashCode());
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
		Word other = (Word) obj;
		if (lexicalization == null) {
			if (other.lexicalization != null)
				return false;
		} else if (!lexicalization.equals(other.lexicalization))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Word [lexicalization=" + lexicalization + "]";
	}
}
