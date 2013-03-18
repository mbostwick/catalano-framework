// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2013
// diego.catalano at live.com
//
// Copyright © Andrew Kirillov, 2007-2008
// andrew.kirillov at gmail.com
//
// In Aforge.NET, its called ComplexImage. But i adapted to this framework, i just changed the name.
//
//    This library is free software; you can redistribute it and/or
//    modify it under the terms of the GNU Lesser General Public
//    License as published by the Free Software Foundation; either
//    version 2.1 of the License, or (at your option) any later version.
//
//    This library is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//    Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with this library; if not, write to the Free Software
//    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
//

package Catalano.Imaging.Filters;

import Catalano.Imaging.FastBitmap;
import Catalano.Statistics.Histogram;

/**
 * Gather statistics about image in RGB color space.
 * @author Diego Catalano
 */
public class ImageStatistics {
    
    private Histogram gray;
    private Histogram red;
    private Histogram green;
    private Histogram blue;
    
    private int pixels;

    /**
     * Histogram of gray channel.
     * @return Histogram.
     */
    public Histogram getHistogramGray(){
        if (gray == null) {
            try {
                throw new Exception("Histogram gray null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gray;
    }
    
    /**
     * Histogram of red channel.
     * @return Histogram.
     */
    public Histogram getHistogramRed() {
        if (red == null) {
            try {
                throw new Exception("Histogram red null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return red;
    }
    
    /**
     * Histogram of green channel.
     * @return Histogram.
     */
    public Histogram getHistogramGreen() {
        if (green == null) {
            try {
                throw new Exception("Histogram green null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return green;
    }
        
    /**
     * Histogram of blue channel.
     * @return Histogram.
     */
    public Histogram getHistogramBlue() {
        if (blue == null) {
            try {
                throw new Exception("Histogram blue null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return blue;
    }
    
    
    
    /**
     * Initialize a new instance of the ImageStatistics class.
     * @param fastBitmap Image to be processed.
     */
    public ImageStatistics(FastBitmap fastBitmap) {
        int width = fastBitmap.getWidth();
        int height = fastBitmap.getHeight();
        
        pixels = 0;
        red = green = blue = gray = null;
        
        if (fastBitmap.isGrayscale()) {
            int[] g = new int[256];
            
            int G;
            
            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    G = fastBitmap.getGray(x, y);
                    
                    g[G]++;
                    pixels++;
                }
            }
            
            gray = new Histogram(g);
            
        }
        else if (fastBitmap.isRGB()){
            int[] r = new int[256];
            int[] g = new int[256];
            int[] b = new int[256];

            int R,G,B;

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    R = fastBitmap.getRed(x, y);
                    G = fastBitmap.getGreen(x, y);
                    B = fastBitmap.getBlue(x, y);

                    r[R]++;
                    g[G]++;
                    b[B]++;
                    pixels++;
                }
            }
            red = new Histogram(r);
            green = new Histogram(r);
            blue = new Histogram(r);
        }
        
    }

    /**
     * Count pixels.
     * @return amount of pixels.
     */
    public int PixelsCount() {
        return pixels;
    }
}