// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2013
// diego.catalano at live.com
//
// Copyright © Max Bügler, 2010-2013
// max at maxbuegler.eu
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
import Catalano.Math.Functions.Gabor;
import Catalano.Math.Functions.Gabor.Config;

/**
 * Gabor Filter.
 * <para> In image processing, a Gabor filter, named after Dennis Gabor, is a linear filter used for edge detection.
 * Frequency and orientation representations of Gabor filters are similar to those of the human visual system,
 * and they have been found to be particularly appropriate for texture representation and discrimination.
 * In the spatial domain, a 2D Gabor filter is a Gaussian kernel function modulated by a sinusoidal plane wave. </para>
 * 
 * @see http://en.wikipedia.org/wiki/Gabor_filter
 * @author Diego Catalano
 */
public class GaborFilter implements IBaseInPlace{
    
    // Size of kernel
    private int size = 3;
    
    // Wavelength
    private double lambda = 4.0;
    
    // Orientation
    private double theta = 0.6;
    
    // Phase offset
    private double phi = 1.0;
    
    // Gaussian variance
    private double sigma = 2.0;
    
    // Aspect ratio
    private double gamma = 0.3;
    private Gabor.Config config = Gabor.Config.Imaginary;
    private boolean signed = false;

    /**
     * Get size of Gabor kernel.
     * @return Size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Set size of Gabor kernel.
     * @param size Size.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Get Lambda (Wavelength).
     * @return Lambda value.
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * Set Lambda (Wavelength).
     * @param lambda Lambda value.
     */
    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    /**
     * Get Theta (Orientation).
     * @return Theta value.
     */
    public double getTheta() {
        return theta;
    }

    /**
     * Set Theta (Orientation).
     * @param theta Theta value.
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }

    /**
     * Get Phi (Phase offset).
     * @return Phi value.
     */
    public double getPhi() {
        return phi;
    }

    /**
     * Set Phi (Phase offset).
     * @param phi 
     */
    public void setPhi(double phi) {
        this.phi = phi;
    }

    /**
     * Get Sigma (Gaussian variance).
     * @return Sigma value.
     */
    public double getSigma() {
        return sigma;
    }

    /**
     * Set Sigma (Gaussian variance).
     * @param sigma Sigma value.
     */
    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    /**
     * Get Gamma (Aspect ratio).
     * @return gamma.
     */
    public double getGamma() {
        return gamma;
    }

    /**
     * Set Gamma (Aspect ratio).
     * @param gamma Gamma.
     */
    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    /**
     * Get Gabor function configuration.
     * @return Configuration.
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Set Gabor function configuration.
     * @param config Configuration.
     */
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * Verify if the image is signed.
     * @return True if the image is signed, otherwise false.
     */
    public boolean isSigned() {
        return signed;
    }

    /**
     * Set signed image.
     * If true, image will be converted to RGB.
     * @param signed True for signed image, false for grayscale image.
     */
    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    /**
     * Initializes a new instance of the GaborFilterFinal class.
     */
    public GaborFilter() {}

    /**
     * Initializes a new instance of the GaborFilterFinal class.
     * @param lambda Lambda.
     */
    public GaborFilter(double lambda) {
        this.lambda = lambda;
    }

    /**
     * Initializes a new instance of the GaborFilterFinal class.
     * @param lambda Lambda.
     * @param theta Theta.
     */
    public GaborFilter(double lambda, double theta) {
        this.lambda = lambda;
        this.theta = theta;
    }

    /**
     * Initializes a new instance of the GaborFilterFinal class.
     * @param lambda Lambda.
     * @param theta Theta.
     * @param phi Phi.
     */
    public GaborFilter(double lambda, double theta, double phi) {
        this.lambda = lambda;
        this.theta = theta;
        this.phi = phi;
    }

    /**
     * Initializes a new instance of the GaborFilterFinal class.
     * @param lambda Lambda.
     * @param theta Theta.
     * @param phi Phi.
     * @param sigma Sigma.
     */
    public GaborFilter(double lambda, double theta, double phi, double sigma) {
        this.lambda = lambda;
        this.theta = theta;
        this.phi = phi;
        this.sigma = sigma;
    }

    /**
     * Initializes a new instance of the GaborFilterFinal class.
     * @param lambda Lambba.
     * @param theta Theta.
     * @param phi Phi.
     * @param sigma Sigma.
     * @param gamma Gamma.
     */
    public GaborFilter(double lambda, double theta, double phi, double sigma, double gamma) {
        this.lambda = lambda;
        this.theta = theta;
        this.phi = phi;
        this.sigma = sigma;
        this.gamma = gamma;
    }
    
    /**
     * Initializes a new instance of the GaborFilterFinal class.
     * @param lambda Lambba.
     * @param theta Theta.
     * @param phi Phi.
     * @param sigma Sigma.
     * @param gamma Gamma.
     */
    public GaborFilter(double lambda, double theta, double phi, double sigma, double gamma, Gabor.Config config) {
        this.lambda = lambda;
        this.theta = theta;
        this.phi = phi;
        this.sigma = sigma;
        this.gamma = gamma;
        this.config = config;
    }

    @Override
    public void applyInPlace(FastBitmap fastBitmap) {
        
        double[][] gaborKernel;
        int width = fastBitmap.getWidth();
        int height = fastBitmap.getHeight();
        
        if (fastBitmap.isGrayscale()){
            
            gaborKernel = Gabor.Kernel2D(size, lambda, theta, phi, sigma, gamma, config);
            int[][] gaborResponse = applyGabor(fastBitmap, gaborKernel);
            int maxG = Integer.MIN_VALUE;
            int minG = Integer.MAX_VALUE;
            
            if (isSigned()){
                fastBitmap.toRGB();

                // Gets max and min gray value.
                for (int i = 0; i < gaborResponse.length; i++) {
                    for (int j = 0; j < gaborResponse[0].length; j++) {
                        int gray = gaborResponse[i][j];
                        if (gray > maxG && gray > 0) maxG = gray;
                        if (gray < minG && gray < 0) minG = gray;
                    }
                }

                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        
                        int g = (int)Math.round((255*(double)(gaborResponse[i][j] / (double)maxG)));
                        if (gaborResponse[i][j] < 0){
                            g = (int)Math.round((255*(double)(gaborResponse[i][j] / (double)minG)));
                        }
                        if (gaborResponse[i][j] > 0)
                            fastBitmap.setRGB(i, j, g, 0, 0);
                        else
                            fastBitmap.setRGB(i, j, 0, 0, g);
                    }
                }
            }
            else{

                // Gets max and min gray value.
                for (int i = 0; i < gaborResponse.length; i++) {
                    for (int j = 0; j < gaborResponse[0].length; j++) {
                        int gray = gaborResponse[i][j];
                        if (gray > maxG) maxG = gray;
                        if (gray < minG) minG = gray;
                    }
                }

                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        int g = (int)Math.round((255*(double)(gaborResponse[i][j] - minG))/(maxG - minG));
                        fastBitmap.setGray(i, j, g);
                    }
                }
            }
        }
        else{
            try {
                throw new IllegalArgumentException("Gabor filter only works with grayscale images.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Apply Gabor 
     * @param fastBitmap Image to be processed.
     * @param gaborKernel Gabor kernel.
     * @return Gabor response.
     */
    private int[][] applyGabor(FastBitmap fastBitmap, double[][] gaborKernel){
        
        int xmax=(int)Math.floor(gaborKernel.length / 2.0);
        int ymax=(int)Math.floor(gaborKernel[0].length / 2.0);
        int[][] gaborResponse = new int[fastBitmap.getHeight()][fastBitmap.getWidth()];
        
        for (int x=0; x < fastBitmap.getHeight();x++){
            for (int y=0; y < fastBitmap.getWidth();y++){
                double sum = 0;
                for (int xf = -xmax; xf <= xmax; xf++){
                    for (int yf = -ymax; yf <= ymax; yf++){
                        if (x-xf >= 0 && x-xf < fastBitmap.getHeight() && y-yf >= 0 && y-yf < fastBitmap.getWidth()){
                            int value = fastBitmap.getGray(x - xf, y - yf);
                            sum += gaborKernel[xf + xmax][yf + ymax] * value;
                        }
                    }
                }
                gaborResponse[x][y] = (int)Math.round(sum);
            }
        }
        return gaborResponse;
    }
}