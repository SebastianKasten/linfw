package de.tudarmstadt.linglit.linfw.app.model.workspace;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class WorkspaceTreeModel<T extends WorkspaceNode> extends DefaultTreeModel {
	public WorkspaceTreeModel(T root) {
		super(root, false);
		root.setModel(this);
	}

	@Override
	public T getRoot() {
		return (T) super.getRoot();
	}
}
