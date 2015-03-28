package de.tudarmstadt.linglit.linfw.app.model.workspace;

import javax.swing.event.ChangeListener;

import com.google.common.base.Optional;

/**
 * The WorkspaceResourceSelectionModel provides access to
 * the currently selected WorkspaceResource.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 */
public interface WorkspaceResourceSelectionModel {
	/**
	 * Returns the selected workspace resource.
	 * 
	 * @return the selected workspace resource
	 */
	public Optional<WorkspaceResource> selectedResource();
	
	/**
	 * Adds a change listener that gets notified when
	 * the selected workspace resource changes.
	 * 
	 * @param listener change listener
	 */
	public void addListener(ChangeListener listener);
}
