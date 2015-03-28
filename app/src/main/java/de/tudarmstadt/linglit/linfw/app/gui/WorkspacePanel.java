package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.action.BoundAction;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceTreeModel;

public class WorkspacePanel extends JPanel implements WorkspaceView {
	private final JToolBar toolbar = new JToolBar();
	private final JXTree tree = new JXTree();

	private final BoundAction newCorpusAction = new BoundAction(Messages.getString("WorkspacePanel.0"),  //$NON-NLS-1$
			"NEW_CORPUS", //$NON-NLS-1$
			new ImageIcon("icons/add-corpus.png")); //$NON-NLS-1$ 
	private final BoundAction newDocumentAction = new BoundAction(Messages.getString("WorkspacePanel.2"),  //$NON-NLS-1$
			"NEW_DOCUMENT", //$NON-NLS-1$
			new ImageIcon("icons/add-text.png")); //$NON-NLS-1$ 
	private final BoundAction deleteAction = new BoundAction(Messages.getString("WorkspacePanel.1"),  //$NON-NLS-1$
			"DELETE", //$NON-NLS-1$
			new ImageIcon("icons/delete.png")); //$NON-NLS-1$
	private final BoundAction corpusFromAnnotationsAction = new BoundAction(Messages.getString("WorkspacePanel.6"),"NEW_CORPUS_FROM_ANNOTATIONS"); //$NON-NLS-1$ //$NON-NLS-2$

	public BoundAction newCorpusAction() {
		return this.newCorpusAction;
	}
	
	public BoundAction newCorpusFromAnnotationsAction() {
		return this.corpusFromAnnotationsAction;
	}

	public BoundAction newDocumentAction() {
		return this.newDocumentAction;
	}

	@Override
	public BoundAction deleteAction() {
		return this.deleteAction;
	}

	public WorkspacePanel() {
		super();
		setLayout(new BorderLayout());

		add(this.toolbar, BorderLayout.NORTH);
		add(new JScrollPane(this.tree), BorderLayout.CENTER);
		
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.newCorpusAction);
		this.toolbar.add(this.newDocumentAction);
		this.toolbar.add(this.deleteAction);

		this.newCorpusAction.setEnabled(true);
		this.newCorpusAction.setShortDescription("Create an empty corpus in this workspace");
		this.newDocumentAction.setEnabled(false);
		this.newDocumentAction.setShortDescription("Add a document to the selected corpus");
		this.deleteAction.setEnabled(false);
		this.deleteAction.setShortDescription("Delete the selected resource");

		TreeCellRenderer treeRenderer = new TreeCellRenderer() {

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value,
					boolean selected, boolean expanded, boolean leaf, int row,
					boolean hasFocus) {
				DefaultTreeCellRenderer base = new DefaultTreeCellRenderer();
				base.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
				if(value instanceof WorkspaceNode) {
					base.setIcon(new ImageIcon("icons/Folder.png")); //$NON-NLS-1$
					base.setText(Messages.getString("MainFrame.9")); //$NON-NLS-1$
				}
				if(value instanceof CorpusNode) {
					base.setIcon(new ImageIcon("icons/corpus.png")); //$NON-NLS-1$
					base.setText(((CorpusNode)value).name());
				}
				else if(value instanceof DocumentNode) {
					base.setIcon(new ImageIcon("icons/text.png")); //$NON-NLS-1$
					base.setText(((DocumentNode)value).name());
				}
				else if(value instanceof LayerNode<?>) {
					base.setIcon(new ImageIcon("icons/document.png")); //$NON-NLS-1$
					base.setText(((LayerNode<?>)value).layer().type().getSimpleName());
				}
				return base;
			}
		};
		this.tree.setCellRenderer(treeRenderer);
	}

	@Override
	public void setTreeModel(WorkspaceTreeModel model) {
		this.tree.setModel(model);
	}

	@Override
	public void addTreeListener(TreeSelectionListener listener) {
		this.tree.addTreeSelectionListener(listener);
	}

	@Override
	public Optional<TreePath> treeSelection() {
		return Optional.fromNullable(this.tree.getSelectionPath());
	}

}
