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
import java.awt.Rectangle;

/**
 * Crop an image.
 * <br />The filter crops an image providing a new image, which contains only the specified rectangle of the original image.
 * @author Diego Catalano
 */
public class Crop {
    
    private Rectangle rectangle;
    
    /**
     * Initialize a new instance of the Crop class.
     * @param x start x position.
     * @param y start y position.
     * @param width new width.
     * @param height new height.
     */
    public Crop(int x, int y, int width, int height){
        this.rectangle.x = x;
        this.rectangle.y = y;
        this.rectangle.width = width;
        this.rectangle.height = height;
    }

    /**
     * Initialize a new instance of the Crop class.
     * @param rectangle Rectangle to crop.
     */
    public Crop(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    
     /**
     * Rectangle to crop
     * @return Rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Rectangle to crop
     * @param rectangle Rectangle
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    
    /**
     * Apply filter to a FastBitmap.
     * @param fastBitmap FastBitmap
     */
    public void ApplyInPlace(FastBitmap fastBitmap){
        
        FastBitmap l = new FastBitmap(rectangle.width, rectangle.height, fastBitmap.getColorSpace());
        
        if (fastBitmap.isGrayscale()) {
            for (int x = rectangle.x; x < rectangle.x + rectangle.height; x++) {
                for (int y = rectangle.y; y < rectangle.y + rectangle.width; y++) {
                    l.setGray(x - rectangle.x, y - rectangle.y, fastBitmap.getGray(x, y));
                }
            }

            fastBitmap.setImage(l);    
        }
        else{
            int X,Y;
            for (int x = rectangle.x; x < rectangle.x + rectangle.height; x++) {
                for (int y = rectangle.y; y < rectangle.y + rectangle.width; y++) {
                    X = x - rectangle.x;
                    Y = y - rectangle.y;
                    l.setRed(X, Y, fastBitmap.getRed(x, y));
                    l.setGreen(X, Y, fastBitmap.getGreen(x, y));
                    l.setBlue(X, Y, fastBitmap.getBlue(x, y));
                }
            }

            fastBitmap.setImage(l);
        }
    }
}
