package de.tudarmstadt.linglit.linfw.app.gui;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EyeCancerColoring implements Coloring {
	private final Map<Object,Color> colors = Collections.synchronizedMap(new HashMap<Object, Color>());
	private int hue = 0;
	private int sat = 100;
	private int bri = 50;

	private Color nextColor() {
		final Color color = Color.getHSBColor(this.hue / 360f,
				0.2f + this.sat / 100f, 0.2f + this.bri / 100f);
		this.hue = (this.hue + 50) % 360;
		this.sat = (this.sat + 15) % 80;
		this.bri = (this.bri + 15) % 80;
		return color;
	}
	
	@Override
	public Color primary(Object o) {
		if(!this.colors.containsKey(o))
			this.colors.put(o, nextColor());
		
		return this.colors.get(o);
	}
	
	public Color contrasting(Object o) {
		final Color color = primary(o);
		final boolean bright = 0.299 * color.getRed() + 0.587
				* color.getGreen() + 0.114 * color.getBlue() > 127;
				
		if(bright) return Color.black;
		return Color.white;
	}

}
