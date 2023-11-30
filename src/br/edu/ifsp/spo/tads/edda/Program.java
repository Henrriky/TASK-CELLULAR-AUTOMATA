package br.edu.ifsp.spo.tads.edda;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import br.edu.ifsp.spo.tads.edda.environment.City;
import br.edu.ifsp.spo.tads.edda.states.StatePossibles; 

public class Program {
	public static void main(String[] args) throws IOException {

		int numberOfGerations = 100;
		int size = 200;
		City city = new City(size);
		city.startGenerations(numberOfGerations);
		List<HashMap<String, Integer>> statistics = city.getStatisticsOfGenerations();
		
		
        XYChart chart = new XYChartBuilder()
                .theme(ChartTheme.GGPlot2)
                .width(1600)
                .height(800)
                .title("Variação dos estados ao longo do tempo")
                .xAxisTitle("Gerações")
                .yAxisTitle("Quantidade de pessoas em cada estado")
                .build();
        chart.getStyler().setYAxisMax((double) size * size);
        chart.getStyler().setPlotContentSize(0.999);
        
        //Percorre as três categorias e coloca cada uma no gráfico
        for (String categoria : statistics.get(0).keySet()) {
            int index = 0;
             //Transforma todos os dados da categoria em um array
            double[] yAxisValues = statistics
            		.stream()
                    .mapToDouble(stat -> stat.get(categoria))
                    .toArray();
            //Cria o array contendo o numero de gerações
            double[] xAxisValues = new double[yAxisValues.length];
            for (int i = 0; i < xAxisValues.length; i++) {
            	xAxisValues[i] = index++;
            }

            XYSeries series = null;

            if (categoria.equals(StatePossibles.INFECTADO.getStateName())) {
            	series = chart.addSeries("Infectados", xAxisValues, yAxisValues);
            	series.setLineStyle(SeriesLines.SOLID);
                series.setLineColor(Color.RED);
            } else if (categoria.equals(StatePossibles.RECUPERADO.getStateName())) {
            	series = chart.addSeries("Recuperados", xAxisValues, yAxisValues);
            	series.setLineStyle(SeriesLines.SOLID);
                series.setLineColor(Color.BLUE);
            } else if (categoria.equals(StatePossibles.SUSCETIVEL.getStateName())) {
            	series = chart.addSeries("Suscetíveis", xAxisValues, yAxisValues);
                series.setLineStyle(SeriesLines.SOLID);
                series.setLineColor(Color.GREEN);
            }


            series.setMarker(SeriesMarkers.NONE);
        }

        new SwingWrapper<>(chart).displayChart();
        BitmapEncoder.saveBitmap(chart, "./chart/GRAFICO_AUTOMATO_CELULAR", BitmapFormat.PNG);			
	}
}