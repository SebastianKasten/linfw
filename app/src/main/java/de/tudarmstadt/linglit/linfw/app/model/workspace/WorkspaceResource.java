package de.tudarmstadt.linglit.linfw.app.model.workspace;

import java.net.URI;

public interface WorkspaceResource {
	public boolean changed();
	public URI uri();
	public void delete();
}
