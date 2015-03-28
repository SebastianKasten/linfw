package de.tudarmstadt.linglit.linfw.app.plugin;

import java.util.Set;

public interface PluginManager {

	public Set<Plugin> plugins();

	public ClassLoader classLoader();

}