package de.tudarmstadt.linglit.linfw.app;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UserInteraction {
	public enum Response {
		POSITIVE,
		NEGATIVE,
		ALWAYS_POSITIVE,
		ALWAYS_NEGATIVE
	}
	
	public static Response ask(String message, String title, String rememberMessage) {
		JCheckBox checkbox = new JCheckBox("<html>"+rememberMessage+"</html>",false); //$NON-NLS-1$ //$NON-NLS-2$
		JPanel messagePanel = new JPanel(new BorderLayout());
		messagePanel.add(new JLabel("<html>"+message+"</html>"), BorderLayout.CENTER); //$NON-NLS-1$ //$NON-NLS-2$
		messagePanel.add(checkbox, BorderLayout.SOUTH);
		
		int response = JOptionPane.showConfirmDialog(null, messagePanel, title, JOptionPane.YES_NO_OPTION); 
		if(checkbox.isSelected())
			if(response == JOptionPane.YES_OPTION) return Response.ALWAYS_POSITIVE;
			else return Response.ALWAYS_NEGATIVE;
		else
			if(response == JOptionPane.YES_OPTION) return Response.POSITIVE;
			else return Response.NEGATIVE;
	}
	
	public static Response ask(String message, String title) {		
		int response = JOptionPane.showConfirmDialog(null, "<html>"+message+"</html>", title, JOptionPane.YES_NO_OPTION); 

		if(response == JOptionPane.YES_OPTION) return Response.POSITIVE;
		else return Response.NEGATIVE;
	}
	
	public static void error(Exception exception) {
		JOptionPane.showMessageDialog(null, exception.getLocalizedMessage());
	}

	private UserInteraction() {}
}
