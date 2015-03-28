package de.tudarmstadt.linglit.linfw.app.annotator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Collections;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.google.common.collect.Sets;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.annotation.FeatureVector;
import de.tudarmstadt.linglit.linfw.app.features.ExplicitFeatureGeneratorProvider;
import de.tudarmstadt.linglit.linfw.app.features.FeatureGenerator;
import de.tudarmstadt.linglit.linfw.app.features.FeatureGeneratorProvider;
import de.tudarmstadt.linglit.linfw.app.features.ImplicitFeatureGeneratorProvider;
import de.tudarmstadt.linglit.linfw.app.model.MainModel;
import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.plugin.AnnotatorSupplier;
import de.tudarmstadt.linglit.linfw.app.plugin.Plugin;
import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;

public class FeatureVectorAnnotatorFactory implements AnnotatorSupplier {
	private static class InitializedFeatureVectorAnnotatorFactory implements AnnotatorSupplier {
		private final ExplicitFeatureGeneratorProvider provider;
		private final AnnotatorExecutor executor;
		private final String name;

		@Override
		public Annotator<?> get() {
			return new FeatureVectorAnnotator(this.executor, this.provider);
		}

		@Override
		public AnnotatorSupplier initialized() {
			return this;
		}

		@Override
		public Class<?> produces() {
			return FeatureVector.class;
		}

		@Override
		public Set<Class<?>> consumes() {
			return this.provider.supportedTypes();
		}

		@Override
		public String name() {
			return this.name;
		}

		public InitializedFeatureVectorAnnotatorFactory(ExplicitFeatureGeneratorProvider provider, AnnotatorExecutor executor, String name) {
			this.provider = provider;
			this.executor = executor;
			this.name = name;
		}
	}

	private final MainModel model;

	public AnnotatorExecutor executor() {
		return this.model.executor();
	}

	@Override
	public Annotator<?> get() {
		throw new UnsupportedOperationException("This supplier must be initialized!");
	}

	@Override
	public Class<?> produces() {
		return FeatureVector.class;
	}

	@Override
	public Set<Class<?>> consumes() {
		return Collections.emptySet();
	}

	@Override
	public String name() {
		return "Feature generator";
	}

	private <T> void addFeatureGenerator(TreePath path, ExplicitFeatureGeneratorProvider provider) {
		FeatureGenerator<? super T> generator = (FeatureGenerator<? super T>)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
		Class<T> type = (Class<T>)((DefaultMutableTreeNode)path.getParentPath().getLastPathComponent()).getUserObject();
		
		provider.put(type, generator);
	}
	
	@Override
	public AnnotatorSupplier initialized() {
		ImplicitFeatureGeneratorProvider possibleProviders = new ImplicitFeatureGeneratorProvider();
		for(int i=0; i<this.model.corpus().get().annotators().getSize(); i++) {
			final DefaultMutableTreeNode typeNode = 
					new DefaultMutableTreeNode(this.model.corpus().get().annotators().getElementAt(i).produces());
			for(Plugin plugin : this.model.pluginManager().plugins())
				for(FeatureGenerator<?> generator : plugin.featureGenerators())
					possibleProviders.put(generator);
		}
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("All");
		for(int i=0; i<this.model.corpus().get().annotators().getSize(); i++) {
			final Class<?> type = this.model.corpus().get().annotators().getElementAt(i).produces();
			final DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(type);
			for(FeatureGenerator<?> generator : possibleProviders.get(type))
				typeNode.add(new DefaultMutableTreeNode(generator));
			root.add(typeNode);
		}
		
		DefaultTreeModel treemodel = new DefaultTreeModel(root);
		JTree tree = new JTree(treemodel);
		
		tree.setCellRenderer(new TreeCellRenderer() {

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value,
					boolean selected, boolean expanded, boolean leaf, int row,
					boolean hasFocus) {
				DefaultTreeCellRenderer base = new DefaultTreeCellRenderer();
				base.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
				Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
				if(userObject instanceof Class) {
					base.setIcon(new ImageIcon("icons/document.png")); //$NON-NLS-1$
					base.setText(((Class<?>)userObject).getSimpleName());
				}
				if(userObject instanceof FeatureGenerator) {
					base.setIcon(new ImageIcon("icons/featuregenerator.png")); //$NON-NLS-1$
					base.setText(((FeatureGenerator<?>)userObject).getClass().getSimpleName());
				}
				return base;
			}
		});
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Select the annotation layers you want to generate features from"),BorderLayout.NORTH);
		panel.add(new JScrollPane(tree),BorderLayout.CENTER);

		int response = JOptionPane.showConfirmDialog(null, panel,"Annotation layers",JOptionPane.OK_CANCEL_OPTION);
		if(response==JOptionPane.OK_OPTION) {
			ExplicitFeatureGeneratorProvider provider = new ExplicitFeatureGeneratorProvider();
			
			TreeSelectionModel selection = tree.getSelectionModel();
			TreePath[] paths = selection.getSelectionPaths();
			
			for(TreePath path : paths)
				if(((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject() instanceof FeatureGenerator) 
					addFeatureGenerator(path, provider);
			
			return new InitializedFeatureVectorAnnotatorFactory(provider, this.executor(), this.name());
		}
		return null;
	}

	public FeatureVectorAnnotatorFactory(MainModel model) {
		this.model = model;
	}

}
