package de.tudarmstadt.linglit.linfw.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import de.tudarmstadt.linglit.linfw.app.annotator.AnnotatorManager;
import de.tudarmstadt.linglit.linfw.app.model.workspace.Annotable;
import de.tudarmstadt.linglit.linfw.app.plugin.AnnotatorSupplier;

public class AnnotatorListModel extends AbstractListModel<AnnotatorSupplier> implements AnnotatorManager {
	private transient List<AnnotatorSupplier> list;
	private transient Map<Class<?>, AnnotatorSupplier> map;
	
	@Override
	public synchronized boolean valid() {
		for(AnnotatorSupplier supplier : list)
			for(Class<?> consumes : supplier.consumes())
				if(!supports(consumes)) return false;
		
		return true;
	}
	
	@Override
	public boolean supports(Class<?> type) {
		return this.map.containsKey(type);
	}
	
	@Override
	public Optional<AnnotatorSupplier> forType(Class<?> type) {
		return Optional.fromNullable(this.map.get(type));
	}
	
	@Override
	public synchronized boolean add(AnnotatorSupplier supplier) {
		if(this.map.containsKey(supplier.produces())) {
			AnnotatorSupplier other = this.map.get(supplier.produces());
			if(!other.equals(supplier)) {
				int response = JOptionPane.showConfirmDialog(null, "You are already using an annotator for type "
					+ supplier.produces().getSimpleName()+". Do you want to replace it?", "Replace annotator", 
					  JOptionPane.YES_NO_OPTION);
				if(response==JOptionPane.YES_OPTION) {
					remove(other);
					add(supplier);
				}
			}
		} else {
			this.list.add(supplier);
			this.map.put(supplier.produces(), supplier);
			fireIntervalAdded(this, this.list.size()-1, this.list.size()-1);
		}
		
		return valid();
	}
	
	@Override
	public synchronized boolean remove(AnnotatorSupplier supplier) {
		int index = this.list.indexOf(supplier);
		this.list.remove(supplier);
		this.map.remove(supplier.produces());
		fireIntervalRemoved(this, index, index);
		
		return valid();
	}
	
	@Override
	public AnnotatorSupplier getElementAt(int index) {
		return this.list.get(index);
	}

	@Override
	public int getSize() {
		return this.list.size();
	}

	@Override
	public boolean annotate(Annotable annotable) {
		if(valid()) {
			for(AnnotatorSupplier supplier : this.list)
				annotable.annotate(supplier.get());
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Class<?> type) {
		if(supports(type))
			return remove(forType(type).get());
		
		return valid();
	}
	
	
	private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
		stream.writeObject(this.list);
	}
	
	private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
		this.list = new ArrayList<AnnotatorSupplier>();
		this.map = new HashMap<Class<?>, AnnotatorSupplier>();
		
		List<AnnotatorSupplier> list = (List<AnnotatorSupplier>)stream.readObject();
		for(AnnotatorSupplier supplier : list)
			add(supplier);
	}

	public AnnotatorListModel() {
		this.list = new ArrayList<AnnotatorSupplier>();
		this.map = new HashMap<Class<?>, AnnotatorSupplier>();
	}
}
