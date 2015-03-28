package de.tudarmstadt.linglit.linfw.app.plugin;

public class Plugins {
	public static Plugin pluginOf(Class<?> annotatorClass) {
		ClassLoader cl = annotatorClass.getClassLoader();
		if(cl instanceof PluginClassLoader)
			return ((PluginClassLoader)cl).plugin();
		
		return null;
	}
	
	private Plugins() {
		// Do not instantiate
	}
}
