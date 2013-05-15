// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2013
// diego.catalano at live.com
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
import Catalano.Imaging.IBaseInPlace;
import Catalano.Math.Matrix;

/**
 * Erosion operator from Mathematical Morphology.
 * The filter assigns minimum value of surrounding pixels to each pixel of the result image. Surrounding pixels, which should be processed, are specified by structuring element: 1 - to process the neighbor, 0 - to skip it.
 * The filter especially useful for binary image processing, where it removes pixels, which are not surrounded by specified amount of neighbors. It gives ability to remove noisy pixels (stand-alone pixels) or shrink objects.
 * 
 * The filter accepts 8, 24 bpp images for processing.
 * 
 * @author Diego Catalano
 */
public class Erosion implements IBaseInPlace{
    
    private int radius = 0;
    private int[][] kernel;
    private int[][] mask;

    /**
     * Initialize a new instance of the Erosion class.
     */
    public Erosion() {
        this.radius = 1;
    }

    /**
     * Initialize a new instance of the Erosion class.
     * @param radius Radius.
     */
    public Erosion(int radius) {
        this.radius = Math.max(radius,1);
    }

    /**
     * Initialize a new instance of the Erosion class.
     * @param kernel Kernel.
     */
    public Erosion(int[][] kernel) {
        this.kernel = kernel;
    }

    @Override
    public void applyInPlace(FastBitmap fastBitmap) {
        
        int height = fastBitmap.getHeight();
        int width = fastBitmap.getWidth();
        
        if (fastBitmap.isGrayscale()){
            if (kernel == null)
                createKernel(radius);
            this.mask = createMask(radius);
            
            FastBitmap copy = new FastBitmap(fastBitmap);
            for (int i = 1; i < height; i++) {
                for (int j = 1; j < width; j++) {
                    
                    int X = 0,Y;
                    for (int x = i - radius; x < i + radius + 1; x++) {
                        Y = 0;
                        for (int y = j - radius; y < j + radius + 1; y++) {
                            
                            if (x >= 0 && x < height && y >= 0 && y < width){
                                mask[X][Y] = copy.getGray(x, y) - kernel[X][Y];
                            }
                            Y++;
                        }
                        X++;
                    }
                    int min = Matrix.Min(mask);
                    min = min <  0 ? 0 : min;
                    fastBitmap.setGray(i, j, min);
                }
            }
        }
        if (fastBitmap.isRGB()){
            if (kernel == null)
                createKernel(radius);
            int[][] redMask = createMask(radius);
            int[][] greenMask = createMask(radius);
            int[][] blueMask = createMask(radius);
            
            FastBitmap copy = new FastBitmap(fastBitmap);
            for (int i = 1; i < height; i++) {
                for (int j = 1; j < width; j++) {
                    
                    int X = 0,Y;
                    for (int x = i - radius; x < i + radius + 1; x++) {
                        Y = 0;
                        for (int y = j - radius; y < j + radius + 1; y++) {
                            
                            if (x >= 0 && x < height && y >= 0 && y < width){
                                redMask[X][Y] = copy.getRed(x, y) - kernel[X][Y];
                                greenMask[X][Y] = copy.getGreen(x, y) - kernel[X][Y];
                                blueMask[X][Y] = copy.getBlue(x, y) - kernel[X][Y];
                            }
                            Y++;
                        }
                        X++;
                    }
                    int minRed = Matrix.Min(redMask);
                    int minGreen = Matrix.Min(greenMask);
                    int minBlue = Matrix.Min(blueMask);
                    
                    minRed = minRed <  0 ? 0 : minRed;
                    minGreen = minGreen <  0 ? 0 : minGreen;
                    minBlue = minBlue <  0 ? 0 : minBlue;
                    
                    fastBitmap.setRGB(i, j, minRed, minGreen, minBlue);
                }
            }
        }
    }
    
    private void createKernel(int radius){
        int size = radius * 2 + 1;
        this.kernel = new int[size][size];
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                kernel[i][j] = 1;
            }
        }
    }
    
    private int[][] createMask(int radius){
        int size = radius * 2 + 1;
        int[][] mask = new int[size][size];
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[0].length; j++) {
                mask[i][j] = 0;
            }
        }
        return mask;
    }
}