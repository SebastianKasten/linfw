package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.CoarsePartOfSpeech;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.GrammaticalCategory;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.PartOfSpeech;

public class PartOfSpeechVisualizer implements Visualizer<PartOfSpeech> {

	@Override
	public List<Visualization> visualize(Annotation<? extends PartOfSpeech> annotation) {
		List<Visualization> result = new ArrayList<Visualization>();
		PartOfSpeech pos = annotation.value();

		JTextPane nameField  = new JTextPane();
		nameField.setText(pos.toString());
		result.add(new Visualization(Messages.getString("PartOfSpeechVisualizer.0"),new ImageIcon("icons/note.png"),nameField)); //$NON-NLS-1$ //$NON-NLS-2$

		JTextPane coarseField  = new JTextPane();
		coarseField.setText(pos.coarseForm().toString());
		result.add(new Visualization(Messages.getString("PartOfSpeechVisualizer.2"),new ImageIcon("icons/note.png"),coarseField)); //$NON-NLS-1$ //$NON-NLS-2$

		if(pos.coarseForm()==CoarsePartOfSpeech.VERB) {
			JTextPane aspectField  = new JTextPane();
			aspectField.setText(pos.aspect().toString());
			result.add(new Visualization(Messages.getString("PartOfSpeechVisualizer.4"),new ImageIcon("icons/note.png"),aspectField)); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if(pos.coarseForm()==CoarsePartOfSpeech.ADJECTIVE || pos.coarseForm()==CoarsePartOfSpeech.ADVERB) {
			JTextPane degreeField  = new JTextPane();
			degreeField.setText(pos.degree().toString());
			result.add(new Visualization(Messages.getString("PartOfSpeechVisualizer.6"),new ImageIcon("icons/note.png"),degreeField)); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if(pos.number()!=GrammaticalCategory.Number.NONE) {
			JTextPane numberField  = new JTextPane();
			numberField.setText(pos.number().toString());
			result.add(new Visualization(Messages.getString("PartOfSpeechVisualizer.8"),new ImageIcon("icons/note.png"),numberField)); //$NON-NLS-1$ //$NON-NLS-2$
		}

		JTextPane classField  = new JTextPane();
		classField.setText(pos.isOpen() ? Messages.getString("PartOfSpeechVisualizer.10") : Messages.getString("PartOfSpeechVisualizer.11")); //$NON-NLS-1$ //$NON-NLS-2$
		result.add(new Visualization(Messages.getString("PartOfSpeechVisualizer.12"),new ImageIcon("icons/note.png"),classField)); //$NON-NLS-1$ //$NON-NLS-2$

		return result;
	}

	@Override
	public LayerVisualization visualizeLayer() {
		return LayerVisualization.EMPTY;
	}

	@Override
	public Class<PartOfSpeech> type() {
		return PartOfSpeech.class;
	}

}
