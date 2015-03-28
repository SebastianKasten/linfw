package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.CaretListener;
import javax.swing.event.TreeSelectionListener;

import org.jdesktop.swingx.action.BoundAction;

import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualization;
import de.tudarmstadt.linglit.linfw.app.model.AnnotationTreeModel;
import de.tudarmstadt.linglit.linfw.app.model.DocumentModel;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;

public interface DocumentView {

	public void addAnnotationTreeSelectionListener(
			TreeSelectionListener listener);

	public void addCaretListener(CaretListener listener);

	void addLayerSelectorSelectionListener(ItemListener listener);
	
	public void clearAnnotationPropertyVisualization();
	
	public void clearLayerAnnotationsListModel();

	void clearLayerDocumentModel();

	public void clearLayerPropertyVisualization();

	public BoundAction selectAnnotationAction();


	Class<?> selectedLayer();

	public void selectSpan(int start, int end);

	public void setAnnotationPropertyVisualizations(
			List<Visualization> visualization);

	void setLayerAnnotationsListModel(AnnotationTreeModel<?> model);

	public void setLayerDocumentModel(DocumentModel model);

	public void setLayerPropertyVisualizations(List<Visualization> visualization);

	void setLayerSelectorModel(ComboBoxModel<Class<?>> model);

	public void setRenderingProgressModel(BoundedRangeModel model);

	void setSelectedLayer(Class<?> layer);

}