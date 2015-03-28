package de.tudarmstadt.linglit.linfw.core.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.Lists;

public class MultiTextSpan implements TextArea {
	private final CharSequence context;
	private final TreeMap<Integer, TextSpan> contextIndex = new TreeMap<>();
	private final TreeMap<Integer, TextSpan> internalIndex = new TreeMap<>();
	private final int length;

	@Override
	public int length() {
		return this.length;
	}

	@Override
	public MultiTextSpan subArea(int start, int end) {
		if(start==end)
			return new MultiTextSpan(TextSpan.empty());
		
		List<TextSpan> newSpans = new ArrayList<>();
		final SortedMap<Integer, TextSpan> included = internalIndex.subMap(start, end);
		final Iterator<Entry<Integer, TextSpan>> iterator = included.entrySet().iterator();
		final TextSpan first = iterator.next().getValue();
		newSpans.add(first.subArea(start, Math.min(end,first.length())));
		while(iterator.hasNext()) {
			final Entry<Integer, TextSpan> next = iterator.next();
			final TextSpan span = next.getValue();
			final int spanStart = next.getKey();
			if(!iterator.hasNext())
				newSpans.add(span.subArea(0,end-spanStart));
			else
				newSpans.add(span);
		}
		return new MultiTextSpan(newSpans);
	}


	@Override
	public boolean covers(int index) {
		if(this.contextIndex.containsKey(index))
			return true;
		
		Entry<Integer,TextSpan> floor = this.contextIndex.floorEntry(index);
		if(floor==null) return false;
		return floor.getValue().covers(index);
	}

	@Override
	public TextSpan closure() {
		return TextSpan.between(this.context,
				this.contextIndex.firstKey().intValue(),
				this.contextIndex.lastEntry().getValue().end());
	}
	
	public String toString() {
		return this.internalIndex.values().toString();
	}
	
	public Iterable<TextSpan> spans() {
		return this.internalIndex.values();
	}

	public MultiTextSpan(Iterable<TextSpan> spans) {
		super();
		int length = 0;
		CharSequence context = null;
		for(TextSpan span : spans) {
			context = span.context();
			this.contextIndex.put(span.start(), span);
			this.internalIndex.put(length, span);
			length += span.length();
		}
		this.context = context;
		this.length = length;
	}
	
	public MultiTextSpan(TextSpan first, TextSpan...rest) {
		this(Lists.asList(first,rest));
	}

	@Override
	public CharSequence context() {
		return this.context;
	}

	@Override
	public CharSequence coveredText() {
		StringBuilder sb = new StringBuilder();
		for(TextSpan span : this.contextIndex.values())
			sb.append(span.coveredText());
		
		return sb;
	}
}
