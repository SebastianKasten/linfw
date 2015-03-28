package de.tudarmstadt.linglit.linfw.app.plugin;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import de.tudarmstadt.linglit.linfw.app.features.FeatureGenerator;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualizer;
import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;


public class XMLPlugin implements Plugin {
	private static Map<String,XMLPlugin> pluginCache =
			new HashMap<>();
	
	private final String id;
	private final String name;
	private final String description;
	private final ClassLoader classLoader;
	private final ImmutableSet<AnnotatorSupplier> annotatorSuppliers;
	private final ImmutableSet<Visualizer<?>> visualizers;
	private final ImmutableSet<FeatureGenerator<?>> featureGenerators;
	private final ImmutableSet<Plugin> dependencies;

	@Override
	public String id() {
		return this.id;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public Optional<String> description() {
		return Optional.fromNullable(this.description);
	}

	@Override
	public ClassLoader classLoader() {
		return this.classLoader;
	}

	@Override
	public Set<AnnotatorSupplier> annotatorSuppliers() {
		return this.annotatorSuppliers;
	}

	@Override
	public Set<Visualizer<?>> visualizers() {
		return this.visualizers;
	}

	@Override
	public Set<FeatureGenerator<?>> featureGenerators() {
		return this.featureGenerators;
	}

	@Override
	public Set<Plugin> dependencies() {
		return this.dependencies;
	}

	private XMLPlugin(Path parentDirectory, String id, AnnotatorExecutor executor, 
			XMLPluginManager manager) throws SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.id = id;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		Document document = builder.parse(parentDirectory.resolve(id+".xml").toFile());

		ImmutableSet.Builder<Plugin> dependencies = ImmutableSet.builder();
		NodeList dependencyNodes = document.getElementsByTagName("dependency");
		for(int i=0; i<dependencyNodes.getLength(); i++)
			if(dependencyNodes.item(i).getNodeType()==Node.ELEMENT_NODE) {
				final Element node = (Element)dependencyNodes.item(i);

				XMLPlugin dependentPlugin =XMLPlugin.getInstance(parentDirectory, node.getTextContent(), executor, manager);
				dependencies.add(dependentPlugin);

			}
		this.dependencies = dependencies.build();

		this.name = document.getElementsByTagName("name").item(0).getTextContent();
		this.description = document.getElementsByTagName("description").item(0).getTextContent();

		String jarFilename = document.getElementsByTagName("jar").item(0).getTextContent();
		ClassLoader cl = new PluginClassLoader(this, new URL[]{parentDirectory.resolve(jarFilename).toUri().toURL()});
		//		NodeList annotationNodes = document.getElementsByTagName("annotation");
		//		for(int i=0; i<annotationNodes.getLength(); i++) {
		//			if(annotationNodes.item(i).getNodeType()==Node.ELEMENT_NODE) {
		//				Element node = (Element)annotationNodes.item(i);
		//				String className = node.getAttribute("class");
		////				cl.loadClass(className);
		//			}
		//		}

		final ImmutableSet.Builder<AnnotatorSupplier> annotatorFactories = ImmutableSet.builder();
		final Node annotatorRoot = document.getElementsByTagName("annotators").item(0);
		if(annotatorRoot!=null) {
			NodeList annotatorNodes = annotatorRoot.getChildNodes();
			for(int i=0; i<annotatorNodes.getLength(); i++) {
				if(annotatorNodes.item(i).getNodeType()==Node.ELEMENT_NODE) {
					Element node = (Element)annotatorNodes.item(i);
					if(node.getTagName().equals("annotator")) {
						String className = node.getAttribute("class");
						final String name = node.getTextContent();

						annotatorFactories.add(DefaultAnnotatorFactory.fromConstructor((Class<? extends Annotator<?>>)cl.loadClass(className),name,executor));
					} if(node.getTagName().equals("factory")) {
						String className = node.getAttribute("class");
						final String name = node.getTextContent();

						try {
							annotatorFactories.add((AnnotatorSupplier)cl.loadClass(className).getConstructor(AnnotatorExecutor.class).newInstance(executor));
						} catch (IllegalArgumentException
								| InvocationTargetException
								| NoSuchMethodException | SecurityException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		final ImmutableSet.Builder<Visualizer<?>> visualizers = ImmutableSet.builder();
		final Node visualizersRoot = document.getElementsByTagName("visualizers").item(0);
		if(visualizersRoot!=null) {
			final NodeList visualizerNodes = visualizersRoot.getChildNodes();
			for(int i=0; i<visualizerNodes.getLength(); i++) {
				if(visualizerNodes.item(i).getNodeType()==Node.ELEMENT_NODE) {
					Element node = (Element)visualizerNodes.item(i);
					if(node.getTagName().equals("visualizer")) {
						String className = node.getAttribute("class");
						visualizers.add((Visualizer<?>)cl.loadClass(className).newInstance());
					}
				}
			}
		}

		final ImmutableSet.Builder<FeatureGenerator<?>> featureGenerators = ImmutableSet.builder();
		final Node featureGeneratorRoot = document.getElementsByTagName("featuregenerators").item(0);
		if(visualizersRoot!=null) {
			final NodeList featureGeneratorNodes = featureGeneratorRoot.getChildNodes();
			for(int i=0; i<featureGeneratorNodes.getLength(); i++) {
				if(featureGeneratorNodes.item(i).getNodeType()==Node.ELEMENT_NODE) {
					Element node = (Element)featureGeneratorNodes.item(i);
					if(node.getTagName().equals("featuregenerator")) {
						String className = node.getAttribute("class");
						featureGenerators.add((FeatureGenerator<?>)cl.loadClass(className).newInstance());
					}
				}
			}
		}

		this.annotatorSuppliers = annotatorFactories.build();
		this.visualizers = visualizers.build();
		this.featureGenerators = featureGenerators.build();
		this.classLoader = cl;
		manager.classLoader().add(this.classLoader);
	}

	@Override
	public String toString() {
		return "XMLPlugin [id=" + id + ", name=" + name + ", description="
				+ description + "]";
	}
	
	public static XMLPlugin getInstance(Path parentDirectory, String id, AnnotatorExecutor executor, 
			XMLPluginManager manager) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SAXException, IOException {
		if(!pluginCache.containsKey(id))
			pluginCache.put(id, new XMLPlugin(parentDirectory, id, executor, manager));
		
		return pluginCache.get(id);
	}
}
