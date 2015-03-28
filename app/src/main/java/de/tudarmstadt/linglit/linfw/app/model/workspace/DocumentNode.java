package de.tudarmstadt.linglit.linfw.app.model.workspace;

import java.util.Enumeration;

import javax.swing.ComboBoxModel;
import javax.swing.tree.TreeNode;

import de.tudarmstadt.linglit.linfw.app.model.CorpusDocument;
import de.tudarmstadt.linglit.linfw.app.model.LayerManager;

public interface DocumentNode extends TreeNode, CorpusDocument, WorkspaceResource {
	public Enumeration<LayerNode<?>> children();
	public LayerNode<?> getChildAt(int childIndex);
	public CorpusNode getParent();
	
	public String name();
	public CharSequence text();
	public LayerManager layerManager();
	public ComboBoxModel<Class<?>> typeSelectionModel();
}
