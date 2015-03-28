package de.tudarmstadt.linglit.linfw.app.annotator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.model.DefaultSelectionModel;
import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.WorkspaceResource;

public class WorkspaceListener implements TreeSelectionListener, ActionListener {
	private final DefaultSelectionModel model;

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		Object last = event.getPath().getLastPathComponent();

		if(last instanceof WorkspaceNode)
			this.model.setSelectedWorkspaceNode((WorkspaceNode)last);
		else if(last instanceof CorpusNode)
			this.model.setSelectedCorpusNode((CorpusNode)last);
		else if(last instanceof DocumentNode)
			this.model.setSelectedDocumentNode((DocumentNode)last);
		else if(last instanceof LayerNode)
			this.model.setSelectedLayerNode((LayerNode<?>)last);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		Optional<WorkspaceResource> resource = this.model.selectedResource();

		if(resource.isPresent())
			switch(command) {
			case "DELETE":
				resource.get().delete();
				break;
			default:
				break;
			}
	}

	public WorkspaceListener(DefaultSelectionModel model) {
		this.model = model;
	}

}
