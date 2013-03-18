/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Catalano.Statistics;

/**
 *
 * @author Diego Catalano
 */
public class Histogram {
    private int[]   values;
    private double  mean = 0;
    private double  stdDev = 0;
    private double  entropy = 0;
    private int     median = 0;
    private int     mode;
    private int     min;
    private int     max;
    private long    total;

    public Histogram(int[] values) {
        this.values = values;
        update();
    }

    public int[] getValues() {
        return values;
    }

    public double getMean() {
        return mean;
    }

    public double getStdDev() {
        return stdDev;
    }
    
    public double getEntropy(){
        return entropy;
    }

    public int getMedian() {
        return median;
    }
    
    public int getMode(){
        return mode;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
    
    private void update(){
        int i, n = values.length;

        max = 0;
        min = n;
        total = 0;

        // calculate min and max
        for ( i = 0; i < n; i++ )
        {
            if ( values[i] != 0 )
            {
                // max
                if ( i > max )
                    max = i;
                // min
                if ( i < min )
                    min = i;

                total += values[i];
            }
        }

        mean   = HistogramStatistics.Mean( values );
        stdDev = HistogramStatistics.StdDev( values, mean );
        median = HistogramStatistics.Median( values );
        mode = HistogramStatistics.Mode(values);
        entropy = HistogramStatistics.Entropy(values);
        
    }
    
    
    
}
