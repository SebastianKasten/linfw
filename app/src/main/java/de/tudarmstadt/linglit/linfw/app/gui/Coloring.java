package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.Color;

/**
 * A coloring provides a color for a given object.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 */
public interface Coloring {
	/**
	 * Returns a color for the object o.
	 * @param o an object
	 * @return a color for that object
	 */
	public Color primary(Object o);
	
	/**
	 * Returns a contrasting color to the primary color for the object o.
	 * @param o an object
	 * @return a contrasting color for that object
	 */
	public Color contrasting(Object o);
}
