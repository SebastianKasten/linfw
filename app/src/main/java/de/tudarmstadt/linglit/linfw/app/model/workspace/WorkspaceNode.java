package de.tudarmstadt.linglit.linfw.app.model.workspace;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import com.google.common.base.Optional;

public abstract interface WorkspaceNode extends TreeNode, WorkspaceResource {
	public Optional<? extends WorkspaceTreeModel<? extends WorkspaceNode>> model();
	public void setModel(WorkspaceTreeModel model);
	
	public Enumeration<? extends CorpusNode> children();
	public CorpusNode getChildAt(int childIndex);
}
