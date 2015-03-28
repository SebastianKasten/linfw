package de.tudarmstadt.linglit.linfw.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingWorker;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

import de.tudarmstadt.linglit.linfw.app.model.AnnotationHandler;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotation.AnnotationPredicate;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class CorpusController<A> extends SwingWorker<Void, CorpusContext<A>> {
	private final Class<A> type;
	private final Iterable<DocumentNode> documents;
	private final Predicate<Annotation<A>> predicate;
	private final List<AnnotationHandler<A>> handlers;
	private final List<LayerController<A>> layerControllers = new ArrayList<>();

	@Override
	protected Void doInBackground() throws Exception {
		for(DocumentNode document : this.documents) {
			
			ReadableLayer<A> layer = document.layerManager().forType(this.type);
			CharSequence text = document.text();
			Iterator<Annotation<A>> iterator = layer.arbitrary();
			while(iterator.hasNext() && !isCancelled())
				publish(new CorpusContext<A>(text, layer, iterator.next()));
		}
		
		return null;
	}
	
	public void cancelAll(boolean mayInterruptIfRunning) {
		cancel(mayInterruptIfRunning);
		for(LayerController<A> controller : layerControllers)
			controller.cancel(mayInterruptIfRunning);
	}
	
	public CorpusController(Iterable<DocumentNode> documents, Class<A> type, Predicate<Annotation<A>> predicate, 
			List<AnnotationHandler<A>> handlers) {
		this.documents = documents;
		this.type = type;
		this.predicate = predicate;
		this.handlers = handlers;
	}

	@Override
	protected void done() {
		for(AnnotationHandler<A> handler : this.handlers)
			handler.finish(this);
	}

	@Override
	protected void process(List<CorpusContext<A>> chunks) {
		Map<ReadableLayer<A>,CharSequence> texts = new HashMap<>();
		ListMultimap<ReadableLayer<A>, Annotation<A>> annotations = ArrayListMultimap.create();
		
		for(CorpusContext<A> chunk : chunks) {
			texts.put(chunk.layer(), chunk.text());
			annotations.put(chunk.layer(), chunk.annotation());
		}
		
		for(AnnotationHandler<A> handler : this.handlers)
			for(ReadableLayer<A> layer : annotations.keySet())
				handler.process(this, layer, texts.get(layer), annotations.get(layer));
	}

}
