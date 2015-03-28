package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXBusyLabel;

public class PlaceholderVisualization extends Visualization {
	private final JPanel panel;
	
	public PlaceholderVisualization(String label, Icon icon) {
		super(label, icon, new JPanel(new GridLayout(1, 1)));
		this.panel = (JPanel)value();
		final JXBusyLabel placeholder = new JXBusyLabel();
		placeholder.setText(de.tudarmstadt.linglit.linfw.app.Messages.getString("PlaceholderVisualization.0")); //$NON-NLS-1$
		placeholder.setBusy(true);
		this.panel.add(placeholder);
	}

	public void setComponent(final JComponent component) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				panel.removeAll();
				panel.add(component);
				panel.revalidate();
				((JViewport)panel.getParent().getParent()).setViewPosition(new Point(0, 0));
			}
		});
	}
}
