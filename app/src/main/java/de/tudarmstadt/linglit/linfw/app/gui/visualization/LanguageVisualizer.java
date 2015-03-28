package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.tika.metadata.Metadata;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.linguistics.ISO6391;
import de.tudarmstadt.linglit.linfw.linguistics.ISO6393;
import de.tudarmstadt.linglit.linfw.linguistics.ISO6395;
import de.tudarmstadt.linglit.linfw.linguistics.Languages;
import de.tudarmstadt.linglit.linfw.linguistics.Languages.Language;

public class LanguageVisualizer  implements Visualizer<Language>  {

	@Override
	public List<Visualization> visualize(Annotation<? extends Language> annotation) {
		List<Visualization> result = new ArrayList<Visualization>();
		JEditorPane codePane = new JEditorPane();
		codePane.setContentType("text/html"); //$NON-NLS-1$
		JEditorPane nativePane = new JEditorPane();
		JEditorPane localPane = new JEditorPane();
		JEditorPane typePane = new JEditorPane();
		JEditorPane scopePane = new JEditorPane();
		JTree treePane = new JTree();

		Optional<ISO6393> iso6393 = Languages.forISO6393Code(annotation.value().iso6393code());
		if(iso6393.isPresent()) {
			codePane.setText("<html><tt>"+iso6393.get().code().toString()+"</tt></html>"); //$NON-NLS-1$ //$NON-NLS-2$
			result.add(new Visualization(Messages.getString("LanguageVisualizer.3"), new ImageIcon("icons/language.png"), codePane)); //$NON-NLS-1$ //$NON-NLS-2$
			typePane.setText(iso6393.get().type().toString());
			result.add(new Visualization(Messages.getString("LanguageVisualizer.5"), new ImageIcon("icons/language.png"), typePane)); //$NON-NLS-1$ //$NON-NLS-2$
			scopePane.setText(iso6393.get().scope().toString());
			result.add(new Visualization(Messages.getString("LanguageVisualizer.7"), new ImageIcon("icons/language.png"), scopePane)); //$NON-NLS-1$ //$NON-NLS-2$

			Optional<ISO6391> iso6391 = iso6393.get().iso6391Equivalent();
			if(iso6391.isPresent()) {
				nativePane.setText(iso6391.get().nativeName());
				localPane.setText(iso6391.get().localName());

				result.add(new Visualization(Messages.getString("LanguageVisualizer.9"), new ImageIcon("icons/language.png"), nativePane)); //$NON-NLS-1$ //$NON-NLS-2$
				result.add(new Visualization(Messages.getString("LanguageVisualizer.11"), new ImageIcon("icons/language.png"), localPane)); //$NON-NLS-1$ //$NON-NLS-2$

				Deque<ISO6395> languageHierarchy = new LinkedList<ISO6395>();
				Optional<ISO6395> nextFamily = Optional.of(iso6391.get().family());
				DefaultMutableTreeNode root = new DefaultMutableTreeNode();
				DefaultMutableTreeNode current = root;
				while(nextFamily.isPresent()) {
					languageHierarchy.push(nextFamily.get());
					nextFamily = nextFamily.get().broader();	
				}

				
				while(!languageHierarchy.isEmpty()) {
					ISO6395 family = languageHierarchy.pop();
					current.setUserObject(family);
					
					if(!languageHierarchy.isEmpty()) {
						DefaultMutableTreeNode next = new DefaultMutableTreeNode();
						current.add(next);
						current = next;
					}
				}
				final DefaultMutableTreeNode next = new DefaultMutableTreeNode(iso6391.get().localName());
				next.setAllowsChildren(false);
				current.add(next);
				treePane.setModel(new DefaultTreeModel(root));
				result.add(new Visualization(Messages.getString("LanguageVisualizer.13"), new ImageIcon("icons/language.png"), treePane)); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}

		return result;
	}
	
	@Override
	public LayerVisualization visualizeLayer() {
		return LayerVisualization.EMPTY;
	}

	@Override
	public Class<Language> type() {
		return Language.class;
	}
}
