import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;


public class GenericChart {

    public JPanel charpanel;
    public XYChart chart;
    double phase;


    public GenericChart(){

        phase = 0;
        double[][] initdata = getData(phase);
        chart = QuickChart.getChart("Generic Chart", "Radians", "Sine", "sine", initdata[0], initdata[1]);
        charpanel = new XChartPanel(chart);


    }

    private void process() throws InterruptedException {

        while (true) {

            phase += 2 * Math.PI * 2 / 20.0;

            Thread.sleep(100);

            final double[][] data = getData(phase);

            chart.updateXYSeries("sine", data[0], data[1], null);
            charpanel.validate();
            charpanel.repaint();
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

