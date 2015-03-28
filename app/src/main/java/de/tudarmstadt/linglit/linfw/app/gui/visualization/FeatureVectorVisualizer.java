package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.annotation.FeatureVector;
import de.tudarmstadt.linglit.linfw.app.model.AnnotationHandler;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;

public class FeatureVectorVisualizer implements Visualizer<FeatureVector> {

	@Override
	public List<Visualization> visualize(
			Annotation<? extends FeatureVector> annotation) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		FeatureVector vector = annotation.value();
		for(Object feature : vector.features())
			dataset.addValue(vector.value(feature), "a", feature.toString());
		JFreeChart chart = ChartFactory.createBarChart("Vector", "Tokens", "Value", dataset, 
				PlotOrientation.HORIZONTAL,false,false,false);
		chart.getLegend().setVisible(false);
		CategoryPlot plot = (CategoryPlot)chart.getPlot();
		plot.getDomainAxis().setVisible(false);
		return Collections.singletonList(new Visualization("Histogram", new ImageIcon("icons/note.png"), new ChartPanel(chart)));
	}

	@Override
	public LayerVisualization visualizeLayer() {
		final PlaceholderVisualization histogramVisualization = new PlaceholderVisualization("Histogram", new ImageIcon("icons/note.png")); //$NON-NLS-1$ //$NON-NLS-2$
		
		AnnotationHandler<FeatureVector> handler = new AnnotationHandler<FeatureVector>() {
			private FeatureVector featureVector = FeatureVector.empty();
			@Override
			public void process(Object controller, ReadableLayer<FeatureVector> layer,
					CharSequence text, List<Annotation<FeatureVector>> annotations) {
				for(Annotation<FeatureVector> annotation : annotations)
				this.featureVector = this.featureVector.plus(annotation.value());
			}
			
			@Override
			public void finish(Object controller) {
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				for(Object feature : featureVector.features())
					dataset.addValue(featureVector.value(feature), "a", feature.toString());
				JFreeChart chart = ChartFactory.createBarChart("Vector", "Tokens", "Value", dataset,
						PlotOrientation.HORIZONTAL,false,false,false);
				chart.getLegend().setVisible(false);
				CategoryPlot plot = (CategoryPlot)chart.getPlot();
				plot.getDomainAxis().setVisible(false);
				histogramVisualization.setComponent(new ChartPanel(chart));
				
			}
		};
		
		return new LayerVisualization(handler,Collections.singletonList(histogramVisualization));
	}

	@Override
	public Class<FeatureVector> type() {
		return FeatureVector.class;
	}

}
