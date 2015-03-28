package de.tudarmstadt.linglit.linfw.core.annotator;

import de.tudarmstadt.linglit.linfw.core.layer.AppendableLayer;
import de.tudarmstadt.linglit.linfw.core.layer.DefaultSequentialLayer;
import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

/**
 * <p>Implementations of this abstract annotator create annotations in a sequential way, i.e.
 * they can only append to an existing layer.</p>
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 * @param <A> type of the annotations this annotator creates
 */
public abstract class SerialAnnotator<A> extends NonBlockingAnnotator<A> {
	/**
	 * Creates annotations for the given text and writes them to an appendable layer.
	 * 
	 * @param text text to be annotated
	 * @param layerprovider layer provider
	 * @param layer appendable layer to be written on
	 */
	protected abstract void process(CharSequence text, LayerProvider layerprovider, AppendableLayer<A> layer);
	
	@Override
	public final ReadableLayer<A> process(final CharSequence document, final LayerProvider layerprovider) {
		final DefaultSequentialLayer<A> layer = new DefaultSequentialLayer<>(this.type());
		executor().execute(new Runnable() {

			@Override
			public void run() {
				process(document, layerprovider, layer);
				layer.close();
			}
		},this);
		return layer;
	}

	/** Creates a new serial annotator with the given executor.
	 * 
	 * @param executor executor this annotator processes on
	 * @param type type of the annotations this annotator creates
	 */
	public SerialAnnotator(final AnnotatorExecutor executor, final Class<A> type) {
		super(executor, type);
	}
}
