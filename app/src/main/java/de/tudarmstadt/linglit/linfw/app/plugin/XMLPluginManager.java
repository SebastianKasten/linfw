package de.tudarmstadt.linglit.linfw.app.plugin;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;

import org.xml.sax.SAXException;

import com.google.common.collect.ImmutableSet;

import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;

public class XMLPluginManager implements PluginManager {
	private final Set<Plugin> plugins;
	private final MultiClassLoader classloader = new MultiClassLoader();

	/* (non-Javadoc)
	 * @see de.tudarmstadt.linglit.linfw.core.plugin.PluginManager#plugins()
	 */
	@Override
	public Set<Plugin> plugins() {
		return this.plugins;		
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.linglit.linfw.core.plugin.PluginManager#classLoader()
	 */
	@Override
	public MultiClassLoader classLoader() {
		return this.classloader;
	}

	public XMLPluginManager(Path directory, AnnotatorExecutor executor, Plugin builtIn) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SAXException {
		HashMap<String, Plugin> idMapping = new HashMap<String, Plugin>();
		idMapping.put(builtIn.id(), builtIn);

		DirectoryStream<Path> pathStream = Files.newDirectoryStream(directory);
		for(Path path : pathStream) {
			String filename = path.getFileName().toString();
			if(filename.endsWith(".xml")) {
				String id = filename.substring(0, filename.length()-4);
				if(!idMapping.containsKey(id)) {
					if(!idMapping.containsKey(id)) {
						Plugin plugin = XMLPlugin.getInstance(directory, id, executor, this);
						idMapping.put(id, plugin);
						for(Plugin dependency : plugin.dependencies())
							if(!idMapping.containsKey(dependency.id()))
								idMapping.put(dependency.id(), dependency);
					}
				}
			}
		}
		this.plugins = ImmutableSet.copyOf(idMapping.values());
	}
}
