package de.tudarmstadt.linglit.linfw.app.model.workspace.directory;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.TreeNode;

import org.apache.tika.Tika;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.net.MediaType;
import com.thoughtworks.xstream.XStream;

import de.tudarmstadt.linglit.linfw.app.FileUtils;
import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.UserInteraction;
import de.tudarmstadt.linglit.linfw.app.UserInteraction.Response;
import de.tudarmstadt.linglit.linfw.app.model.AnnotatorListModel;
import de.tudarmstadt.linglit.linfw.app.model.CorpusDocument;
import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DirectoryResource;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceTreeModel;
import de.tudarmstadt.linglit.linfw.app.plugin.AnnotatorSupplier;
import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class DirectoryCorpusNode extends DirectoryResource implements CorpusNode {
	private final WorkspaceNode workspace;
	private AnnotatorListModel annotators = new AnnotatorListModel();
	private final Map<String,DocumentNode> documents = Collections.synchronizedMap(new HashMap<String,DocumentNode>());
	private final List<FileDocumentNode> documentList = Collections.synchronizedList(new ArrayList<FileDocumentNode>());
	private final ClassLoader classloader;
	private final Multiset<Class<?>> availableTypes = HashMultiset.create();
	private final List<Class<?>> typeList = new ArrayList<>();
	private final DirectoryCorpusComboBoxModel comboboxmodel = new DirectoryCorpusComboBoxModel();

	protected class DirectoryCorpusComboBoxModel extends AbstractListModel<Class<?>> 
	implements ComboBoxModel<Class<?>> {
		private Object selected = null;

		@Override
		public Class<?> getElementAt(int index) {
			return typeList.get(index);
		}

		@Override
		public int getSize() {
			return typeList.size();
		}

		@Override
		public Object getSelectedItem() {
			return this.selected;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			this.selected = anItem;
			fireContentsChanged(DirectoryCorpusNode.this, -1, -1);
		}

		@Override
		protected void fireContentsChanged(Object source, int index0, int index1) {
			super.fireContentsChanged(source, index0, index1);
		}

		@Override
		protected void fireIntervalAdded(Object source, int index0, int index1) {
			super.fireIntervalAdded(source, index0, index1);
		}

		@Override
		protected void fireIntervalRemoved(Object source, int index0, int index1) {
			super.fireIntervalRemoved(source, index0, index1);
		}

	}

	protected synchronized void addLayer(ReadableLayer<?> layer) {
		availableTypes.add(layer.type());
		if(availableTypes.count(layer.type())==1) {
			typeList.add(layer.type());
			comboboxmodel.fireIntervalAdded(this, typeList.size()-1, typeList.size()-1);
		}
	}

	protected synchronized void removeLayer(ReadableLayer<?> layer) {
		availableTypes.remove(layer.type());
		if(!availableTypes.contains(layer.type())) {
			int index = typeList.indexOf(layer.type());
			typeList.remove(layer.type());
			comboboxmodel.fireIntervalRemoved(this, index, index);
		}
	}

	@Override
	public String name() {
		return path().getFileName().toString();
	}

	@Override
	public synchronized AnnotatorListModel annotators() {
		return this.annotators;
	}

	public synchronized void addAnnotator(AnnotatorSupplier annotator) {
		this.annotators.add(annotator);
	}

	public DirectoryCorpusNode(WorkspaceNode workspace, Path path, ClassLoader classloader) throws IOException {
		super(path);
		this.workspace = workspace;
		this.classloader = classloader;
		populate();
	}


	private final static Tika TIKA = new Tika();
	@Override
	protected void tryAdd(Path path) {
		if(Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
			try {
				while(!path.toFile().renameTo(path.toFile())) {
					// Cannot read from file, windows still working on it.
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(path.endsWith("annotators.xml")) { //$NON-NLS-1$
					XStream xStream = new XStream();
					xStream.setClassLoader(this.classloader);
					this.annotators = (AnnotatorListModel)xStream.fromXML(path.toFile());
				} else {
					final String probeContentType = TIKA.detect(path.toFile());
					if(probeContentType!=null) {
						final MediaType mediatype = MediaType.parse(probeContentType);
						if(mediatype.is(MediaType.ANY_TEXT_TYPE) || mediatype.is(MediaType.PDF) || mediatype.is(MediaType.MICROSOFT_WORD)) {
							FileDocumentNode document = new FileDocumentNode(this,path,this.classloader);
							this.documentList.add(document);
							this.documents.put(path.toAbsolutePath().toString(), document);
							if(this.getParent().model().isPresent()) {
								System.out.println(path);
								WorkspaceTreeModel workspaceModel = this.getParent().model().get();
								workspaceModel.nodesWereInserted(this, new int[] {this.documentList.size()-1});
							}
						}
					}
				}
			} catch (IOException e) { System.out.println(e); }
		}
	}

	@Override
	protected void remove(Path path) {
		DocumentNode removed = this.documents.remove(path.toAbsolutePath().toString());
		if(removed==null) return;
		int index = this.documentList.indexOf(removed);
		this.documentList.remove(removed);

		if(this.getParent().model().isPresent()) {
			WorkspaceTreeModel workspaceModel = this.getParent().model().get();
			workspaceModel.nodesWereRemoved(this,new int[] {index}, new Object[]{ removed });
		}
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public int getChildCount() {
		return this.documentList.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return this.documentList.indexOf(node);
	}

	@Override
	public boolean isLeaf() {
		return getChildCount()==0;
	}

	@Override
	public Enumeration<FileDocumentNode> children() {
		return Collections.enumeration(this.documentList);
	}

	@Override
	public DocumentNode getChildAt(int childIndex) {
		return this.documentList.get(childIndex);
	}

	@Override
	public WorkspaceNode getParent() {
		return this.workspace;
	}

	@Override
	public ComboBoxModel<Class<?>> typeSelectionModel() {
		return this.comboboxmodel;
	}

	@Override
	public List<FileDocumentNode> documents() {
		return ImmutableList.copyOf(this.documentList);
	}

	@Override
	public synchronized void annotate(Annotator<?> annotator) {
		Response response = null;
		for(CorpusDocument document : this.documentList) {
			if(document.hasType(annotator.type()) && response!=Response.ALWAYS_POSITIVE) {
				if(response==Response.ALWAYS_NEGATIVE) continue;

				final String rememberMessage = String.format(Messages.getString("DirectoryCorpusNode.1"),annotator.type().getSimpleName()); //$NON-NLS-1$
				final String message = String.format(Messages.getString("DirectoryCorpusNode.2"),document.name(),annotator.type().getSimpleName()); //$NON-NLS-1$
				final String title = Messages.getString("DirectoryCorpusNode.3"); //$NON-NLS-1$

				response = UserInteraction.ask(message, title, rememberMessage);
				if(response==Response.ALWAYS_NEGATIVE || response==Response.NEGATIVE) continue;
			} 
			document.annotate(annotator);
		}
	}

	@Override
	public boolean annotate() {
		return this.annotators.annotate(this);
	}
	
	public void save() throws IOException {
		for(FileDocumentNode document : this.documentList)
			document.save();
	}

	@Override
	public boolean changed() {
		for(DocumentNode document : this.documentList)
			if(document.changed()) return true;
		
		return false;
	}

	@Override
	public URI uri() {
		return this.path().toUri();
	}

	@Override
	public void delete() {
		try {
			Response response = UserInteraction.ask(String.format(Messages.getString("DirectoryCorpusNode.0"),path().getFileName()), Messages.getString("DirectoryCorpusNode.4")); //$NON-NLS-1$ //$NON-NLS-2$
			if(response==Response.POSITIVE)
				FileUtils.deleteRecursive(this.path());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
