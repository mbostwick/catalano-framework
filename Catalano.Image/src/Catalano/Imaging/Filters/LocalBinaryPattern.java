/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Catalano.Imaging.Filters;

import Catalano.Imaging.FastBitmap;
import Catalano.Statistics.Histogram;

/**
 *
 * @author Diego Catalano
 */
public class LocalBinaryPattern {

    public LocalBinaryPattern() {
        
    }
    
    public Histogram ProcessImage(FastBitmap fastBitmap){
        if (!fastBitmap.isGrayscale()) {
            try {
                throw new Exception("LBP works only with grayscale images.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        int width = fastBitmap.getWidth() - 1;
        int height = fastBitmap.getHeight() - 1;
        
        int sum;
        int[] g = new int[256];
        int gray;
        for (int x = 1; x < height; x++) {
            for (int y = 1; y < width; y++) {
                gray = fastBitmap.getGray(x, y);
                sum = 0;
                if (gray < fastBitmap.getGray(x - 1, y - 1))    sum += 128;
                if (gray < fastBitmap.getGray(x - 1, y))        sum += 64;
                if (gray < fastBitmap.getGray(x - 1, y + 1))    sum += 32;
                if (gray < fastBitmap.getGray(x, y + 1))        sum += 16;
                if (gray < fastBitmap.getGray(x + 1, y + 1))    sum += 8;
                if (gray < fastBitmap.getGray(x + 1, y))        sum += 4;
                if (gray < fastBitmap.getGray(x + 1, y - 1))    sum += 2;
                if (gray < fastBitmap.getGray(x, y - 1))        sum += 1;
                g[sum]++;
            }
        }
        return new Histogram(g);
    }
}