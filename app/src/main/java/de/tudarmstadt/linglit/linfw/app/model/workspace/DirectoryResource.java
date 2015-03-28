package de.tudarmstadt.linglit.linfw.app.model.workspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import org.apache.tika.Tika;

public abstract class DirectoryResource {
	private final Path path;
	private final WatchService watcher;
	private final Map<Path, WatchKey> watchkeys = new HashMap<>();
	private boolean active = true;
	
	public Path path() {
		return this.path;
	}
	
	protected WatchService watcher() {
		return this.watcher;
	}

	protected abstract void tryAdd(Path path);
	protected abstract void remove(Path path);

	protected void populate() {
		try {
			this.path.register(this.watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
			new Thread(new Runnable() {
				@Override
				public void run() {

					while(active) {
						WatchKey key;
						try {
							key = watcher().take();
							for (WatchEvent<?> event: key.pollEvents()) {
								System.out.println(DirectoryResource.this);
								System.out.println(event.count());
								System.out.println(event.context());
								System.out.println(event.kind());
								WatchEvent.Kind<?> kind = event.kind();
								WatchEvent<Path> ev = (WatchEvent<Path>)event;
								Path filename = path.resolve(ev.context());
								System.out.println(filename);
								if(kind==StandardWatchEventKinds.ENTRY_CREATE)
									tryAdd(filename);
								else if(kind==StandardWatchEventKinds.ENTRY_DELETE) {
									remove(filename);
								}
							}
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						boolean valid = key.reset();
						if (!valid) {
							break;
						}
					}
				}
			}).start();

			File directory = path.toFile();
			for(File child : directory.listFiles())
				tryAdd(child.toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public DirectoryResource(Path path) {
		this.path = path;
		try {
			this.watcher  = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
