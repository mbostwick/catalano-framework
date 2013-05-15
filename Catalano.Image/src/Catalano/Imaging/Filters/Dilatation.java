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
 * Dilatation operator from Mathematical Morphology.
 * The filter assigns maximum value of surrounding pixels to each pixel of the result image. Surrounding pixels, which should be processed, are specified by structuring element: 1 - to process the neighbor, 0 - to skip it.
 * The filter especially useful for binary image processing, where it allows to grow separate objects or join objects.
 * 
 * The filter accepts 8, 24 bpp images for processing.
 * 
 * @author Diego Catalano
 */
public class Dilatation implements IBaseInPlace{
    
    private int radius = 0;
    private int[][] kernel;
    private int[][] mask;

    /**
     * Initialize a new instance of the Dilatation class.
     */
    public Dilatation() {
        this.radius = 1;
    }

    /**
     * Initialize a new instance of the Dilatation class.
     * @param radius Radius.
     */
    public Dilatation(int radius) {
        this.radius = Math.max(radius,1);
    }

    /**
     * Initialize a new instance of the Dilatation class.
     * @param kernel Kernel.
     */
    public Dilatation(int[][] kernel) {
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
                                mask[X][Y] = copy.getGray(x, y) + kernel[X][Y];
                            }
                            Y++;
                        }
                        X++;
                    }
                    int max = Matrix.Max(mask);
                    max = max >  255 ? 255 : max;
                    fastBitmap.setGray(i, j, max);
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
                                redMask[X][Y] = copy.getRed(x, y) + kernel[X][Y];
                                greenMask[X][Y] = copy.getGreen(x, y) + kernel[X][Y];
                                blueMask[X][Y] = copy.getBlue(x, y) + kernel[X][Y];
                            }
                            Y++;
                        }
                        X++;
                    }
                    int maxRed = Matrix.Max(redMask);
                    int maxGreen = Matrix.Max(greenMask);
                    int maxBlue = Matrix.Max(blueMask);
                    
                    maxRed = maxRed >  255 ? 255 : maxRed;
                    maxGreen = maxGreen >  255 ? 255 : maxGreen;
                    maxBlue = maxBlue >  255 ? 255 : maxBlue;
                    
                    fastBitmap.setRGB(i, j, maxRed, maxGreen, maxBlue);
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