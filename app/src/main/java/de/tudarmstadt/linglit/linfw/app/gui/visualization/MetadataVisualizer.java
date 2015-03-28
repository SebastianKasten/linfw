package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;

import org.apache.tika.metadata.TikaCoreProperties;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.annotation.Metadata;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class MetadataVisualizer implements Visualizer<Metadata> {

	@Override
	public List<Visualization> visualize(Annotation<? extends Metadata> annotation) {
		List<Visualization> result = new ArrayList<>();
		Metadata metadata = annotation.value();

		Date created = metadata.getDate(TikaCoreProperties.CREATED);
		if(created!=null) {
			JTextPane datefield = new JTextPane();
			datefield.setContentType("text"); //$NON-NLS-1$
			datefield.setEditable(false);
			datefield.setText(created.toLocaleString());
			result.add(new Visualization(Messages.getString("MetadataVisualizer.0"), new ImageIcon("icons/calendar.png"), datefield)); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Date modified = metadata.getDate(TikaCoreProperties.MODIFIED);
		if(modified!=null) {
			JTextPane modifiedfield = new JTextPane();
			modifiedfield.setContentType("text"); //$NON-NLS-1$
			modifiedfield.setEditable(false);
			modifiedfield.setText(created.toLocaleString());
			result.add(new Visualization(Messages.getString("MetadataVisualizer.2"), new ImageIcon("icons/calendar.png"), modifiedfield)); //$NON-NLS-1$ //$NON-NLS-2$
		}

		for(String name : metadata.names()) {
			JTextPane field = new JTextPane();
			field.setContentType("text"); //$NON-NLS-1$
			field.setEditable(false);
			field.setText(metadata.get(name));
			result.add(new Visualization(name, new ImageIcon("icons/note.png"), field)); //$NON-NLS-1$
		}

		return result;
	}

	@Override
	public Class<Metadata> type() {
		return Metadata.class;
	}

	@Override
	public LayerVisualization visualizeLayer() {
		return LayerVisualization.EMPTY;
	}

}
