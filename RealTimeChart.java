import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * Creates a real-time chart using SwingWorker
 */
public class RealTimeChart {

    public SwingWorker mySwingWorker;
    public SwingWrapper<XYChart> sw;
    public XYChart chart;
    public JFrame chart_frame;


    public void show(){
        // Create Chart
        chart = QuickChart.getChart("Generic Chart", "X", "Y", "randomWalk", new double[] { 0 }, new double[] { 0 });
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisTicksVisible(false);

        // Show it
        sw = new SwingWrapper<XYChart>(chart);
        chart_frame = sw.displayChart();
        chart_frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        chart_frame.setTitle("Generic Char");
        chart_frame.validate();
        chart_frame.repaint();


    }

    public void go() {


        mySwingWorker = new MySwingWorker();
        mySwingWorker.execute();
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {

        LinkedList<Double> fifo = new LinkedList<Double>();

        public MySwingWorker() {

            fifo.add(0.0);
        }

        @Override
        protected Boolean doInBackground() throws Exception {

            while (!isCancelled()) {

                fifo.add(fifo.get(fifo.size() - 1) + Math.random() - .5);
                if (fifo.size() > 500) {
                    fifo.removeFirst();
                }

                double[] array = new double[fifo.size()];
                for (int i = 0; i < fifo.size(); i++) {
                    array[i] = fifo.get(i);
                }
                publish(array);

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    // eat it. caught when interrupt is called
                    System.out.println("MySwingWorker shut down.");
                }

            }

            return true;
        }

        @Override
        protected void process(List<double[]> chunks) {

            System.out.println("number of chunks: " + chunks.size());

            double[] mostRecentDataSet = chunks.get(chunks.size() - 1);
            chart.updateXYSeries("randomWalk", null, mostRecentDataSet, null);
            sw.repaintChart();

            long start = System.currentTimeMillis();
            long duration = System.currentTimeMillis() - start;
            try {
                Thread.sleep(40 - duration); // 40 ms ==> 25fps
                // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
            } catch (InterruptedException e) {
            }

        }
    }
}