package de.tudarmstadt.linglit.linfw.app.gui;

import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.action.BoundAction;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceTreeModel;

public interface WorkspaceView {
	public BoundAction newCorpusAction();
	public BoundAction newCorpusFromAnnotationsAction();
	public BoundAction newDocumentAction();
	public BoundAction deleteAction();
	
	public void setTreeModel(WorkspaceTreeModel model);
	public void addTreeListener(TreeSelectionListener listener);
	
	public Optional<TreePath> treeSelection();
}
