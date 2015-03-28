package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.model.AnnotationHandler;

public class Visualization {
	private final String id;
	private final String label;
	private final Icon icon;
	private final JComponent value;
	
	public String id() {
		if(this.id==null)
			return this.label;
		return this.id;
	}
	
	public String label() {
		return this.label;
	}
	
	public Icon icon() {
		return this.icon;
	}
	
	public JComponent value() {
		return this.value;
	}

	public Visualization(String id, String label, Icon icon, JComponent value) {
		this.id = id;
		this.label = label;
		this.icon = icon;
		this.value = value;
	}
	
	public Visualization(String label, Icon icon, JComponent value) {
		this(null,label,icon,value);
	}
	
}
