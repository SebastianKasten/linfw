package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoundedRangeModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.action.BoundAction;

import com.google.common.base.Predicate;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.HTMLUtils;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.LayerVisualization;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualization;
import de.tudarmstadt.linglit.linfw.app.model.AnnotationTreeModel;
import de.tudarmstadt.linglit.linfw.app.model.DocumentModel;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.linguistics.partofspeech.PartOfSpeech;

public class DocumentPanel extends JPanel implements DocumentView {
	private final JToolBar mainToolbar = new JToolBar();
	private final JComboBox layerSelector = new JComboBox();
	private final JProgressBar renderingProgressBar = new JProgressBar();
	private final JSplitPane documentSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private final JTabbedPane layerTabs = new JTabbedPane();
	private final JTextPane editor = new JTextPane();
	private final JPanel annotationPanel = new JPanel();
	private final JToolBar annotationToolbar = new JToolBar();
	private final BoundAction selectAnnotation = new BoundAction(Messages.getString("DocumentPanel.3"),  //$NON-NLS-1$
			new ImageIcon("icons/annotation.png")); //$NON-NLS-1$
	private final JTabbedPane annotationTabs = new JTabbedPane();	
	private final List<TreeSelectionListener> annotationListSelectionListener = new ArrayList<>();
	
	private String selectedLayerTabName = Messages.getString("DocumentPanel.0"); //$NON-NLS-1$
	private String selectedAnnotationTabName = "";

	public DocumentPanel() {
		setLayout(new BorderLayout());
		add(this.mainToolbar, BorderLayout.NORTH);
		add(this.documentSplitPane, BorderLayout.CENTER);
		add(this.renderingProgressBar, BorderLayout.SOUTH);
		
		this.mainToolbar.setFloatable(false);
		this.mainToolbar.add(this.layerSelector);
		this.layerSelector.setRenderer(new ListCellRenderer<Class<?>>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends Class<?>> list,
					Class<?> value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if(value==null) return new JLabel("<html><span style=\"color:gray\">"+"None"+"</span>");
				DefaultListCellRenderer base = new DefaultListCellRenderer();
				JLabel label = (JLabel)base.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				label.setIcon(new ImageIcon("icons/document.png")); //$NON-NLS-1$
				label.setText(value.getSimpleName());
				
				return label;
			}
		});

		this.documentSplitPane.setDividerLocation(450);
		this.documentSplitPane.add(this.layerTabs);
		this.documentSplitPane.add(this.annotationPanel);
		
		this.layerTabs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedLayerTabName = layerTabs.getTitleAt(layerTabs.getSelectedIndex());
			}
		});
		
		this.editor.setDoubleBuffered(true);
		this.editor.setEditable(false);
		this.editor.getCaret().setVisible(true);
		this.editor.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		this.editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
		this.editor.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) { }

			@Override
			public void focusGained(FocusEvent e) {
				DocumentPanel.this.editor.getCaret().setVisible(true);
			}
		});
		
		this.annotationPanel.setLayout(new BorderLayout());
		this.annotationPanel.add(this.annotationToolbar, BorderLayout.NORTH);
		this.annotationPanel.add(this.annotationTabs, BorderLayout.CENTER);
		
		this.annotationToolbar.setFloatable(false);
		this.annotationToolbar.add(this.selectAnnotation);
		this.selectAnnotation.setEnabled(false);
		this.selectAnnotation.setShortDescription("Highlight the selected annotation in the current document");
	}

	@Override
	public void addAnnotationTreeSelectionListener(TreeSelectionListener listener) {
		this.annotationListSelectionListener.add(listener);
	}

	@Override
	public void addCaretListener(CaretListener listener) {
		this.editor.addCaretListener(listener);
	}

	@Override
	public synchronized void setRenderingProgressModel(final BoundedRangeModel model) {
		this.renderingProgressBar.setModel(model);
	}

	private static JPanel createVisualizationPanel(List<Visualization> visualizations) {
		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		for(int i=0; i<visualizations.size(); i++) {
			JLabel label  = new JLabel(visualizations.get(i).label());
			label.setIcon(visualizations.get(i).icon());
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
			panel.add(visualizations.get(i).value(),c);
			c.gridx = 0;
			c.gridy = i*2+1;
			c.gridwidth = 2;
			panel.add(new JSeparator(),c);
		}
		return panel;
	}

	@Override
	public synchronized void setLayerDocumentModel(final DocumentModel model) {
		this.editor.setDocument(model);
		if(this.layerTabs.indexOfTab(Messages.getString("DocumentPanel.0"))==-1) //$NON-NLS-1$
			this.layerTabs.insertTab(Messages.getString("DocumentPanel.0"), null, new JScrollPane(this.editor), null, 0); //$NON-NLS-1$
		int selectedLayerTab = this.layerTabs.indexOfTab(this.selectedLayerTabName);
		if(selectedLayerTab != -1 && selectedLayerTab < this.layerTabs.getTabCount())
			this.layerTabs.setSelectedIndex(selectedLayerTab);
	}
	
	@Override
	public synchronized void clearLayerDocumentModel() {
		if(this.layerTabs.indexOfTab(Messages.getString("DocumentPanel.0"))!=-1) //$NON-NLS-1$
			this.layerTabs.removeTabAt(this.layerTabs.indexOfTab(Messages.getString("DocumentPanel.0"))); //$NON-NLS-1$
	}

	@Override
	public synchronized void setLayerPropertyVisualizations(List<Visualization> visualization) {
		JPanel visualizationPanel = createVisualizationPanel(visualization);
		clearLayerPropertyVisualization();

		this.layerTabs.addTab(Messages.getString("DocumentPanel.1"), null, new JScrollPane(visualizationPanel),null); //$NON-NLS-1$
		int selectedLayerTab = this.layerTabs.indexOfTab(this.selectedLayerTabName);
		if(selectedLayerTab != -1 && selectedLayerTab < this.layerTabs.getTabCount())
			this.layerTabs.setSelectedIndex(selectedLayerTab);
	}

	@Override
	public synchronized void clearLayerPropertyVisualization() {
		if(this.layerTabs.indexOfTab(Messages.getString("DocumentPanel.1"))!=-1) //$NON-NLS-1$
			this.layerTabs.remove(this.layerTabs.indexOfTab(Messages.getString("DocumentPanel.1"))); //$NON-NLS-1$
	}

	@Override
	public synchronized void setLayerSelectorModel(ComboBoxModel<Class<?>> model) {
		this.layerSelector.setModel(model);
	}
	
	@Override
	public synchronized void addLayerSelectorSelectionListener(ItemListener listener) {
		this.layerSelector.addItemListener(listener);
	}
	
	@Override
	public synchronized void setSelectedLayer(Class<?> type) {
		if(type==null)
			this.layerSelector.setSelectedIndex(-1);
		else
			this.layerSelector.setSelectedItem(type);
	}
	
	@Override
	public Class<?> selectedLayer() {
		return (Class<?>)this.layerSelector.getSelectedItem();
	}
	
	@Override
	public synchronized void setLayerAnnotationsListModel(AnnotationTreeModel<?> model) {
		JXTreeTable annotationsList = new JXTreeTable(model);
	
		annotationsList.setTreeCellRenderer(new DefaultTreeCellRenderer() {

			@Override
			public Component getTreeCellRendererComponent(JTree arg0,
					Object arg1, boolean arg2, boolean arg3, boolean arg4,
					int arg5, boolean arg6) {
				if(arg1 instanceof Annotation) {
					JLabel label = (JLabel) super.getTreeCellRendererComponent(arg0, 
							HTMLUtils.styleTextArea(((Annotation<?>)arg1).area()), arg2, arg3, arg4, arg5, arg6);
					label.setIcon(new ImageIcon("icons/annotation.png")); //$NON-NLS-1$
					return label;
				}
				JLabel label = (JLabel) super.getTreeCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
				label.setIcon(new ImageIcon("icons/note.png")); //$NON-NLS-1$
				return label;
			}
		});
		annotationsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		for(TreeSelectionListener listener : this.annotationListSelectionListener)
			annotationsList.addTreeSelectionListener(listener);
		clearLayerAnnotationsListModel();
		this.layerTabs.addTab(Messages.getString("DocumentPanel.2"), null, new JScrollPane(annotationsList), null); //$NON-NLS-1$
		int selectedLayerTab = this.layerTabs.indexOfTab(this.selectedLayerTabName);
		if(selectedLayerTab != -1 && selectedLayerTab < this.layerTabs.getTabCount())
			this.layerTabs.setSelectedIndex(selectedLayerTab);
	}

	@Override
	public synchronized void clearLayerAnnotationsListModel() {
		if(this.layerTabs.indexOfTab(Messages.getString("DocumentPanel.2"))!=-1) //$NON-NLS-1$
			this.layerTabs.remove(this.layerTabs.indexOfTab(Messages.getString("DocumentPanel.2"))); //$NON-NLS-1$
	}

	@Override
	public synchronized void setAnnotationPropertyVisualizations(List<Visualization> visualization) {
		JPanel visualizationPanel = createVisualizationPanel(visualization);
		clearAnnotationPropertyVisualization();

		final JScrollPane scrollpane = new JScrollPane(visualizationPanel);
		this.annotationTabs.addTab(Messages.getString("DocumentPanel.1"), null, scrollpane, null); //$NON-NLS-1$
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				scrollpane.getVerticalScrollBar().setValue(0);
				scrollpane.getHorizontalScrollBar().setValue(0);
			}
		});
		
		int selectedAnnotationTab = this.layerTabs.indexOfTab(this.selectedAnnotationTabName);
		if(selectedAnnotationTab != -1 && selectedAnnotationTab < this.annotationTabs.getTabCount())
			this.annotationTabs.setSelectedIndex(selectedAnnotationTab);
	}

	@Override
	public synchronized void clearAnnotationPropertyVisualization() {
		this.selectedAnnotationTabName = Messages.getString("DocumentPanel.1");
		if(this.annotationTabs.indexOfTab(Messages.getString("DocumentPanel.1"))!=-1) //$NON-NLS-1$
			this.annotationTabs.remove(this.annotationTabs.indexOfTab(Messages.getString("DocumentPanel.1"))); //$NON-NLS-1$
	}

	@Override
	public void selectSpan(int start, int end) {
		this.layerTabs.setSelectedIndex(0);
		this.editor.requestFocusInWindow();
		this.editor.setCaretPosition(end+1);
		this.editor.moveCaretPosition(start);
		try {
			this.editor.scrollRectToVisible(this.editor.modelToView(start));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BoundAction selectAnnotationAction() {
		return this.selectAnnotation;
	}
}
