package de.tudarmstadt.linglit.linfw.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Consumer {
	public Class<?>[] types(); 
}
