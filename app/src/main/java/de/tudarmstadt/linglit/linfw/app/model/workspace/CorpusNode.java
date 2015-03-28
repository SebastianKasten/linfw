package de.tudarmstadt.linglit.linfw.app.model.workspace;

import java.util.Enumeration;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.tree.TreeNode;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.model.AnnotatorListModel;
import de.tudarmstadt.linglit.linfw.app.model.Corpus;

/**
 * A corpus node is a tree node in the workspace tree that
 * acts as a corpus.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
public interface CorpusNode extends TreeNode, Corpus, WorkspaceResource {
	/** {@inheritDoc} */
	@Override
	public Enumeration<? extends DocumentNode> children();
	
	/** {@inheritDoc} */
	@Override
	public DocumentNode getChildAt(int childIndex);
	
	/** {@inheritDoc} */
	@Override
	public WorkspaceNode getParent();
	
	
	public ComboBoxModel<Class<?>> typeSelectionModel();
	
	@Override
	public String name();
	@Override
	public AnnotatorListModel annotators();
	@Override
	public List<? extends DocumentNode> documents();
}
