package de.tudarmstadt.linglit.linfw.core.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Iterables;

public class TextAreas {
	public static boolean inside(TextArea inside, TextArea outside) {
		// Closures overlap? Not inside!
		if(inside.closure().start()<outside.closure().start()
				|| inside.closure().end()>outside.closure().end())
			return false;

		// Both areas are contagious text spans? Inside!
		if(inside.closure() == inside && outside.closure() == outside)
			return true;

		// Check each position
		for(int i=inside.closure().start(); i<=inside.closure().end(); i++)
			if(inside.covers(i) && !outside.covers(i)) return false;

		return true;
	}
	
	public static List<TextSpan> splitSpans(TextArea area) {
		if(area instanceof TextSpan) return Collections.singletonList((TextSpan)area);
		
		List<TextSpan> result = new ArrayList<TextSpan>();
//		if(area instanceof MultiTextSpan)
//			Iterables.addAll(result,((MultiTextSpan)area).spans());
//		else 
		{
			int start = -1;
			int end = -1;
			for(int i=area.closure().start(); i<=area.closure().end(); i++) {
				if(area.covers(i))
					if(start==-1) start = i;
					else end = i;
				else
					if(start > -1 && end > -1) {
						result.add(TextSpan.between(area.context(), start, end));
						start = -1;
						end = -1;
					}
			}
			if(start > -1 && end > -1) 
				result.add(TextSpan.between(area.context(), start, end));
		}
		
		return result;
	}
	
//	public static void main(String[] args) {
//		TextArea area = new MultiTextSpan(TextSpan.between(0, 10), TextSpan.between(20, 25));
//		for(TextSpan span : splitSpans(area))
//			System.out.println(span);
//	}
	
	private TextAreas() {}
}
