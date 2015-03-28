package de.tudarmstadt.linglit.linfw.app.annotation;

public class Metadata extends org.apache.tika.metadata.Metadata {
	
	@Override
	public int hashCode() {
		int hash = 0;
		for(String name : names())
			hash += get(name).hashCode();
		
		return hash;
	}
}
