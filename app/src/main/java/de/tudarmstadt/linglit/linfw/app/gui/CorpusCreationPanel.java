package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import org.jdesktop.swingx.JXRadioGroup;

public class CorpusCreationPanel extends JPanel {
	private final JTextField nameField = new JTextField();
	private final JRadioButton allAnnotationOption = new JRadioButton("All annotations");
	private final JRadioButton selectedAnnotationOption = new JRadioButton("Selected annotations");
	private final JRadioButton documentOption = new JRadioButton("Current document");
	private final JRadioButton corpusOption = new JRadioButton("Whole corpus");
	
	public String corpusName() {
		return this.nameField.getText();
	}
	
	public boolean allAnnotations() {
		return this.allAnnotationOption.isSelected();
	}
	
	public boolean wholeCorpus() {
		return this.corpusOption.isSelected();
	}
	
	public CorpusCreationPanel(boolean selectedAnnotationEnabled, boolean documentEnabled) {
		setLayout(new GridLayout(3, 2));
		
		add(new JLabel("Name"));
		add(this.nameField);
		add(new JLabel("Annotations"));
		this.selectedAnnotationOption.setEnabled(selectedAnnotationEnabled);
		add(new JXRadioGroup<JRadioButton>(new JRadioButton[] {this.allAnnotationOption,this.selectedAnnotationOption}));
		add(new JLabel("Scope"));
		this.documentOption.setEnabled(documentEnabled);
		add(new JXRadioGroup<JRadioButton>(new JRadioButton[] {this.documentOption,this.corpusOption}));
		
		if(selectedAnnotationEnabled)
			this.selectedAnnotationOption.setSelected(true);
		else
			this.allAnnotationOption.setSelected(true);
		
		this.corpusOption.setSelected(true);
	}
}
