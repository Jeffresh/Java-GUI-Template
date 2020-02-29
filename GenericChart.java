import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;


public class GenericChart {

    public JPanel chartpanel;
    public XYChart chart;
    double phase;


    public GenericChart() {

        phase = 0;
        double[][] initdata = getData(phase);
        chart = QuickChart.getChart("Generic Chart", "Radians", "Sine",
                "sine", initdata[0], initdata[1]);
        chartpanel = new XChartPanel(chart);


    }

    private void process() {

        while (true) {

            phase += 2 * Math.PI * 2 / 20.0;


            final double[][] data = getData(phase);

            chart.updateXYSeries("sine", data[0], data[1], null);
//            chart.updateXYSeries("sine", data[0], data[1], null);
            chartpanel.validate();
            chartpanel.repaint();
        }

    }

    private double[][] getData(double phase) {

        double[] xData = new double[100];
        double[] yData = new double[100];
        for (int i = 0; i < xData.length; i++) {
            double radians = phase + (2 * Math.PI / xData.length * i);
            xData[i] = radians;
            yData[i] = Math.sin(radians);
        }
        return new double[][]{xData, yData};
    }
}

