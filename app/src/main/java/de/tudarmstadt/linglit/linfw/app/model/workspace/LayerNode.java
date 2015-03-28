package de.tudarmstadt.linglit.linfw.app.model.workspace;

import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class LayerNode<A> implements TreeNode, WorkspaceResource {
	private final ReadableLayer<A> layer;
	private final DocumentNode document;
	
	public ReadableLayer<A> layer() {
		return this.layer;
	}

	@Override
	public Enumeration<?> children() {
		return Collections.emptyEnumeration();
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		return -1;
	}

	@Override
	public DocumentNode getParent() {
		return this.document;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	
	public LayerNode(DocumentNode document, ReadableLayer<A> layer) {
		this.document = document;
		this.layer = layer;
	}

	@Override
	public boolean changed() {
		return false;
	}

	@Override
	public URI uri() {
		return URI.create(this.document.uri().toString()+"#"+this.layer.type().toString()); //$NON-NLS-1$
	}

	@Override
	public void delete() {
		this.document.removeForType(this.layer.type());
	}
}
