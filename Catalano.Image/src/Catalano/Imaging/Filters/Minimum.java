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

/**
 * Minimum filter.
 * <br /> Minimum filter - set minimum pixel values using radius.
 * @author Diego Catalano
 */
public class Minimum implements IBaseInPlace{
    
    private int radius = 1;

    /**
     * Initialize a new instance of the Maximum class.
     */
    public Minimum() {
        
    }
    
    /**
     * Initialize a new instance of the Maximum class.
     * @param radius Radius.
     */
    public Minimum(int radius){
        radius = radius < 1 ? 1 : radius;
        this.radius = radius;
    }

    /**
     * Radius.
     * @return Radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Radius.
     * @param radius Radius.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    @Override
    public void applyInPlace(FastBitmap sourceImage){
        
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();

        FastBitmap copy = new FastBitmap(sourceImage);
        int Xline,Yline;
        int lines = CalcLines(radius);

        if (sourceImage.isGrayscale()) {
            int sumGray;
            int c;
            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    c = 0;
                    sumGray = 255;
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i-radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j-radius);
                            if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                sumGray = Math.min(sumGray,copy.getGray(Xline, Yline));
                                c++;
                            }
                        }
                    }
                    sourceImage.setGray(x, y, sumGray);
                }
            }
        }
        else{
            int sumR;
            int sumG;
            int sumB;
            int c;

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    c = 0;
                    sumR = sumG = sumB = 255;
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i-radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j-radius);
                            if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                sumR = Math.min(sumR,copy.getRed(Xline, Yline));
                                sumG = Math.min(sumG,copy.getGreen(Xline, Yline));
                                sumB = Math.min(sumB,copy.getBlue(Xline, Yline));
                                c++;
                            }
                        }
                    }
                    sourceImage.setRGB(x, y, sumR, sumG, sumB);
                }
            }
        }
    }
    
    private int CalcLines(int radius){
        return radius * 2 + 1;
    }
}
