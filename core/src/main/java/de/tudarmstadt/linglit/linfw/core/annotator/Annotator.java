package de.tudarmstadt.linglit.linfw.core.annotator;

import de.tudarmstadt.linglit.linfw.core.layer.LayerProvider;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;


public interface Annotator<A> {
	public ReadableLayer<A> process(CharSequence document, LayerProvider layerprovider);
	public Class<A> type();
}
