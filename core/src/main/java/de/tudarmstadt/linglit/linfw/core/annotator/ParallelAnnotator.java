package de.tudarmstadt.linglit.linfw.core.annotator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.AlignedLayer;
import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.core.layer.WritableLayer;

public abstract class ParallelAnnotator<A, R> extends NonBlockingAnnotator<A> {
	private final Class<R> requiredAnnotationType;
	protected abstract void process(CharSequence document, LayerProvider layerprovider,
			Annotation<R> annotation, WritableLayer<A> layer);

	@Override
	public final ReadableLayer<A> process(final CharSequence document, final LayerProvider layerprovider) {
		final AlignedLayer<A,R> layer = new AlignedLayer<>(this.type());
		executor().execute(new Runnable() {

			@Override
			public void run() {
				final ReadableLayer<R> required = layerprovider
						.forType(
								ParallelAnnotator.this.requiredAnnotationType);
				final Iterator<Annotation<R>> iterator = required.sequential();

				List<ForkJoinTask<?>> forks = new ArrayList<>();

				while (iterator.hasNext()) {
					final Annotation<R> annotation = iterator.next();
					final WritableLayer<A> partition = layer.align(annotation);

					ForkJoinTask<?> subTask = ForkJoinTask.adapt(
							new Runnable() {

								@Override
								public void run() {
									process(document, layerprovider, annotation, partition);
									partition.close();
								}
							});
					subTask.fork();
					forks.add(subTask);
				}
				layer.prepareFinish();
				
				for(ForkJoinTask<?> task : forks)
					task.join();
			}
		},this);
		return layer;
	}

	protected ParallelAnnotator(final Class<R> required, final AnnotatorExecutor executor, final Class<A> type) {
		super(executor, type);
		this.requiredAnnotationType = required;
	}
}
