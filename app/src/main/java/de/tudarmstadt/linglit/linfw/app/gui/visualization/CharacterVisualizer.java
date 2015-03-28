package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Multisets;

import de.tudarmstadt.linglit.linfw.app.LayerController;
import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.annotation.Unicode;
import de.tudarmstadt.linglit.linfw.app.model.AnnotationHandler;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class CharacterVisualizer implements Visualizer<Unicode> {
	private static final Joiner joiner = Joiner.on(", "); //$NON-NLS-1$
	private static final Pattern punctuation = Pattern.compile("\\p{Punct}"); //$NON-NLS-1$
	@Override
	public List<Visualization> visualize(Annotation<? extends Unicode> annotation) {
		List<Visualization> result = new ArrayList<Visualization>();
		final char character = annotation.value().character();
		final int codepoint = annotation.value().codepoint();

		JTextPane nameField  = new JTextPane();
		nameField.setText(Character.getName(codepoint));
		result.add(new Visualization(Messages.getString("CharacterVisualizer.2"),new ImageIcon("icons/character.png"),nameField)); //$NON-NLS-1$ //$NON-NLS-2$

		JTextPane lowerField  = new JTextPane();
		lowerField.setText(""+Character.toLowerCase(character)); //$NON-NLS-1$
		result.add(new Visualization(Messages.getString("CharacterVisualizer.5"),new ImageIcon("icons/character.png"),lowerField)); //$NON-NLS-1$ //$NON-NLS-2$

		JTextPane upperField  = new JTextPane();
		upperField.setText(""+Character.toUpperCase(character)); //$NON-NLS-1$
		result.add(new Visualization(Messages.getString("CharacterVisualizer.8"),new ImageIcon("icons/character.png"),upperField)); //$NON-NLS-1$ //$NON-NLS-2$

		List<String> types = new ArrayList<String>();
		if(Character.isDigit(character)) types.add(Messages.getString("CharacterVisualizer.10")); //$NON-NLS-1$
		if(Character.isLetter(character)) types.add(Messages.getString("CharacterVisualizer.11")); //$NON-NLS-1$
		if(Character.isSpaceChar(character)) types.add(Messages.getString("CharacterVisualizer.12")); //$NON-NLS-1$
		if(Character.isWhitespace(character)) types.add(Messages.getString("CharacterVisualizer.13")); //$NON-NLS-1$
		if(Character.isIdeographic(codepoint)) types.add(Messages.getString("CharacterVisualizer.14")); //$NON-NLS-1$
		if(punctuation.matcher(""+character).matches()) types.add(Messages.getString("CharacterVisualizer.16")); //$NON-NLS-1$ //$NON-NLS-2$

		JTextPane typeField  = new JTextPane();
		typeField.setText(joiner.join(types));
		result.add(new Visualization(Messages.getString("CharacterVisualizer.17"),new ImageIcon("icons/character.png"),typeField)); //$NON-NLS-1$ //$NON-NLS-2$

		return result;
	}

	@Override
	public LayerVisualization visualizeLayer() {
		List<Visualization> result = new ArrayList<Visualization>();
		final PlaceholderVisualization alphabetVisualization = new PlaceholderVisualization(Messages.getString("CharacterVisualizer.19"), new ImageIcon("icons/character.png")); //$NON-NLS-1$ //$NON-NLS-2$
		result.add(alphabetVisualization);
		final PlaceholderVisualization entropyVisualization = new PlaceholderVisualization(Messages.getString("CharacterVisualizer.21"), new ImageIcon("icons/character.png")); //$NON-NLS-1$ //$NON-NLS-2$
		result.add(entropyVisualization);


		final Multiset<Character> characterset = HashMultiset.create();
		final Multiset<Character> uppercasecharacterset = HashMultiset.create();
		final Multiset<Character> lettercharacterset = HashMultiset.create();
		AnnotationHandler<Unicode> handler = new AnnotationHandler<Unicode>() {

			@Override
			public void process(Object controller,
					ReadableLayer<Unicode> layer, CharSequence text,
					List<Annotation<Unicode>> annotations) {
				for(Annotation<Unicode> annotation : annotations) {
					final char character = annotation.value().character();
					characterset.add(character);
					uppercasecharacterset.add(Character.toUpperCase(character));
					if(Character.isLetter(character)) lettercharacterset.add(Character.toUpperCase(character));	
				}
			}

			@Override
			public void finish(Object controller) {
				double totalEntropy = 0;
				double caseEntropy = 0;
				double letterEntropy = 0;

				int total = characterset.size();
				for(Entry<Character> entry : characterset.entrySet()) {
					double pi = (double)entry.getCount()/total;
					totalEntropy -= pi*(Math.log(pi)/Math.log(2)+1e-10); 
				}
				for(Entry<Character> entry : uppercasecharacterset.entrySet()) {
					double pi = (double)entry.getCount()/total;
					caseEntropy -= pi*(Math.log(pi)/Math.log(2)+1e-10); 
				}
				int totalLetters = lettercharacterset.size();
				for(Entry<Character> entry : lettercharacterset.entrySet()) {
					double pi = (double)entry.getCount()/totalLetters;
					letterEntropy -= pi*(Math.log(pi)/Math.log(2)+1e-10); 
				}
				
				final JTextPane alphabetField = new JTextPane();
				alphabetField.setText(joiner.join(characterset.elementSet()));
				
				final JTextPane entropyField = new JTextPane();
				entropyField.setText(String.format(Messages.getString("CharacterVisualizer.23"),totalEntropy,caseEntropy,letterEntropy)); //$NON-NLS-1$
				
				alphabetVisualization.setComponent(alphabetField);
				entropyVisualization.setComponent(entropyField);
			}
		};
		
		return new LayerVisualization(handler, result);
	}

	@Override
	public Class<Unicode> type() {
		return Unicode.class;
	}

}
