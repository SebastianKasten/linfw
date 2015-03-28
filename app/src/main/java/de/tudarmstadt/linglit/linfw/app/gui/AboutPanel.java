package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutPanel extends JPanel {
	public AboutPanel() {
		setLayout(new BorderLayout());
		add(new JLabel(new ImageIcon("icons/linfw.png")), BorderLayout.CENTER);
	}
}
