package de.tudarmstadt.linglit.linfw.app.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import de.tudarmstadt.linglit.linfw.app.LayerController;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotation.AnnotationValueEquivalence;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class AnnotationTreeModel<A> extends AbstractTreeTableModel implements
	AnnotationHandler<A>  {
	private final AnnotationValueEquivalence<A> equivalence = new AnnotationValueEquivalence<>();
	private ListMultimap<A, Annotation<A>> data = ArrayListMultimap.create();

	private List<A> list = new ArrayList<>();
	private boolean finished = false;
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if(!this.finished) return null;
		if(column==0) return node;
		int number = (node instanceof Annotation) ? 1 : this.data.get((A)node).size(); 
		if(column==1) 
			return Integer.valueOf(number);
		if(column==2) {
			return Double.valueOf((double)number/this.data.values().size()* 100);
		}
		return Double.valueOf((double)this.data.values().size()/number);
	}

	@Override
	public Object getChild(Object node, int index) {
		if(!this.finished) return null;
		if(node==this.root) {
			if(index<this.list.size())
				return this.list.get(index);
		}
		if(node instanceof Annotation)
			return null;
		final List<Annotation<A>> children = this.data.get((A)node);
		if(index<children.size())
			return children.get(index);
		return null;
	}

	@Override
	public int getChildCount(Object node) {
		if(!this.finished) return 0;
		if(node==this.root)
			return this.list.size();
		else if(node instanceof Annotation)
			return 0;
		final List<Annotation<A>> children = this.data.get((A)node);
		return children.size();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if(!this.finished) return -1;
		if(parent==this.root)
			return this.list.indexOf(child);
		else if(parent instanceof Annotation)
			return -1;
		final List<Annotation<A>> children = this.data.get((A)parent);
		return children.indexOf(child);
	}

	@Override
	public synchronized void process(Object controller,
			ReadableLayer<A> layer, CharSequence text,
			List<Annotation<A>> annotations) {
		if(!this.finished)
			for(Annotation<A> annotation : annotations)
				this.data.put(annotation.value(),annotation);
	}

	@Override
	public synchronized void finish(final Object controller) {
		if(!this.finished) {
			this.finished = true;
			Multiset<A> occurrences = HashMultiset.create();
			for(Entry<A, Collection<Annotation<A>>> entry : this.data.asMap().entrySet())
				occurrences.add(entry.getKey(), entry.getValue().size());
			this.list.addAll(Multisets.copyHighestCountFirst(occurrences).elementSet());
			this.modelSupport.fireNewRoot();
		}
	}

	@Override
	public Class<?> getColumnClass(int column) {
		if(column==0)
			return Annotation.class;
		else
			return Integer.class;
	}

	public AnnotationTreeModel() {
		super(new Object());
	}

	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0: return "Value";
		case 1: return "Occurrences";
		case 2: return "Density [%]";
		case 3: return "Density [every nth]";
		default: return null;
		}
	}

}
