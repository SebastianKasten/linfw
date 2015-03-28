package de.tudarmstadt.linglit.linfw.app.gui.visualization;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.jdesktop.swingx.action.BoundAction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYStepAreaRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import de.tudarmstadt.linglit.linfw.app.LayerController;
import de.tudarmstadt.linglit.linfw.app.Messages;
import de.tudarmstadt.linglit.linfw.app.model.AnnotationHandler;
import de.tudarmstadt.linglit.linfw.core.annotation.Annotation;
import de.tudarmstadt.linglit.linfw.core.layer.ReadableLayer;
import de.tudarmstadt.linglit.linfw.core.text.TextAreas;
import de.tudarmstadt.linglit.linfw.core.text.TextSpan;

public class DefaultVisualizer implements Visualizer<Object> {

	@Override
	public List<Visualization> visualize(final Annotation<?> annotation) {
		List<Visualization> result = new ArrayList<>();

		JPanel areaPanel = new JPanel(new BorderLayout());
		BoundAction copyAction = new BoundAction(Messages.getString("DefaultVisualizer.4"),new ImageIcon("icons/copy.png")); //$NON-NLS-1$ //$NON-NLS-2$
		copyAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
						new StringSelection(annotation.area().coveredText().toString()), 
						new ClipboardOwner() {					
					@Override
					public void lostOwnership(Clipboard clipboard, Transferable contents) {	}
				});
			}
		});
		JTextPane areaField  = new JTextPane();
		areaField.setContentType("text/html"); //$NON-NLS-1$
		areaField.setEditable(false);
		areaField.setText(HTMLUtils.styleTextArea(annotation.area()));
		areaPanel.add(areaField,BorderLayout.CENTER);
		areaPanel.add(new JButton(copyAction),BorderLayout.EAST);
		
		result.add(new Visualization(Messages.getString("DefaultVisualizer.7"),new ImageIcon("icons/annotation.png"),areaPanel)); //$NON-NLS-1$ //$NON-NLS-2$

		JTextPane valueField = new JTextPane();
		valueField.setContentType("text"); //$NON-NLS-1$
		valueField.setEditable(false);
		if(annotation.value().toString().length()<=400)
			valueField.setText(annotation.value().toString());
		else
			valueField.setText(annotation.value().toString().substring(0, 400)+"..."); //$NON-NLS-1$

		result.add(new Visualization(Messages.getString("DefaultVisualizer.10"),new ImageIcon("icons/note.png"),valueField)); //$NON-NLS-1$ //$NON-NLS-2$

		return result;
	}

	private static final Ordering<Entry<?>> INCREASING_COUNT_ORDERING = new Ordering<Entry<?>>() {
	    @Override
	    public int compare(Entry<?> entry1, Entry<?> entry2) {
	      return Ints.compare(entry1.getCount(), entry2.getCount());
	    }
	  };
	  
	  public static <E> List<Entry<E>> copyLowestCountFirst(Multiset<E> multiset) {
		   return INCREASING_COUNT_ORDERING.sortedCopy(multiset.entrySet());
		  }
	@Override
	public LayerVisualization visualizeLayer() {
		List<Visualization> result = new ArrayList<>();

		final PlaceholderVisualization numberVisualization = new PlaceholderVisualization(Messages.getString("DefaultVisualizer.21"), new ImageIcon("icons/annotation.png")); //$NON-NLS-1$ //$NON-NLS-2$
		result.add(numberVisualization); 
		final PlaceholderVisualization ratioVisualization = new PlaceholderVisualization(Messages.getString("DefaultVisualizer.1"), new ImageIcon("icons/annotation.png")); //$NON-NLS-1$ //$NON-NLS-2$
		result.add(ratioVisualization); 
		final PlaceholderVisualization giniVisualization = new PlaceholderVisualization("Gini coefficent", new ImageIcon("icons/annotation.png")); //$NON-NLS-1$ //$NON-NLS-2$
		result.add(giniVisualization);
		final PlaceholderVisualization entropyVisualization = new PlaceholderVisualization(Messages.getString("DefaultVisualizer.25"), new ImageIcon("icons/annotation.png")); //$NON-NLS-1$ //$NON-NLS-2$
		result.add(entropyVisualization); 
		final PlaceholderVisualization distributionVisualization = new PlaceholderVisualization(Messages.getString("DefaultVisualizer.27"),  new ImageIcon("icons/annotation.png")); //$NON-NLS-1$ //$NON-NLS-2$
		result.add(distributionVisualization); 
		final PlaceholderVisualization lengthVisualization = new PlaceholderVisualization(Messages.getString("DefaultVisualizer.3"), new ImageIcon("icons/annotation.png"));   //$NON-NLS-1$//$NON-NLS-2$
		result.add(lengthVisualization); 
		
		final Multiset<Object> bag = ConcurrentHashMultiset.create();
		final AtomicInteger coveredCharacters = new AtomicInteger();
		final AtomicInteger numAnnotations = new AtomicInteger();
		
		AnnotationHandler<Object> handler = new AnnotationHandler<Object>() {

			@Override
			public void process(Object controller,
					ReadableLayer<Object> layer, CharSequence text, List<Annotation<Object>> annotations) {
				for(Annotation<?> annotation : annotations) {
					bag.add(annotation.value());
					if(annotation.area()==annotation.area().closure()) 
						coveredCharacters.addAndGet(annotation.area().closure().length());
					 else {
						 Iterable<TextSpan> spans = TextAreas.splitSpans(annotation.area());
						 for(TextSpan span : spans)
							 coveredCharacters.addAndGet(span.length());
					 }
					numAnnotations.incrementAndGet();
				}
			}

		
			@Override
			public void finish(Object controller) {
				final JTextPane numberField  = new JTextPane();
				numberField.setEditable(false);
				numberField.setText(bag.size() +Messages.getString("DefaultVisualizer.0")+ bag.elementSet().size()+Messages.getString("DefaultVisualizer.17")); //$NON-NLS-1$ //$NON-NLS-2$
				numberVisualization.setComponent(numberField);
				
				double ratio = (double)bag.elementSet().size()/bag.size();
				final JProgressBar ratioBar = new JProgressBar(0, 100);
				ratioBar.setStringPainted(true);
				ratioBar.setValue((int)(ratio*100));
				ratioBar.setString(String.format("%.2f %%", ratio*100)); //$NON-NLS-1$
				ratioVisualization.setComponent(ratioBar);
				
				final JTextPane entropyField  = new JTextPane();
				entropyField.setEditable(false);
				entropyField.setText(Messages.getString("DefaultVisualizer.2")); //$NON-NLS-1$
				double entropy = 0.0;
				int total = bag.size();
				for(Entry<?> entry : bag.entrySet()) {
					double pi = (double)entry.getCount()/total;
					entropy -= pi*(Math.log(pi)/Math.log(2)+1e-10); 
				}
				entropyField.setText(String.format("%.2f Sh",entropy)); //$NON-NLS-1$
				entropyVisualization.setComponent(entropyField);
				
				List<Entry<Object>> lowestFirst = copyLowestCountFirst(bag);
				final JTextPane giniField = new JTextPane();
				giniField.setEditable(false);
				double giniNumerator = 0.0d;
				double giniDenominator = 0.0d;
				int i = 0;
				for(Entry<?> entry : lowestFirst) {
					giniNumerator += (i + 1) * entry.getCount();
					giniDenominator += entry.getCount();
					i++;
				}
				giniNumerator = giniNumerator * 2;
				giniDenominator = i * giniDenominator;
				final double gini = giniNumerator / giniDenominator - (i+1)/i;
				giniField.setText(String.format("%.2f",Double.valueOf(gini))); //$NON-NLS-1$
				giniVisualization.setComponent(giniField);
				
				final XYSeriesCollection dataset = new XYSeriesCollection();
				final XYSeriesCollection zipfDataset = new XYSeriesCollection();
				final JFreeChart distributionChart = ChartFactory.createScatterPlot(null, null, 
						Messages.getString("DefaultVisualizer.14"), zipfDataset,
						PlotOrientation.HORIZONTAL,false,false,false); //$NON-NLS-1$
				distributionChart.removeLegend();
				final XYPlot xyPlot = distributionChart.getXYPlot();
				xyPlot.getDomainAxis().setVisible(false);
				xyPlot.setRenderer(1,new XYStepAreaRenderer());
				final XYLineAndShapeRenderer lineRenderer = new XYLineAndShapeRenderer();
				lineRenderer.setBaseShapesVisible(false);
				xyPlot.setRenderer(0,lineRenderer);
				xyPlot.setDataset(0, zipfDataset);
				xyPlot.setDataset(1, dataset);
				i=0;
				final ImmutableMultiset<?> freq = Multisets.copyHighestCountFirst(bag);
				final XYSeries series = new XYSeries(Messages.getString("DefaultVisualizer.19")); //$NON-NLS-1$
				for(Object o : freq.elementSet()) 
					series.add(i++, (double)freq.count(o)/freq.size());

				dataset.addSeries(series);
				final XYSeries zipf = new XYSeries("Zipf"); //$NON-NLS-1$
				for(int j=0; j<i; j++) {
					zipf.add(j,1.0d/((j+1)*Math.log(1.78*freq.elementSet().size())));
				}
				zipfDataset.addSeries(zipf);
				distributionVisualization.setComponent(new ChartPanel(distributionChart));
				
				final JTextPane lengthField  = new JTextPane();
				numberField.setEditable(false);
				double length = coveredCharacters.doubleValue() / numAnnotations.doubleValue();
				lengthField.setText(String.format("%.2f"+Messages.getString("DefaultVisualizer.5"), Double.valueOf(length))); //$NON-NLS-1$ //$NON-NLS-2$
				lengthVisualization.setComponent(lengthField);
			}
		};
		
		return new LayerVisualization(handler, result);
	}

	@Override
	public Class<Object> type() {
		return Object.class;
	}

}
