package WORK;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/*
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection; */

public class Program {
	public static void main(String[] args) {
		
		int numberOfGerations = 100;
		City city = new City(200);
		city.startGenerations(numberOfGerations);
		System.out.println(city.getStatisticsOfGenerations());
		//Map<Integer, double []> statistics = city.getStatistics();
		
		
//		XYSeries series1 = new XYSeries("Quantidade de pessoas suscetíveis");
//		XYSeries series2 = new XYSeries("Quantidade de pessoas infectadas");
//		XYSeries series3 = new XYSeries("Quantidade de pessoas removidas");
//		
//		
//		for (int i = 0; i < numberOfGerations; i++) {
//		    series1.add(i, statistics.get(i)[0]);
//		    series2.add(i, statistics.get(i)[1]);
//		    series3.add(i, statistics.get(i)[2]);
//		}	
//
//		XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(series1);
//		dataset.addSeries(series2);
//		dataset.addSeries(series3);
//		JFreeChart chart = ChartFactory.createXYLineChart(
//			    "Variação dos estados ao longo do tempo",
//			    "Período de tempo",
//			    "Quantidades de cada estado",
//			    dataset,
//			    PlotOrientation.VERTICAL,
//			    true,
//			    true,
//			    false
//			);
//		ChartPanel chartPanel = new ChartPanel(chart);
//		chartPanel.setPreferredSize(new Dimension(1000, 500));
//		JFrame frame = new JFrame("Exemplo de gráfico de linhas");
//		frame.add(chartPanel);
//		frame.pack();
//		frame.setVisible(true);
//		
//		
		
	}
}
