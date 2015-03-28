package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.CharMatcher;

import de.tudarmstadt.linglit.linfw.core.text.TextArea;
import de.tudarmstadt.linglit.linfw.core.text.TextAreas;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

public class HTMLUtils {
	public static String replaceWhitespaces(CharSequence sequence) {
		String text = sequence.toString();
		text = text.replace('\n', '\u21B5');
		text = text.replace('\r', '\u21B5');
		return text;
	}
	
	public static String styleTextArea(TextArea area) {
		CharSequence context = area.context();
		
		StringBuilder builder = new StringBuilder("<html>"); //$NON-NLS-1$
		List<CharSequence> spanTexts = new ArrayList<>();
		int totalLength = 0;
		final List<TextSpan> spans = TextAreas.splitSpans(area);
		for(TextSpan span : spans) {
			CharSequence text = span.coveredText();
			spanTexts.add(text);
			totalLength += text.length();
		}

		int i=0;
		for(CharSequence text : spanTexts) {
			if(totalLength>400) 
				text = text.subSequence(0, Math.min(text.length()-1,400/spanTexts.size()));
			CharSequence prequel;
			if(i==0) {
				int start = spans.get(i).start()-15;
				if(start<0) 
					prequel = context.subSequence(0, spans.get(i).start());
				else
					prequel = "..."+context.subSequence(start, spans.get(i).start()); //$NON-NLS-1$
			} else {
				int startNext = spans.get(i).start()-15;
				int endLast = spans.get(i-1).end()+15;
				if(startNext>endLast+1) {
					prequel = context.subSequence(spans.get(i-1).end()+1, endLast)+"..."+ //$NON-NLS-1$
							  context.subSequence(startNext, spans.get(i).start());
				} else {
					prequel = context.subSequence(spans.get(i-1).end()+1, spans.get(i).start());
				}
			}
			prequel = replaceWhitespaces(prequel);
			builder.append("<span style=\"color:gray\"><tt>"+prequel+"</tt></span>"); //$NON-NLS-1$ //$NON-NLS-2$
			builder.append("<sub style=\"color:white; font-family:sans-serif; background-color:black\"><small>&nbsp;"+spans.get(i).start()+"&nbsp;</small></sub> <tt>" //$NON-NLS-1$ //$NON-NLS-2$
					+text+"</tt> <sub style=\"color:white; font-family:sans-serif;  background-color:black\"><small>&nbsp;"+spans.get(i).end()+"&nbsp;</small></sub> "); //$NON-NLS-1$ //$NON-NLS-2$
			if(i==spanTexts.size()-1) {
				int end = spans.get(i).end()+15;
				CharSequence sequel;
				if(end>=context.length())
					sequel = context.subSequence(spans.get(i).end()+1, context.length());
				else
					sequel = context.subSequence(spans.get(i).end()+1, end)+"..."; //$NON-NLS-1$
				sequel = replaceWhitespaces(sequel);
				builder.append("<span style=\"color:gray\"><tt>"+sequel+"</tt></span>"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			i++;
		}
		builder.setLength(builder.length()-1);
		builder.append("</html>"); //$NON-NLS-1$
		return builder.toString();
	}
	private HTMLUtils() {}
}
