package de.tudarmstadt.linglit.linfw.app.plugin;

import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {
	private final Plugin plugin;

	public Plugin plugin() {
		return this.plugin;
	}
	
	public PluginClassLoader(Plugin xmlPlugin, URL[] urls) {
		super(urls);
		plugin = xmlPlugin;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return super.findClass(name);
		} catch(ClassNotFoundException e2) {
			for(Plugin dependent : plugin.dependencies())
				try {
					return dependent.classLoader().loadClass(name);
				} catch (ClassNotFoundException e) {
					// Go on...
				}
		}

		throw new ClassNotFoundException(name);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return super.loadClass(name,false);
	}

	@Override
	public String toString() {
		return "Classloader of "+plugin+"@"+hashCode();
	}
}