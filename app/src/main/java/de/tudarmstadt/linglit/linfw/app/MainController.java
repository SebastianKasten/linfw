package de.tudarmstadt.linglit.linfw.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.pdfbox.util.ExtensionFileFilter;

import com.google.common.base.CharMatcher;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.thoughtworks.xstream.XStream;

import de.tudarmstadt.linglit.linfw.app.annotation.FeatureVector;
import de.tudarmstadt.linglit.linfw.app.gui.CorpusCreationPanel;
import de.tudarmstadt.linglit.linfw.app.gui.MainFrame;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.LayerVisualization;
import de.tudarmstadt.linglit.linfw.app.gui.visualization.Visualization;
import de.tudarmstadt.linglit.linfw.app.model.AnnotationHandler;
import de.tudarmstadt.linglit.linfw.app.model.AnnotationTreeModel;
import de.tudarmstadt.linglit.linfw.app.model.LayerManager;
import de.tudarmstadt.linglit.linfw.app.model.MainModel;
import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceResource;
import de.tudarmstadt.linglit.linfw.app.model.workspace.directory.DirectoryCorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.directory.FileDocumentNode;
import de.tudarmstadt.linglit.linfw.app.plugin.AnnotatorSupplier;
import de.tudarmstadt.linglit.linfw.app.plugin.DelegatingAnnotatorSupplier;
import de.tudarmstadt.linglit.linfw.app.plugin.Plugin;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotation.AnnotationPredicate;
import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;
import de.tudarmstadt.linglit.linfw.core.annotator.AnnotatorExecutor;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class MainController {
	private final MainFrame frame;
	private final MainModel model;
	private final AnnotatorExecutor executor;

	private LayerController<?> layerController;
	private CorpusController<?> corpusController;

	public MainController(final MainModel model, final MainFrame frame) {
		this.frame = frame;
		this.model = model;
		this.executor = model.executor();

		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		for(Plugin plugin : model.pluginManager().plugins()) {
			final DefaultMutableTreeNode pluginNode = new DefaultMutableTreeNode(plugin);
			root.add(pluginNode);
			for(AnnotatorSupplier supplier : plugin.annotatorSuppliers())
				pluginNode.add(new DefaultMutableTreeNode(supplier));
		}

		DefaultTreeModel annotatorModel = new DefaultTreeModel(root);
		this.frame.setPluginModel(annotatorModel);
		this.frame.documentView().setRenderingProgressModel(model.progressModel());

		this.frame.documentView().addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				if(!model.document().isPresent()) return;

				int position = e.getDot();
				model.setPosition(position);
				frame.setPositionStatus(model.caretLine(), model.caretColumn());

				if(!model.layer().isPresent()) return;

				if(model.annotation().isPresent()) {
					frame.documentView().setAnnotationPropertyVisualizations(model.annotationVisualizations());
				} else
					frame.documentView().clearAnnotationPropertyVisualization();
				frame.documentView().selectAnnotationAction().setEnabled(model.annotation().isPresent() && model.document().isPresent());
			}
		});
		this.frame.documentView().addAnnotationTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				final Object selected = e.getPath().getLastPathComponent();
				if(model.annotationTreeModel().isPresent()) {
					if(selected instanceof Annotation)
						model.setAnnotation((Annotation<?>) selected);
				}
				if(model.annotation().isPresent()) {
					frame.documentView().setAnnotationPropertyVisualizations(model.annotationVisualizations());
				} else
					frame.documentView().clearAnnotationPropertyVisualization();
				frame.documentView().selectAnnotationAction().setEnabled(model.annotation().isPresent() && model.document().isPresent());
			}
		});
		this.frame.documentView().addAnnotationTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				List<Object> values = new ArrayList<>();
				if(model.selectedAnnotationValues().isPresent())
					values.addAll(model.selectedAnnotationValues().get());

				for(TreePath treepath : e.getPaths()) 
					if(e.isAddedPath(treepath))
						values.add(treepath.getLastPathComponent());
					else
						values.remove(treepath.getLastPathComponent());

				model.setSelectedAnnotationValues(values);
				System.out.println(values);
			}
		});
		this.frame.documentView().selectAnnotationAction().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.annotation().isPresent())
					frame.documentView().selectSpan(model.annotation().get().area().closure().start(), 
							model.annotation().get().area().closure().end());
			}
		});
		this.frame.documentView().addLayerSelectorSelectionListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					System.out.println(e);
					if(MainController.this.layerController!=null) MainController.this.layerController.cancel(true);
					if(MainController.this.corpusController!=null) MainController.this.corpusController.cancelAll(true);

					final AnnotationTreeModel annotationListModel = new AnnotationTreeModel();
					model.setAnnotationTreeModel(annotationListModel);
					frame.documentView().setLayerAnnotationsListModel(annotationListModel);
					model.setAnnotationType((Class<?>)e.getItem());
					List<AnnotationHandler> handlers = new ArrayList<>();
					handlers.add(annotationListModel);
					List<LayerVisualization> visualizations = model.layerVisualizations();
					ImmutableList.Builder<Visualization> visualizationRows = ImmutableList.builder();
					for(LayerVisualization layerVisualization : visualizations)
						visualizationRows.addAll(layerVisualization.rows());
					frame.documentView().setLayerPropertyVisualizations(visualizationRows.build());

					for(LayerVisualization visualization : visualizations)
						if(visualization.handler().isPresent())
							handlers.add(visualization.handler().get());

					if(model.document().isPresent()) {
						ReadableLayer<?> layerSelection = model.layerManager().get().forType((Class<?>)e.getItem());
						model.setLayer(layerSelection);

						frame.documentView().setLayerDocumentModel(model.styledDocument().get());

						handlers.add(model.progressModel());
						handlers.add(model.styledDocument().get());
						MainController.this.layerController = new LayerController(model.document().get().text(), 
								model.layer().get(),
								handlers);
						layerController.execute();
					} else {
						MainController.this.corpusController = new CorpusController(Collections.list(model.corpus().get().children()), 
								model.annotationType().get(), Predicates.<Annotation<Object>>alwaysTrue(), handlers);
						corpusController.execute();
					}
					frame.workspaceView().newDocumentAction().setEnabled(true);
					frame.workspaceView().deleteAction().setEnabled(true);				
				}
			}
		});
		this.frame.workspaceView().addTreeListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if(!frame.workspaceView().treeSelection().isPresent()) return;

				TreePath hierarchySelection = frame.workspaceView().treeSelection().get();
				Object lastComponent = hierarchySelection.getLastPathComponent();
				if(lastComponent instanceof WorkspaceNode) {
					model.clearCorpus();
					model.clearDocument();
					model.clearLayer();
					model.clearLayerManager();
					model.clearSelectedAnnotationValues();
					
					frame.disableStartCorpusButton();
					frame.disableStartDocumentButton();
					frame.workspaceView().deleteAction().setEnabled(false);
					frame.workspaceView().newDocumentAction().setEnabled(false);
					frame.disableExportAction();
				} else if(lastComponent instanceof CorpusNode) {
					final DirectoryCorpusNode corpusSelection = (DirectoryCorpusNode) lastComponent;

					if(MainController.this.layerController!=null) MainController.this.layerController.cancel(true);

					model.clearDocument();
					model.clearAnnotation();
					model.clearLayer();
					model.clearLayerManager();
					model.clearSelectedAnnotationValues();
					model.setCorpus(corpusSelection);

					if(model.annotationType().isPresent()) {
						Class<?> type = model.annotationType().get();
						frame.documentView().setSelectedLayer(null); // Enforce GUI update
						frame.documentView().setSelectedLayer(type);
					}
					frame.setAnnotatorList(model.corpus().get().annotators());
					frame.documentView().clearAnnotationPropertyVisualization();
					//					frame.documentView().clearLayerPropertyVisualization();
					frame.documentView().clearLayerDocumentModel();
					frame.documentView().setLayerSelectorModel(model.corpus().get().typeSelectionModel());
					if(model.corpus().get().annotators().getSize()>0 && model.corpus().get().annotators().valid())
						frame.enableStartCorpusButton();
					else
						frame.disableStartCorpusButton();
					frame.disableStartDocumentButton();
					frame.workspaceView().newDocumentAction().setEnabled(true);
					frame.workspaceView().deleteAction().setEnabled(true);
					frame.disableExportAction();
					for(FileDocumentNode document : Collections.list(corpusSelection.children()))
						if(document.hasType(FeatureVector.class))
							frame.enableExportAction();
				} else if(lastComponent instanceof DocumentNode) {
					final DirectoryCorpusNode corpus = (DirectoryCorpusNode)hierarchySelection.getParentPath().getLastPathComponent();
					final FileDocumentNode documentSelection = (FileDocumentNode) lastComponent;

					if(MainController.this.layerController!=null) MainController.this.layerController.cancel(true);

					model.setCorpus(corpus);
					model.setDocument(documentSelection);
					model.setLayerManager(documentSelection.layerManager());
					model.clearSelectedAnnotationValues();

					frame.setAnnotatorList(model.corpus().get().annotators());
					frame.documentView().setLayerDocumentModel(model.styledDocument().get());
					frame.documentView().setLayerSelectorModel(model.document().get().typeSelectionModel());

					if(model.corpus().get().annotators().getSize()>0 && model.corpus().get().annotators().valid()) {
						frame.enableStartCorpusButton();
						frame.enableStartDocumentButton();
					} else {
						frame.disableStartCorpusButton();
						frame.disableStartDocumentButton();
					}
					frame.documentView().clearLayerPropertyVisualization();
					frame.documentView().clearLayerAnnotationsListModel();
					frame.workspaceView().newDocumentAction().setEnabled(true);
					frame.workspaceView().deleteAction().setEnabled(true);
					if(documentSelection.hasType(FeatureVector.class))
						frame.enableExportAction();
				} else if(lastComponent instanceof LayerNode<?>) {
					final LayerNode<?> layerSelection = (LayerNode<?>) lastComponent;
					final FileDocumentNode document = (FileDocumentNode)hierarchySelection.getParentPath().getLastPathComponent();
					final DirectoryCorpusNode corpus = (DirectoryCorpusNode)hierarchySelection.getParentPath().getParentPath().getLastPathComponent();

					model.setCorpus(corpus);
					model.setDocument(document);
					model.setLayerManager(document.layerManager());
					model.clearSelectedAnnotationValues();

					frame.setAnnotatorList(model.corpus().get().annotators());
					frame.documentView().setLayerSelectorModel(model.document().get().typeSelectionModel());
					frame.documentView().setSelectedLayer(null); // Enforce GUI update
					frame.documentView().setSelectedLayer(layerSelection.layer().type());

					if(model.corpus().get().annotators().getSize()>0 && model.corpus().get().annotators().valid()) {
						frame.enableStartCorpusButton();
						frame.enableStartDocumentButton();
					} else {
						frame.disableStartCorpusButton();
						frame.disableStartDocumentButton();
					}
					if(document.hasType(FeatureVector.class))
						frame.enableExportAction();
				}

			}
		});

		final CharMatcher illegalCharacterMatcher = CharMatcher.anyOf("/\\?%*:|\"<>");
		this.frame.workspaceView().newCorpusAction().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String corpusName = JOptionPane.showInputDialog(frame,"Choose a name for the new corpus. The name must not include the following characters: /\\?%*:|\"<>");
				if(corpusName!=null) {
					corpusName = illegalCharacterMatcher.removeFrom(corpusName);
					try {
						Path path = model.workspace().path().resolve(corpusName);
						Files.createDirectories(path);
					} catch(InvalidPathException e2) {
						JOptionPane.showMessageDialog(frame, 
								"The corpus name '"+e2.getInput()+"' is invalid, choose another one.", 
								"Invalid corpus name", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(frame, 
								"Unable to create the corpus. Contact your system administrator with the following error message: '"
										+e1.getLocalizedMessage()+"'", 
										"Unable to create the corpus", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		this.frame.workspaceView().newCorpusFromAnnotationsAction().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {					
				final CorpusCreationPanel corpusCreationPanel = new CorpusCreationPanel(
						model.selectedAnnotationValues().isPresent(), model.document().isPresent());
				int response = JOptionPane.showConfirmDialog(frame, 
						corpusCreationPanel, "New Corpus", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(response==JOptionPane.OK_OPTION) {
					Path directory = model.workspace().path().resolve(corpusCreationPanel.corpusName());
					try {
						Files.createDirectories(directory);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(frame, 
								"Unable to create the corpus. Contact your system administrator with the following error message: '"
										+e1.getLocalizedMessage()+"'", 
										"Unable to create the corpus", JOptionPane.ERROR_MESSAGE);
					}

					List<? extends DocumentNode> documents;
					if(corpusCreationPanel.wholeCorpus())
						documents = Collections.list(model.corpus().get().children());
					else
						documents = Collections.singletonList(model.document().get());						

					Predicate<Annotation<Object>> predicate;
					if(corpusCreationPanel.allAnnotations()) 
						predicate = Predicates.alwaysTrue();
					else {
						final Predicate<Object> onlyValues = Predicates.not(Predicates.instanceOf(Annotation.class));
						final Set<Object> allowedValues = Sets.newHashSet(
								Iterables.filter(model.selectedAnnotationValues().get(), onlyValues));
						predicate = new Predicate<Annotation<Object>>() {

							@Override
							public boolean apply(Annotation<Object> annotation) {
								return allowedValues.contains(annotation.value());
							}
						};
					}


					CorpusFromAnnotation<?> creator = new CorpusFromAnnotation(
							documents, model.annotationType().get(), predicate, directory);
					creator.run();
				}
			}
		});
		this.frame.workspaceView().deleteAction().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//				WorkspaceResource resource = null;
				//				if(model.document().isPresent())
				//					resource = model.document().get();
				//				else if(model.corpus().isPresent())
				//					resource = model.corpus().get();
				//				
				//				if(resource!=null)
				//					resource.delete();
				if(model.layer().isPresent()) {
					FileDocumentNode document = model.document().get();
					document.layerManager().removeForType(model.layer().get().type());
				} else if(model.document().isPresent()) {
					FileDocumentNode document = model.document().get();
					int response = JOptionPane.showConfirmDialog(frame, "Do you really want to delete the document "+document.name()+" from the file system? You cannot undo this action.", 
							"Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(response==JOptionPane.YES_OPTION) {
						try {
							Files.delete(document.path());
						} catch (IOException e) {
							JOptionPane.showMessageDialog(frame, 
									"Unable to delete the document. Contact your system administrator with the following error message: '"
											+e.getLocalizedMessage()+"'", 
											"Unable to delete the document", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if(model.corpus().isPresent()) {
					DirectoryCorpusNode corpus = model.corpus().get();
					int response = JOptionPane.showConfirmDialog(frame, "Do you really want to delete the corpus "+corpus.name()+" and all its documents from the file system? You cannot undo this action.", 
							"Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(response==JOptionPane.YES_OPTION) {
						try {
							FileUtils.deleteRecursive(corpus.path());

						} catch (IOException e) {
							JOptionPane.showMessageDialog(frame, 
									"Unable to delete the document. Contact your system administrator with the following error message: '"
											+e.getLocalizedMessage()+"'", 
											"Unable to delete the document", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		this.frame.workspaceView().newDocumentAction().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new ExtensionFileFilter(new String[] {"txt","doc","pdf","html","html"},"Document files"));
				int response = fc.showOpenDialog(null);
				if(response==JFileChooser.APPROVE_OPTION) {
					Path newPath = model.corpus().get().path().resolve(fc.getSelectedFile().getName());
					
					try {
						Files.copy(fc.getSelectedFile().toPath(), newPath);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		this.frame.addStartDocumentListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//				final LayerManager layerManager = model.layerManager().get();
				//				for(int i=0; i<model.corpus().get().annotators().getSize(); i++) {
				//					final Annotator<?> annotator = model.corpus().get().annotators().getElementAt(i).get();
				//					layerManager.addLayer(annotator.process(model.document().get().text(), layerManager));
				//
				//				}
				if(model.corpus().isPresent() && model.document().isPresent())
					model.corpus().get().annotators().annotate(model.document().get());
			}
		});

		this.frame.addStartCorpusListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//				for(int i=0; i<model.corpus().get().annotators().getSize(); i++) {
				//					final Annotator<?> annotator = model.corpus().get().annotators().getElementAt(i).get();
				//					for(final DocumentNode document : Collections.list(model.corpus().get().children()))
				//						document.layerManager().addLayer(annotator.process(document.text(), document.layerManager()));
				//
				//				}
				if(model.corpus().isPresent())
					model.corpus().get().annotate();
			}
		});
		this.frame.addPluginTreeListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath selection = frame.pluginTreeSelection();
				DefaultMutableTreeNode last = (DefaultMutableTreeNode) selection.getLastPathComponent();
				if(last.getUserObject() instanceof Plugin) {
					model.setPlugin((Plugin)last.getUserObject());
					model.clearDeactivatedAnnotatorSupplier();

					frame.enableAddPluginButton();
					frame.disableAddAnnotatorButton();
				} else if(last.getUserObject() instanceof AnnotatorSupplier) {
					DefaultMutableTreeNode penultimate = (DefaultMutableTreeNode) selection.getParentPath().getLastPathComponent();
					model.setPlugin((Plugin)penultimate.getUserObject());
					model.setDeactivatedAnnotatorSupplier((AnnotatorSupplier)last.getUserObject());

					frame.enableAddPluginButton();
					frame.enableAddAnnotatorButton();
				}
			}
		});
		this.frame.addAnnotatorListListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				model.setActivatedAnnotatorSupplier(frame.annotatorSupplier());

				if(model.activatedAnnotatorSupplier().isPresent())
					frame.enableRemoveAnnotatorButton();
				else
					frame.disableRemoveAnnotatorButton();
			}
		});
		this.frame.addAddAnnotatorListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.corpus().get().annotators().add(model.deactivatedAnnotatorSupplier().get().initialized());
				frame.repaint();

				if(model.corpus().get().annotators().getSize()>0 && model.corpus().get().annotators().valid()) {
					frame.enableStartCorpusButton();
					frame.enableStartDocumentButton();
				} else {
					frame.disableStartCorpusButton();
					frame.disableStartDocumentButton();
				}
			}
		});
		this.frame.addRemoveAnnotatorListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.corpus().get().annotators().remove(model.activatedAnnotatorSupplier().get());
				frame.repaint();

				if(model.corpus().get().annotators().getSize()>0 && model.corpus().get().annotators().valid()) {
					frame.enableStartCorpusButton();
					frame.enableStartDocumentButton();
				} else {
					frame.disableStartCorpusButton();
					frame.disableStartDocumentButton();
				}
			}
		});
		this.frame.addAddPluginListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final Plugin plugin = model.plugin().get();
				for(AnnotatorSupplier supplier : plugin.annotatorSuppliers())	
					model.corpus().get().annotators().add(DelegatingAnnotatorSupplier.initialize(supplier));
				frame.repaint();

				if(model.corpus().get().annotators().getSize()>0 && model.corpus().get().annotators().valid()) {
					frame.enableStartCorpusButton();
					frame.enableStartDocumentButton();
				} else {
					frame.disableStartCorpusButton();
					frame.disableStartDocumentButton();
				}
			}
		});
		this.frame.addExportActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Featurevector export file","csv"));
				int response = fileChooser.showSaveDialog(null);
				if(response==JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					
					try {
						CSVExporter.INSTANCE.export(model.corpus().get(), file.toPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MainController.this.executor.stop();
				for(DirectoryCorpusNode corpus : Collections.list(model.workspace().children())) {
					try {
						corpus.save();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					Path path = corpus.path().resolve("annotators.xml");
					try {
						XStream xStream = new XStream();
						Files.write(path, xStream.toXML(corpus.annotators()).getBytes(), StandardOpenOption.CREATE);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		frame.workspaceView().setTreeModel(model.hierarchy());
	}

	public static void main(String[] args) {
		MainModel model = new MainModel();
		MainFrame frame = new MainFrame();
		MainController controller = new MainController(model,frame);

		frame.setVisible(true);
	}
}
