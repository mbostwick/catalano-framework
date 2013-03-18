/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// verificar numerador e denominador em ingles
package Catalano.Imaging.Filters;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.IBaseInPlace;
/**
 *
 * @author Diego Catalano
 */
public class Mean implements IBaseInPlace{
    private int radius = 1;
    public enum Arithmetic {Mean,Harmonic,ContraHarmonic,Geometry};
    private Arithmetic arithmetic = Arithmetic.Mean;
    private int orderFilter = 1;

    public Mean() {
        
    }

    public Mean(int radius) {
        radius = radius < 1 ? 1 : radius;
        this.radius = radius;
    }

    public Mean(Arithmetic arithmetic) {
        this.arithmetic = arithmetic;
    }
    
    public Mean(int radius, Arithmetic arithmetic) {
        radius = radius < 1 ? 1 : radius;
        this.radius = radius;
        this.arithmetic = arithmetic;
    }

    public Arithmetic getArithmetic() {
        return arithmetic;
    }

    public void setArithmetic(Arithmetic arithmetic) {
        this.arithmetic = arithmetic;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getOrderFilter() {
        return orderFilter;
    }

    public void setOrderFilter(int orderFilter) {
        this.orderFilter = orderFilter;
    }
    
    @Override
    public void applyInPlace(FastBitmap fastBitmap){
        
        int width = fastBitmap.getWidth();
        int height = fastBitmap.getHeight();
        int Xline,Yline;
        int lines = CalcLines(radius);
        int c;
        
        FastBitmap copy = new FastBitmap(fastBitmap.toBufferedImage());
        
        switch(arithmetic){
            case Mean:
                if (fastBitmap.isGrayscale()) {
                    int sumGray;

                    for (int x = 0; x < height; x++) {
                        for (int y = 0; y < width; y++) {
                            c = 0;
                            sumGray = 0;
                            for (int i = 0; i < lines; i++) {
                                Xline = x + (i-radius);
                                for (int j = 0; j < lines; j++) {
                                    Yline = y + (j-radius);
                                    if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                        sumGray += copy.getGray(Xline, Yline);
                                        c++;
                                    }
                                }
                            }
                            sumGray /= c;
                            fastBitmap.setGray(x, y, sumGray);
                        }
                    }
                }
                else if(fastBitmap.isRGB()){
                    int sumR;
                    int sumG;
                    int sumB;

                    for (int x = 0; x < height; x++) {
                        for (int y = 0; y < width; y++) {
                            c = 0;
                            sumR = sumG = sumB = 0;
                            for (int i = 0; i < lines; i++) {
                                Xline = x + (i-radius);
                                for (int j = 0; j < lines; j++) {
                                    Yline = y + (j-radius);
                                    if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                        sumR += copy.getRed(Xline, Yline);
                                        sumG += copy.getGreen(Xline, Yline);
                                        sumB += copy.getBlue(Xline, Yline);
                                        c++;
                                    }
                                }
                            }
                            sumR /= c;
                            sumG /= c;
                            sumB /= c;
                            fastBitmap.setRGB(x, y, sumR, sumG, sumB);
                        }
                    }
                }
            break;
                
            case Harmonic:
                if (fastBitmap.isGrayscale()) {
                    double sumGray;
                    for (int x = 0; x < height; x++) {
                        for (int y = 0; y < width; y++) {
                            c = 0;
                            sumGray = 0;
                            for (int i = 0; i < lines; i++) {
                                Xline = x + (i-radius);
                                for (int j = 0; j < lines; j++) {
                                    Yline = y + (j-radius);
                                    if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                        sumGray += 1/(double)copy.getGray(Xline, Yline);
                                        c++;
                                    }
                                }
                            }
                            sumGray = c / sumGray;
                            fastBitmap.setGray(x, y, (int)sumGray);
                        }
                    }
                }
                else if(fastBitmap.isRGB()){
                    double sumR;
                    double sumG;
                    double sumB;

                    for (int x = 0; x < height; x++) {
                        for (int y = 0; y < width; y++) {
                            c = 0;
                            sumR = sumG = sumB = 0;
                            for (int i = 0; i < lines; i++) {
                                Xline = x + (i-radius);
                                for (int j = 0; j < lines; j++) {
                                    Yline = y + (j-radius);
                                    if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                        sumR += 1/(double)copy.getRed(Xline, Yline);
                                        sumG += 1/(double)copy.getGreen(Xline, Yline);
                                        sumB += 1/(double)copy.getBlue(Xline, Yline);
                                        c++;
                                    }
                                }
                            }
                            sumR = c / sumR;
                            sumG = c / sumG;
                            sumB = c / sumB;
                            fastBitmap.setRGB(x, y, (int)sumR, (int)sumG, (int)sumB);
                        }
                    }
                }
            break;
                
            case ContraHarmonic:
                if (fastBitmap.isGrayscale()) {
                    double sumGray;
                    double sumGrayOne, sumGrayTwo;
                    for (int x = 0; x < height; x++) {
                        for (int y = 0; y < width; y++) {
                            sumGrayOne = sumGrayTwo = 0;
                            for (int i = 0; i < lines; i++) {
                                Xline = x + (i-radius);
                                for (int j = 0; j < lines; j++) {
                                    Yline = y + (j-radius);
                                    if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                        sumGrayOne += Math.pow((double)copy.getGray(Xline, Yline),orderFilter+1);
                                        sumGrayTwo += Math.pow((double)copy.getGray(Xline, Yline),orderFilter);
                                    }
                                }
                            }
                            sumGray = sumGrayOne / sumGrayTwo;
                            fastBitmap.setGray(x, y, (int)sumGray);
                        }
                    }
                }
                else if(fastBitmap.isRGB()){
                    double sumR, sumG, sumB;
                    double sumRone, sumGone, sumBone;
                    double sumRtwo, sumGtwo, sumBtwo;
                    for (int x = 0; x < height; x++) {
                        for (int y = 0; y < width; y++) {
                            sumRone = sumGone = sumBone = 0;
                            sumRtwo = sumGtwo = sumBtwo = 0;
                            for (int i = 0; i < lines; i++) {
                                Xline = x + (i-radius);
                                for (int j = 0; j < lines; j++) {
                                    Yline = y + (j-radius);
                                    if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                        sumRone += Math.pow((double)copy.getRed(Xline, Yline),orderFilter+1);
                                        sumGone += Math.pow((double)copy.getGreen(Xline, Yline),orderFilter+1);
                                        sumBone += Math.pow((double)copy.getBlue(Xline, Yline),orderFilter+1);
                                        
                                        sumRtwo += Math.pow((double)copy.getRed(Xline, Yline),orderFilter);
                                        sumGtwo += Math.pow((double)copy.getGreen(Xline, Yline),orderFilter);
                                        sumBtwo += Math.pow((double)copy.getBlue(Xline, Yline),orderFilter);
                                    }
                                }
                            }
                            sumR = sumRone / sumRtwo;
                            sumG = sumGone / sumGtwo;
                            sumB = sumBone / sumBtwo;
                            fastBitmap.setRGB(x, y, (int)sumR, (int)sumG, (int)sumB);
                        }
                    }
                }
            break;
                
            case Geometry:
                if (fastBitmap.isGrayscale()) {
                    double sumGray;
                    for (int x = 0; x < height; x++) {
                        for (int y = 0; y < width; y++) {
                            c = 0;
                            sumGray = 1;
                            for (int i = 0; i < lines; i++) {
                                Xline = x + (i-radius);
                                for (int j = 0; j < lines; j++) {
                                    Yline = y + (j-radius);
                                    if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                        sumGray *= (double)copy.getGray(Xline, Yline);
                                        c++;
                                    }
                                }
                            }
                            sumGray = Math.pow(sumGray , (double)1/c);
                            fastBitmap.setGray(x, y, (int)sumGray);
                        }
                    }
                }
                else if(fastBitmap.isRGB()){
                    double sumR;
                    double sumG;
                    double sumB;

                    for (int x = 0; x < height; x++) {
                        for (int y = 0; y < width; y++) {
                            c = 0;
                            sumR = sumG = sumB = 1;
                            for (int i = 0; i < lines; i++) {
                                Xline = x + (i-radius);
                                for (int j = 0; j < lines; j++) {
                                    Yline = y + (j-radius);
                                    if ((Xline >= 0) && (Xline < height) && (Yline >=0) && (Yline < width)) {
                                        sumR *= (double)copy.getRed(Xline, Yline);
                                        sumG *= (double)copy.getGreen(Xline, Yline);
                                        sumB *= (double)copy.getBlue(Xline, Yline);
                                        c++;
                                    }
                                }
                            }
                            sumR = Math.pow(sumR , (double)1/c);
                            sumG = Math.pow(sumG , (double)1/c);
                            sumB = Math.pow(sumB , (double)1/c);
                            fastBitmap.setRGB(x, y, (int)sumR, (int)sumG, (int)sumB);
                        }
                    }
                }
            break;
            
        }
    }
    
    private int CalcLines(int radius){
        return radius * 2 + 1;
    }
}