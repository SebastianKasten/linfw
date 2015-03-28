package de.tudarmstadt.linglit.linfw.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotation.AnnotationPredicate;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class CorpusFromAnnotation<A> {
	private final Iterable<DocumentNode> documents;
	private final Class<A> type;
	private final Path directory;
	private final Predicate<Annotation<A>> predicate;

	public void run() {
		Multimap<Object, String> values = ArrayListMultimap.create();
		Multiset<String> names = HashMultiset.create();
		
		for(DocumentNode document : documents) {
			ReadableLayer<A> layer = document.layerManager().forType(this.type);
			CharSequence text = document.text();

			Iterator<? extends Annotation<A>> iterator = layer.arbitrary();
			while(iterator.hasNext()) {
				Annotation<A> annotation = iterator.next();
				if(predicate.apply(annotation)) {
//					Path path = Paths.get(directory.toString(), annotation.value().toString().replaceAll("[^a-zA-Z0-9\\.\\-]", "_")+".txt");
					values.put(annotation.value(), annotation.area().coveredText().toString());
					names.add(annotation.value().toString());
				}
			}			
		}

		for(Object value : values.keySet()) {
			try {
				Path path;
				if(names.count(value.toString())==values.get(value).size())
					path = Paths.get(directory.toString(), value.toString().replaceAll("[^a-zA-Z0-9\\.\\-]", "_")+".txt");
				else {
					System.out.println(names.count(value.toString())+":"+values.get(value).size());
					path = Paths.get(directory.toString(), value.toString().replaceAll("[^a-zA-Z0-9\\.\\-]", "_")+"_"+value.hashCode()+".txt");
				}
				Files.createFile(path);
				Files.write(path, values.get(value));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public CorpusFromAnnotation(Iterable<DocumentNode> documents, Class<A> type, Predicate<Annotation<A>> predicate,
			Path directory) {
		super();
		this.documents = documents;
		this.type = type;
		this.directory = directory;
		this.predicate = predicate;
	}
}
