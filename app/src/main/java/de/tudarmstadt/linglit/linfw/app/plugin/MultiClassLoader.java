package de.tudarmstadt.linglit.linfw.app.plugin;

import java.util.ArrayList;
import java.util.List;

public class MultiClassLoader extends ClassLoader {
	public List<ClassLoader> children = new ArrayList<ClassLoader>();

	public synchronized void add(ClassLoader cl) {
		this.children.add(cl);
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return super.findClass(name);
		} catch(ClassNotFoundException e2) {
			for(ClassLoader child : this.children)
				try {
					Class<?> clazz = child.loadClass(name);
					return clazz;
				} catch(ClassNotFoundException e) {
					// Continue as nothing has happened
				}
		}
		throw new ClassNotFoundException(name);
	}

	public Class<?> loadClass(String name, ClassLoader excluding) throws ClassNotFoundException {
		try {
			loadClass(name);
		} catch(ClassNotFoundException e) {
			for(ClassLoader child : this.children)
				try {
					if(child==excluding) continue;
					Class<?> clazz = child.loadClass(name);
					return clazz;
				} catch(ClassNotFoundException e2) {
					// Continue as nothing has happened
				}
		}
		throw new ClassNotFoundException();
	}
}
