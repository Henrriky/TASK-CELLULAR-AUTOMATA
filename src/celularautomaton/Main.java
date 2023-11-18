package celularautomaton;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {
	public static void main(String[] args) {
		World a = new World(200);
		int generationsNumber = 250;
		
		XYSeries series1 = new XYSeries("Pessoas suscetíveis");
		XYSeries series2 = new XYSeries("Pessoas infectadas");
		XYSeries series3 = new XYSeries("Pessoas recuperadas");
		
		series1.add(0, a.getSuscetibleCells());
		series2.add(0, a.getInfectedCells());
		series3.add(0, a.getRecoveredCells());
		
		for (int i = 0; i < generationsNumber; i++) {
			
			a.statistics();
			a.nextGeneration();
			series1.add(i+1, a.getSuscetibleCells());
			series2.add(i+1, a.getInfectedCells());
			series3.add(i+1, a.getRecoveredCells());
		}
		
		
		

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		JFreeChart chart = ChartFactory.createXYLineChart(
			    "Variação dos stados ao longo do tempo",
			    "Período de tempo",
			    "Quantidade de Pessoas",
			    dataset,
			    PlotOrientation.VERTICAL,
			    true,
			    true,
			    false
			);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1280, 720));
		JFrame frame = new JFrame("Exemplo de gráfico de linhas");
		frame.add(chartPanel);
		frame.pack();
		frame.setVisible(true);
	
		
	}

}
