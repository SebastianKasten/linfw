package de.tudarmstadt.linglit.linfw.app.model;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.tudarmstadt.linglit.linfw.app.LayerController;
import de.tudarmstadt.linglit.linfw.app.MainController;
import de.tudarmstadt.linglit.linfw.app.gui.Coloring;
import de.tudarmstadt.linglit.linfw.app.gui.EyeCancerColoring;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.core.text.TextAreas;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

public class DocumentModel implements StyledDocument, AnnotationHandler<Object> {
	private final DefaultStyledDocument base;
	private Coloring coloring = new EyeCancerColoring();

	@Override
	public void addDocumentListener(final DocumentListener listener) {
		this.base.addDocumentListener(listener);
	}

	@Override
	public void addUndoableEditListener(final UndoableEditListener listener) {
		this.base.addUndoableEditListener(listener);
	}

	@Override
	public Position createPosition(final int offs) throws BadLocationException {
		return this.base.createPosition(offs);
	}

	@Override
	public Element getDefaultRootElement() {
		return this.base.getDefaultRootElement();
	}

	@Override
	public Position getEndPosition() {
		return this.base.getEndPosition();
	}

	@Override
	public int getLength() {
		return this.base.getLength();
	}

	@Override
	public Object getProperty(final Object key) {
		return this.base.getProperty(key);
	}

	@Override
	public Element[] getRootElements() {
		return this.base.getRootElements();
	}

	@Override
	public Position getStartPosition() {
		return this.base.getStartPosition();
	}

	@Override
	public String getText(final int offset, final int length)
			throws BadLocationException {
		return this.base.getText(offset, length);
	}

	@Override
	public void getText(final int offset, final int length, final Segment txt)
			throws BadLocationException {
		this.base.getText(offset, length, txt);
	}

	@Override
	public void insertString(final int offset, final String str,
			final AttributeSet a) throws BadLocationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putProperty(final Object key, final Object value) {
		this.base.putProperty(key, value);
	}

	@Override
	public void remove(final int offs, final int len)
			throws BadLocationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeDocumentListener(final DocumentListener listener) {
		this.base.removeDocumentListener(listener);
	}

	@Override
	public void removeUndoableEditListener(final UndoableEditListener listener) {
		this.base.removeUndoableEditListener(listener);
	}

	@Override
	public void render(final Runnable r) {
		this.base.render(r);
	}

	@Override
	public Style addStyle(final String arg0, final Style arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Color getBackground(final AttributeSet arg0) {
		return this.base.getBackground(arg0);
	}

	@Override
	public Element getCharacterElement(final int arg0) {
		return this.base.getCharacterElement(arg0);
	}

	@Override
	public Font getFont(final AttributeSet arg0) {
		return this.base.getFont(arg0);
	}

	@Override
	public Color getForeground(final AttributeSet arg0) {
		return this.base.getForeground(arg0);
	}

	@Override
	public Style getLogicalStyle(final int arg0) {
		return this.base.getLogicalStyle(arg0);
	}

	@Override
	public Element getParagraphElement(final int arg0) {
		return this.base.getParagraphElement(arg0);
	}

	@Override
	public Style getStyle(final String arg0) {
		return this.base.getStyle(arg0);
	}

	@Override
	public void removeStyle(final String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCharacterAttributes(final int arg0, final int arg1,
			final AttributeSet arg2, final boolean arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLogicalStyle(final int arg0, final Style arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setParagraphAttributes(final int arg0, final int arg1,
			final AttributeSet arg2, final boolean arg3) {
		throw new UnsupportedOperationException();
	}

	public void clearHighlights() {
		this.coloring = new EyeCancerColoring();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DocumentModel.this.base.setCharacterAttributes(0,
						DocumentModel.this.getLength(),
						DocumentModel.this.getLogicalStyle(0), true);
			}
		});
	}

	private final Map<Object, Color> colors = new HashMap<Object, Color>();
	public void highlightAnnotation(final Annotation<?> annotation) {
		Color color = this.coloring.primary(annotation.value());

		final SimpleAttributeSet attributes = new SimpleAttributeSet();
		attributes.addAttribute(StyleConstants.Background, color);
		attributes.addAttribute(StyleConstants.Foreground, this.coloring.contrasting(annotation.value()));

		final TextSpan closure = annotation.area().closure();


		if (closure == annotation.area()) 
			DocumentModel.this.base.setCharacterAttributes(
					closure.start(), closure.length(),
					attributes, false);
		else {
			List<TextSpan> spans = TextAreas.splitSpans(annotation.area());
			for(TextSpan span : spans)
				DocumentModel.this.base.setCharacterAttributes(
						span.start(), span.length(),
						attributes, false);
		}
	}

	public DocumentModel(final CharSequence document) {
		this.base = new DefaultStyledDocument();
		try {
			this.base.insertString(this.base.getLength(), document
					.toString(), this.base.getLogicalStyle(0));
		} catch (final BadLocationException e) {
			// This must not happen! Ever!
			throw new RuntimeException(e);
		}
	}

	@Override
	public void process(Object controller,
			ReadableLayer<Object> layer, CharSequence text, final List<Annotation<Object>> annotations) {
		for(Annotation<?> annotation : annotations)
			highlightAnnotation(annotation);
	}

	@Override
	public void finish(Object controller) {	}
}
