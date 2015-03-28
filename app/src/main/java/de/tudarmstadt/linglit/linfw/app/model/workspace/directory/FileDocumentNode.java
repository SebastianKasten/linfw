package de.tudarmstadt.linglit.linfw.app.model.workspace.directory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import javax.swing.tree.TreeNode;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;

import com.google.common.base.Optional;
import com.google.common.collect.Iterators;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import de.tudarmstadt.linglit.linfw.app.FileUtils;
import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.UserInteraction;
import de.tudarmstadt.linglit.linfw.app.UserInteraction.Response;
import de.tudarmstadt.linglit.linfw.app.annotation.Metadata;
import de.tudarmstadt.linglit.linfw.app.model.BlockingLayerManager;
import de.tudarmstadt.linglit.linfw.app.model.Corpus;
import de.tudarmstadt.linglit.linfw.app.model.LayerManager;
import de.tudarmstadt.linglit.linfw.app.model.workspace.CorpusNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.DocumentNode;
import de.tudarmstadt.linglit.linfw.app.model.workspace.LayerNode;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.annotator.Annotator;
import de.tudarmstadt.linglit.linfw.core.layer.DefaultSequentialLayer;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.core.text.MultiTextSpan;
import de.tudarmstadt.linglit.linfw.core.text.TextArea;
import de.tudarmstadt.linglit.linfw.core.text.TextAreas;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

public class FileDocumentNode implements DocumentNode {	
	public class FileDocumentComboBoxModel extends AbstractListModel<Class<?>> 
	implements ComboBoxModel<Class<?>> {
		private Object selected = null;

		@Override
		public Class<?> getElementAt(int index) {
			return FileDocumentNode.this.layernodes.get(index).layer().type();
		}

		@Override
		public int getSize() {
			return FileDocumentNode.this.layernodes.size();
		}

		@Override
		public Object getSelectedItem() {
			return this.selected;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			this.selected = anItem;
			fireContentsChanged(FileDocumentNode.this, -1, -1);
		}

		@Override
		protected void fireContentsChanged(Object source, int index0, int index1) {
			super.fireContentsChanged(source, index0, index1);
		}

		@Override
		protected void fireIntervalAdded(Object source, int index0, int index1) {
			super.fireIntervalAdded(source, index0, index1);
		}

		@Override
		protected void fireIntervalRemoved(Object source, int index0, int index1) {
			super.fireIntervalRemoved(source, index0, index1);
		}
		
	}
	
	public class FileDocumentLayerManager implements LayerManager {
		
		private final LayerManager base;

		@Override
		public boolean hasType(Class<?> type) {
			return this.base.hasType(type);
		}

		@Override
		public <A> ReadableLayer<A> forType(Class<A> type) {
			return this.base.forType(type);
		}

		@Override
		public Iterator<ReadableLayer<?>> iterator() {
			return this.base.iterator();
		}

		@Override
		public <A> Optional<ReadableLayer<A>> addLayer(ReadableLayer<A> layer) {
			Optional<ReadableLayer<A>> result;
			if(this.base.hasType(layer.type()))
				result = removeForType(layer.type());
			else
				result = Optional.absent();

			this.base.addLayer(layer);
			FileDocumentNode.this.layernodes.add(new LayerNode<>(FileDocumentNode.this, layer));
			if(FileDocumentNode.this.corpus.getParent().model().isPresent()) {
				FileDocumentNode.this.corpus.getParent().model().get().nodesWereInserted(FileDocumentNode.this, new int[] {FileDocumentNode.this.layernodes.size()-1});
			}
			FileDocumentNode.this.comboboxmodel.fireIntervalAdded(FileDocumentNode.this, FileDocumentNode.this.layernodes.size()-1, FileDocumentNode.this.layernodes.size()-1);
			FileDocumentNode.this.corpus.addLayer(layer);
			FileDocumentNode.this.dirty = true;
			return result;
		}

		public FileDocumentLayerManager(LayerManager base) {
			this.base = base;
		}

		@Override
		public <A> Optional<ReadableLayer<A>> removeForType(Class<A> type) {
			Optional<ReadableLayer<A>> result = this.base.removeForType(type);
			if(result.isPresent()) {
				int index = indexOf(result);
				LayerNode<?> node = FileDocumentNode.this.layernodes.remove(index);
				if(FileDocumentNode.this.corpus.getParent().model().isPresent())
					FileDocumentNode.this.corpus.getParent().model().get().nodesWereRemoved(FileDocumentNode.this, new int[] {index},new Object[]{result.get()});
				FileDocumentNode.this.comboboxmodel.fireIntervalRemoved(FileDocumentNode.this, index, index);
				FileDocumentNode.this.corpus.removeLayer(result.get());
				FileDocumentNode.this.dirty = true;
			}
			return result;
		}

		protected <A> int indexOf(Optional<ReadableLayer<A>> result) {
			int index = 0;
			for(LayerNode<?> layernode : FileDocumentNode.this.layernodes) {
				if(layernode.layer() == result.get()) 
					break;
				index++;
			}
			return index;
		}

	}

	private final static Tika TIKA = new Tika();
	private final DirectoryCorpusNode corpus;
	private final Path filename;
	private final FileDocumentLayerManager layermanager = new FileDocumentLayerManager(new BlockingLayerManager());
	private final List<LayerNode<?>> layernodes = new ArrayList<>();
	private final CharSequence text;
	private final FileDocumentComboBoxModel comboboxmodel = new FileDocumentComboBoxModel();
	private boolean dirty = false;

	public Path path() {
		return this.filename;
	}

	@Override
	public LayerManager layerManager() {
		return this.layermanager;
	}

	public ComboBoxModel<Class<?>> typeSelectionModel() {
		return this.comboboxmodel;
	}

	public FileDocumentNode(DirectoryCorpusNode corpus, Path filename, ClassLoader classloader) {
		this.corpus = corpus;
		this.filename = filename;
		this.xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setClassLoader(classloader);

		TIKA.setMaxStringLength(-1);
		String text = ""; //$NON-NLS-1$
		try {
			Metadata metadata = new Metadata();
			TikaInputStream is = TikaInputStream.get(filename.toFile(),metadata);
			text = TIKA.parseToString(is,metadata);

			DefaultSequentialLayer<Metadata> metadataLayer = new DefaultSequentialLayer<>(Metadata.class);
			metadataLayer.appendAnnotation(new Annotation<>(TextSpan.allOf(text), metadata, Metadata.class));
			metadataLayer.close();
			this.layermanager.addLayer(metadataLayer);
			
			if(Files.isDirectory(layerDirectory())) {
				File directory = new File(layerDirectory().toString());
				for(File layerFile : directory.listFiles()) {
					LayerSerializationProxy layerproxy = (LayerSerializationProxy)xstream.fromXML(layerFile);
					this.layermanager.addLayer(layerproxy.asLayer(text));
				}
					
			}
		} catch (TikaException | IOException e) {
			e.printStackTrace();
		}
		this.text = text;
		this.dirty = false;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public int getChildCount() {
		return this.layernodes.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return this.layernodes.indexOf(node);
	}

	@Override
	public boolean isLeaf() {
		return getChildCount()==0;
	}

	@Override
	public Enumeration<LayerNode<?>> children() {
		return Collections.enumeration(this.layernodes);
	}

	@Override
	public LayerNode<?> getChildAt(int childIndex) {
		return this.layernodes.get(childIndex);
	}

	@Override
	public CorpusNode getParent() {
		return this.corpus;
	}

	@Override
	public String name() {
		return this.filename.getFileName().toString();
	}

	@Override
	public CharSequence text() {
		return this.text;
	}

	@Override
	public Corpus corpus() {
		return this.corpus;
	}

	@Override
	public synchronized <A> Optional<ReadableLayer<A>> addLayer(ReadableLayer<A> layer) {
		return this.layermanager.addLayer(layer);
	}

	@Override
	public <A> Optional<ReadableLayer<A>> removeForType(Class<A> type) {
		return this.layermanager.removeForType(type);
	}

	@Override
	public boolean hasType(Class<?> type) {
		return this.layermanager.hasType(type);
	}

	@Override
	public <A> ReadableLayer<A> forType(Class<A> type) {
		return this.layermanager.forType(type);
	}

	@Override
	public Iterator<ReadableLayer<?>> iterator() {
		return this.layermanager.iterator();
	}

	@Override
	public void annotate(Annotator<?> annotator) {
		addLayer(annotator.process(this.text, this));
	}
	
	private static final class LayerSerializationProxy {
		private final Class<?> type;
		private final List<AnnotationSerializationProxy> annotations = new ArrayList<>();
		
		public LayerSerializationProxy(ReadableLayer<?> layer) {
			this.type = layer.type();
			Iterator<? extends Annotation<?>> iterator = layer.sequential();
			while(iterator.hasNext())
				this.annotations.add(new AnnotationSerializationProxy(iterator.next()));
		}
		
		public <A> ReadableLayer<A> asLayer(CharSequence context) {
			DefaultSequentialLayer<A> result = new DefaultSequentialLayer<A>((Class<A>) type);
			for(AnnotationSerializationProxy annotation : this.annotations)
				result.appendAnnotation(annotation.asAnnotation(context, (Class<A>)this.type));
			result.close();
			return result;
		}
	}
	
	private static final class AnnotationSerializationProxy {
		private static final class Span {
			private final int start;
			private final int end;
			
			public Span(final int start, final int end) {
				this.start = start;
				this.end = end;
			}
		}
		
		private final Object value;
		private final List<Span> spans = new ArrayList<>();
		
		public AnnotationSerializationProxy(Annotation<?> annotation) {
			this.value = annotation.value();
			List<TextSpan> textSpans = TextAreas.splitSpans(annotation.area());
			for(TextSpan textSpan : textSpans)
				this.spans.add(new Span(textSpan.start(),textSpan.end()));
		}
		
		public <A> Annotation<A> asAnnotation(final CharSequence context, final Class<A> type) {
			TextArea area;
			if(spans.size()==1)
				area = TextSpan.between(context, spans.get(0).start, spans.get(0).end);
			else {
				List<TextSpan> textSpans = new ArrayList<TextSpan>();
				for(Span span : spans)
					textSpans.add(TextSpan.between(context, span.start, span.end));
				area = new MultiTextSpan(textSpans);
			}
			return new Annotation<A>(area, (A)value, type);
		}
	}
	
	private final XStream xstream;
	public void save() throws IOException {
		if(!dirty) return;
		
		final Path directory = layerDirectory();
		
		if(Files.exists(directory))
			FileUtils.deleteRecursive(directory);
		Files.createDirectory(directory);
		for(LayerNode<?> layerNode : this.layernodes) {
			ReadableLayer<?> layer = layerNode.layer();
			if(!layer.finished() || layer.type().equals(Metadata.class)) continue;
			
			Path file = directory.resolve(layer.type().getSimpleName()+".json"); //$NON-NLS-1$

			LayerSerializationProxy proxy = new LayerSerializationProxy(layer);
			try(OutputStream os = Files.newOutputStream(file)) {
				xstream.toXML(proxy, os);
			}
		}
	}

	private Path layerDirectory() {
		final String directoryName = this.name().replace('.', '_');
		final Path directory = this.path().getParent().resolve(directoryName);
		return directory;
	}

	@Override
	public boolean changed() {
		return this.dirty;
	}

	@Override
	public URI uri() {
		return path().toUri();
	}

	@Override
	public void delete() {
		try {
			Response response = UserInteraction.ask(String.format(Messages.getString("FileDocumentNode.2"), path().getFileName().toString()),Messages.getString("FileDocumentNode.0")); //$NON-NLS-1$ //$NON-NLS-2$
			if(response==Response.POSITIVE)
				Files.delete(this.filename);
		} catch (IOException e) {
			UserInteraction.error(e);
		}
	}

}
