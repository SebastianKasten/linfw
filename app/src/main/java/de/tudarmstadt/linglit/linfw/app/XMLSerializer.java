package de.tudarmstadt.linglit.linfw.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.core.text.TextAreas;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

public class XMLSerializer {
	public static String serialize(final CharSequence document, final ReadableLayer<?> layer) {
		Map<Object, Integer> mapping = new HashMap<>();
		XStream xstream = new XStream();
		Iterator<? extends Annotation<?>> iterator = layer.sequential();
		int i=0;
		int j=0;
		int start=0;
		StringBuilder valuesBuilder = new StringBuilder();
		StringBuilder docBuilder = new StringBuilder();
		while(iterator.hasNext()) {
			Annotation<?> annotation = iterator.next();
			if(!mapping.containsKey(annotation.value())) {
				valuesBuilder.append("<value id=\""+i+"\">"+xstream.toXML(annotation.value())+"</value>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				mapping.put(annotation.value(), Integer.valueOf(i));
				i++;
			}
			int valueId = mapping.get(annotation.value()).intValue();
			for(TextSpan span : TextAreas.splitSpans(annotation.area())) {
				docBuilder.append(document.subSequence(start, span.start()));
				docBuilder.append("<annotation id=\""+j+"\" value=\""+valueId+"\">"+span.coveredText()+"</annotation>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				start = span.end()+1;
			}
			j++;
		}
		return "<layer><values>"+valuesBuilder+"</values><document>"+docBuilder+"</document></layer>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
