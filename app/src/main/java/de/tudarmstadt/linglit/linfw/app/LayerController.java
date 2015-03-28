package de.tudarmstadt.linglit.linfw.app;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import de.tudarmstadt.linglit.linfw.app.model.AnnotationHandler;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

/**
 * <p>The layer controller iterates in the background over a given
 * readable layer and delegates annotation tasks for every annotation.</p>
 * 
 * <p>It is an extension of the <code>SwingWorker</code> class and therefore
 * executes the tasks on the event dispatcher thread (EDT), i.e. you can make
 * changes to Swing components in the task implementations.</p>
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 * @param <A> annotation type
 */
public class LayerController<A> extends SwingWorker<Integer, Annotation<A>> {
	private final CharSequence text;
	private final ReadableLayer<A> layer;
	private final List<AnnotationHandler<A>> tasks;
	private final Predicate<Annotation<A>> predicate;
	
	@Override
	protected Integer doInBackground() throws Exception {
		Iterator<? extends Annotation<A>> iterator = this.layer.sequential();
		Integer annotationStart = Integer.valueOf(0);
		while(iterator.hasNext() && !isCancelled()) {
			final Annotation<A> annotation = iterator.next();
			if(this.predicate.apply(annotation))
				publish(annotation);
		}
		
		return annotationStart;
	}

	public LayerController(CharSequence text, ReadableLayer<A> layer, List<AnnotationHandler<A>> tasks) {
		this.text = text;
		this.layer = layer;
		this.tasks = tasks;
		this.predicate = Predicates.alwaysTrue();
	}

	public LayerController(CharSequence text, ReadableLayer<A> layer, AnnotationHandler<A>...tasks) {
		this.text = text;
		this.layer = layer;
		this.tasks = Arrays.asList(tasks);
		this.predicate = Predicates.alwaysTrue();
	}
	
	public LayerController(CharSequence text, ReadableLayer<A> layer, Predicate<Annotation<A>> predicate, List<AnnotationHandler<A>> tasks) {
		this.text = text;
		this.layer = layer;
		this.tasks = tasks;
		this.predicate = predicate;
	}

	public LayerController(CharSequence text, ReadableLayer<A> layer, Predicate<Annotation<A>> predicate, AnnotationHandler<A>...tasks) {
		this.text = text;
		this.layer = layer;
		this.tasks = Arrays.asList(tasks);
		this.predicate = predicate;
	}

	@Override
	protected void process(List<Annotation<A>> annotations) {
		for(AnnotationHandler<A> task : this.tasks) 
			task.process(this, this.layer, this.text, annotations);
	}

	@Override
	protected void done() {
		for(AnnotationHandler<A> task : this.tasks) 
			task.finish(this);
	}

}
