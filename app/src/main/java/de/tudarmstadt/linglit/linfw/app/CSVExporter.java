package de.tudarmstadt.linglit.linfw.app;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.io.Files;

import de.tudarmstadt.linglit.linfw.app.annotation.FeatureVector;
import de.tudarmstadt.linglit.linfw.app.model.Corpus;
import de.tudarmstadt.linglit.linfw.app.model.CorpusDocument;

public class CSVExporter {
	public static final CSVExporter INSTANCE = new CSVExporter();
	
	private static final Joiner joiner = Joiner.on(';');
	public void export(Corpus corpus, Path path) throws IOException {
		try(BufferedWriter writer = Files.newWriter(path.toFile(), Charsets.UTF_8)) {
			Table<CorpusDocument, String, Double> table = Tables.newCustomTable(new HashMap<CorpusDocument, Map<String,Double>>(), new Supplier<Map<String, Double>>() {

				@Override
				public Map<String, Double> get() {
					return new TreeMap<>();
				}
			});

			for(CorpusDocument document : corpus.documents()) {
				FeatureVector featureVector = document.forType(FeatureVector.class).arbitrary().next().value();
				for(Object feature : featureVector.features()) {
					table.put(document, feature.toString(), Double.valueOf(featureVector.value(feature)));
				}
			}
			List<String> columns = Lists.newArrayList(table.columnKeySet());
			List<CorpusDocument> rows = Lists.newArrayList(table.rowKeySet());
			
			for(int i=0; i<columns.size(); i++)
				writer.write("\""+columns.get(i)+"\""+((i<columns.size()-1) ? "," : "\n")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				
//			writer.write(joiner.join(columns)+"\n"); //$NON-NLS-1$
			for(int j=0; j<rows.size(); j++) {
				CorpusDocument document = rows.get(j);
				for(int i=0; i<columns.size(); i++)
					if(table.contains(document, columns.get(i)))
						writer.write(table.get(document, columns.get(i))+((i<columns.size()-1) ? "," : ((j<rows.size()-1) ? "\n" : ""))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					else
						writer.write("0.0"+((i<columns.size()-1) ? "," : ((j<rows.size()-1) ? "\n" : ""))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
		}
	}
	
	private CSVExporter() {}
}
