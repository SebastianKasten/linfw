package de.tudarmstadt.linglit.linfw.app.model.workspace.directory;

import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreeNode;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.FileUtils;
import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DirectoryResource;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceTreeModel;

public class DirectoryWorkspaceNode extends DirectoryResource implements WorkspaceNode {
	private final Map<Path,DirectoryCorpusNode> corpuses = Collections.synchronizedMap(new HashMap<Path,DirectoryCorpusNode>());
	private final List<DirectoryCorpusNode> corpusList = Collections.synchronizedList(new ArrayList<DirectoryCorpusNode>());
	private final ClassLoader classloader;
	private WorkspaceTreeModel<DirectoryWorkspaceNode> model;
	
	protected synchronized void tryAdd(final Path path)  {
		if(Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
			try {
				DirectoryCorpusNode corpus = new DirectoryCorpusNode(this,path,this.classloader);
				this.corpuses.put(path, corpus);
				this.corpusList.add(corpus);
				if(this.model!=null)
				this.model.nodesWereInserted(this, new int[]{ this.corpusList.size()-1 });
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

	protected synchronized void remove(final Path path) {
		DirectoryCorpusNode removed = this.corpuses.remove(path);
		int index = this.corpusList.indexOf(removed);
		this.corpusList.remove(removed);
		if(this.model!=null)
			this.model.nodesWereRemoved(this,new int[] {index}, new Object[]{removed});
		
	}

	public DirectoryWorkspaceNode(Path path, final ClassLoader classloader) {
		super(path);
		this.classloader = classloader;
		populate();
	}

	@Override
	public Optional<WorkspaceTreeModel<DirectoryWorkspaceNode>> model() {
		return Optional.fromNullable(this.model);
	}

	@Override
	public void setModel(WorkspaceTreeModel model) {
		this.model = model;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public int getChildCount() {
		return this.corpusList.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return this.corpusList.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return this.getChildCount()==0;
	}


	@Override
	public Enumeration<DirectoryCorpusNode> children() {
		return Collections.enumeration(this.corpusList);
	}

	@Override
	public CorpusNode getChildAt(int childIndex) {
		return this.corpusList.get(childIndex);
	}
	
	public void save() throws IOException {
		for(DirectoryCorpusNode corpus : this.corpusList)
			corpus.save();
	}

	@Override
	public boolean changed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public URI uri() {
		return path().toUri();
	}

	@Override
	public void delete() {
		try {
			FileUtils.deleteRecursive(this.path());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
