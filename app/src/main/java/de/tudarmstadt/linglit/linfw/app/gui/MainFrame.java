package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXEditorPane;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTipOfTheDay;
import org.jdesktop.swingx.action.BoundAction;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.MultiTypeVisualizer;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualization;
import de.tudarmstadt.linglit.linfw.app.model.AnnotatorListModel;
import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.plugin.AnnotatorSupplier;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class MainFrame extends JXFrame {
	private static final long serialVersionUID = 382042931735998842L;

	private final List<Visualization> annotationViews = new ArrayList<>();
	private final List<Visualization> layerViews = new ArrayList<>();
	private final JMenuBar menu;
	private final JMenu fileMenu;
	private final JMenu helpMenu;
	private final JToolBar annotatorToolbar;
	private final JToolBar pluginToolbar;
	private final JSplitPane mainPanel;
	private final JSplitPane navigationPanel;
	private final WorkspacePanel workspacePanel;
	private final JPanel annotatorNavigation;
	private final JXStatusBar statusbar;
	private final JLabel positionLabel;
	private final JSplitPane annotatorPane;
	private final JTree pluginTree;
	private final JList<AnnotatorSupplier> annotatorList;
	private final DocumentPanel documentPanel;
	private final BoundAction startDocumentButton;
	private final BoundAction startCorpusButton;
	private final BoundAction addPluginButton;
	private final BoundAction addAnnotatorButton;
	private final BoundAction removeAnnotatorButton;
//	private final BoundAction refreshAction;
	private final BoundAction exportAction;
	private final BoundAction aboutAction;

	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setTitle("Linguistic Framework"); //$NON-NLS-1$

		this.workspacePanel = new WorkspacePanel();
		this.documentPanel = new DocumentPanel();
		
//		this.refreshAction = new BoundAction(Messages.getString("MainFrame.0"), new ImageIcon("icons/refresh.png")); //$NON-NLS-1$ //$NON-NLS-2$
		
		// Set up panels
		setLayout(new BorderLayout());
		this.mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.annotatorNavigation = new JPanel();
		this.annotatorNavigation.setLayout(new BorderLayout());
		this.navigationPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.navigationPanel.setDividerLocation(200);
		this.statusbar = new JXStatusBar();
		this.positionLabel = new JLabel();
		this.statusbar.add(this.positionLabel);
		this.mainPanel.add(this.navigationPanel);
		
		this.mainPanel.add(this.documentPanel);
		this.mainPanel.setDividerLocation(300);
		add(this.mainPanel, BorderLayout.CENTER);

		this.annotatorToolbar = new JToolBar();
		this.annotatorToolbar.setFloatable(false);
		this.startCorpusButton = new BoundAction(Messages.getString("MainFrame.4"),new ImageIcon("icons/book-cog.png")); //$NON-NLS-1$ //$NON-NLS-2$
		this.startCorpusButton.setEnabled(false);
		this.startCorpusButton.setShortDescription("Run all equipped annotators on the selected corpus");
		this.annotatorToolbar.add(this.startCorpusButton);
		this.startDocumentButton = new BoundAction(Messages.getString("MainFrame.6"),new ImageIcon("icons/document-cog.png")); //$NON-NLS-1$ //$NON-NLS-2$
		this.startDocumentButton.setEnabled(false);
		this.startDocumentButton.setShortDescription("Run all equipped annotators on the selected document");
		this.annotatorToolbar.add(this.startDocumentButton);
		this.pluginTree = new JTree((TreeModel)null);
		this.pluginTree.setCellRenderer(new AnnotatorRenderer(new AnnotatorListModel()));
		this.annotatorList = new JList<>();
		this.annotatorPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.annotatorPane.setDividerLocation(180);
		this.annotatorPane.add(new JScrollPane(this.annotatorList));
		this.annotatorPane.add(new JScrollPane(this.pluginTree));
		this.annotatorNavigation.add(this.annotatorToolbar,BorderLayout.NORTH);
		this.annotatorNavigation.add(this.annotatorPane, BorderLayout.CENTER);

		this.navigationPanel.add(this.workspacePanel);
		this.navigationPanel.add(this.annotatorNavigation);
		
		this.pluginToolbar = new JToolBar();
		this.pluginToolbar.setFloatable(false);
		this.addAnnotatorButton = new BoundAction(Messages.getString("MainFrame.13"),new ImageIcon("icons/annotator-add.png")); //$NON-NLS-1$ //$NON-NLS-2$
		this.addAnnotatorButton.setEnabled(false);
		this.addAnnotatorButton.setShortDescription("Equip the selected annotator for this corpus");
		this.addPluginButton = new BoundAction(Messages.getString("MainFrame.15"),new ImageIcon("icons/plugin-add.png")); //$NON-NLS-1$ //$NON-NLS-2$
		this.addPluginButton.setEnabled(false);
		this.addPluginButton.setShortDescription("Equip all annotators from the selected plugin");
		this.removeAnnotatorButton = new BoundAction(Messages.getString("MainFrame.17"),new ImageIcon("icons/annotator-delete.png")); //$NON-NLS-1$ //$NON-NLS-2$
		this.removeAnnotatorButton.setEnabled(false);
		this.removeAnnotatorButton.setShortDescription("Unequip the selected annotator");
		this.pluginToolbar.add(this.addPluginButton);
		this.pluginToolbar.add(this.addAnnotatorButton);
		this.annotatorNavigation.add(this.pluginToolbar, BorderLayout.SOUTH);
		this.annotatorToolbar.add(this.removeAnnotatorButton);
	
		this.exportAction = new BoundAction("Export feature vector...", "EXPORT");
		
		// Set up menu bar
		this.menu = new JMenuBar();
		this.fileMenu = new JMenu(Messages.getString("MainFrame.19")); //$NON-NLS-1$
		this.fileMenu.add(this.workspacePanel.newCorpusAction());
		this.fileMenu.add(this.workspacePanel.newDocumentAction());
		this.fileMenu.add(this.workspacePanel.deleteAction());
		this.fileMenu.add(new JSeparator());
		this.fileMenu.add(this.workspacePanel.newCorpusFromAnnotationsAction());
		this.fileMenu.add(new JSeparator());
		this.fileMenu.add(this.startCorpusButton);
		this.fileMenu.add(this.startDocumentButton);
//		this.fileMenu.add(new JSeparator());
//		this.fileMenu.add(this.refreshAction);
		this.fileMenu.add(new JSeparator());
		this.fileMenu.add(this.exportAction);
		
		this.aboutAction = new BoundAction(Messages.getString("MainFrame.2")); //$NON-NLS-1$
		this.aboutAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this,
						new AboutPanel(), Messages.getString("MainFrame.2"), //$NON-NLS-1$
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		this.helpMenu = new JMenu(Messages.getString("MainFrame.5")); //$NON-NLS-1$
		this.helpMenu.add(this.aboutAction);
		
		this.fileMenu.add(Messages.getString("MainFrame.21")); //$NON-NLS-1$
		this.menu.add(this.fileMenu);
		this.menu.add(this.helpMenu);
		
		setJMenuBar(this.menu);
		setStatusBar(this.statusbar);
	}
	
	public DocumentView documentView() {
		return this.documentPanel;
	}
	
	public WorkspaceView workspaceView() {
		return this.workspacePanel;
	}
	
	public AnnotatorSupplier annotatorSupplier() {
		return this.annotatorList.getSelectedValue();
	}
	
	public TreePath pluginTreeSelection() {
		return this.pluginTree.getSelectionPath();
	}
	
	public void addPluginTreeListener(final TreeSelectionListener listener) {
		this.pluginTree.addTreeSelectionListener(listener);
	}
	
	public void addAnnotatorListListener(final ListSelectionListener listener) {
		this.annotatorList.addListSelectionListener(listener);
	}
	
	public void setAnnotatorList(AnnotatorListModel annotatorList) {
		this.annotatorList.setCellRenderer(new AnnotatorRenderer(annotatorList));
		this.pluginTree.setCellRenderer(new AnnotatorRenderer(annotatorList));
		this.annotatorList.setModel(annotatorList);
	}

	public void addStartDocumentListener(final ActionListener listener) {
		this.startDocumentButton.addActionListener(listener);
	}

	public void enableStartDocumentButton() {
		this.startDocumentButton.setEnabled(true);
	}

	public void disableStartDocumentButton() {
		this.startDocumentButton.setEnabled(false);
	}

	public void addStartCorpusListener(final ActionListener listener) {
		this.startCorpusButton.addActionListener(listener);
	}


	public void enableStartCorpusButton() {
		this.startCorpusButton.setEnabled(true);
	}

	public void disableStartCorpusButton() {
		this.startCorpusButton.setEnabled(false);
	}
	
	public void addAddAnnotatorListener(final ActionListener listener) {
		this.addAnnotatorButton.addActionListener(listener);
	}
	
	public void addAddPluginListener(final ActionListener listener) {
		this.addPluginButton.addActionListener(listener);
	}
	
	public void addRemoveAnnotatorListener(final ActionListener listener) {
		this.removeAnnotatorButton.addActionListener(listener);
	}
	
	public void enableAddAnnotatorButton() {
		this.addAnnotatorButton.setEnabled(true);
	}
	
	public void enableAddPluginButton() {
		this.addPluginButton.setEnabled(true);
	}
	
	public void enableRemoveAnnotatorButton() {
		this.removeAnnotatorButton.setEnabled(true);
	}
	
	public void disableAddAnnotatorButton() {
		this.addAnnotatorButton.setEnabled(false);
	}
	
	public void disableAddPluginButton() {
		this.addPluginButton.setEnabled(false);
	}
	
	public void disableRemoveAnnotatorButton() {
		this.removeAnnotatorButton.setEnabled(false);
	}
	
	public void setPluginModel(TreeModel model) {
		this.pluginTree.setRootVisible(true);
		this.pluginTree.setModel(model);
	}
	
	public void enableExportAction() {
		this.exportAction.setEnabled(true);
	}
	
	public void disableExportAction() {
		this.exportAction.setEnabled(false);
	}
	
	public void addExportActionListener(ActionListener listener) {
		this.exportAction.addActionListener(listener);
	}

	private JPanel layoutDetailViews(List<Visualization> detailviews) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		for(int i=0; i<detailviews.size(); i++) {
			JLabel label  = new JLabel(detailviews.get(i).label());
			label.setIcon(detailviews.get(i).icon());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = i*2;
			c.weightx = 0.5;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.WEST;
			panel.add(label,c);
			c.gridx = 1;
			c.gridy = i*2;
			c.weightx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.WEST;
			panel.add(detailviews.get(i).value(),c);
			c.gridx = 0;
			c.gridy = i*2+1;
			c.gridwidth = 2;
			panel.add(new JSeparator(),c);
		}
		return panel;
	}
	
	public void setPositionStatus(int line, int column) {
		this.positionLabel.setText(Messages.getString("MainFrame.22")+line+Messages.getString("MainFrame.23")+column); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
