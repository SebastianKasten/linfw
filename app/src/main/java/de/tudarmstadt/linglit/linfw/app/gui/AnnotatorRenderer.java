package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import com.google.common.base.Joiner;

import de.tudarmstadt.linglit.linfw.app.model.AnnotatorListModel;
import de.tudarmstadt.linglit.linfw.app.plugin.AnnotatorSupplier;
import de.tudarmstadt.linglit.linfw.app.plugin.Plugin;

final class AnnotatorRenderer implements TreeCellRenderer, ListCellRenderer<AnnotatorSupplier> {
//	public static AnnotatorRenderer INSTANCE = new AnnotatorRenderer();
	private static final Joiner joiner = Joiner.on(" + ");
	
	private final AnnotatorListModel model;
	
	protected String highlight(Class<?> type, boolean alwaysGreen) {
		String color = alwaysGreen || model.supports(type) ? "green" : "red";
		return "<small style=\"color:"+color+";\">"+type.getSimpleName()+"</small>";
	}

	private final String consumeString(Set<Class<?>> consumes) {
		List<String> result = new ArrayList<>();
		for(Class<?> consumer : consumes) {
			final String highlighted = highlight(consumer,false);
			result.add(highlighted);
		}
		return this.joiner.join(result);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree arg0, Object arg1,
			boolean arg2, boolean arg3, boolean arg4, int arg5, boolean arg6) {
		if(arg1 instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) arg1;
			final DefaultTreeCellRenderer baseTreeRenderer = new DefaultTreeCellRenderer();
			JLabel baseLabel = (JLabel)baseTreeRenderer.getTreeCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
			if(node.getUserObject() instanceof Plugin) {
				Plugin plugin = (Plugin)node.getUserObject();
				baseLabel.setIcon(new ImageIcon("icons/plugin.png"));
				baseLabel.setText("<html><b>"+plugin.name()+"</b><br/><small>"
						+ (plugin.description().isPresent() ? plugin.description().get() : "")
						+ "</small></html>");
			} else if(node.getUserObject() instanceof AnnotatorSupplier) {
				AnnotatorSupplier supplier = (AnnotatorSupplier) node.getUserObject();
				changeLabel(baseLabel, supplier);
			} else {
				baseLabel.setText("Plugins");
				baseLabel.setIcon(new ImageIcon("icons/Folder.png"));
			}
			
			return baseLabel;
		}
		return null;
	}
	
	private void changeLabel(JLabel label, AnnotatorSupplier supplier) {
		boolean valid = true;
		for(Class<?> consumes : supplier.consumes())
			valid = valid && model.supports(consumes);
		
		String color = valid ? "black" : "gray";
		label.setText("<html><span style=\"color:"+color+"\">"+supplier.name()+"</span> "
				+ (supplier.consumes().isEmpty() ? "" : consumeString(supplier.consumes())) + " &#8594; "
				+ highlight(supplier.produces(),true)
				+ "</html>");
		if(valid)
			label.setIcon(new ImageIcon("icons/annotator-waiting.png"));
		else
			label.setIcon(new ImageIcon("icons/annotator-error.png"));
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends AnnotatorSupplier> list,
			AnnotatorSupplier value, int index, boolean isSelected,
			boolean cellHasFocus) {
		final DefaultListCellRenderer baseListRenderer = new DefaultListCellRenderer();
		JLabel baseLabel = (JLabel)baseListRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		changeLabel(baseLabel, value);
		
		return baseLabel;		
	}
	
	public AnnotatorRenderer(AnnotatorListModel model)  {
		this.model = model;
	}
}