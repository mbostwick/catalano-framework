// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2013
// diego.catalano at live.com
//
// Copyright © Arlington, 2013
// Copyright © Saulo, 2013
// scsm at ecmp.poli.br
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

package Catalano.Imaging.Tools;

/**
 * Image moments.
 * Compute RST invariants.
 */
public final class HuMoments {
    
    private HuMoments(){};
        
    public static double getRawMoment(int p, int q, double[][] image) {
        double m = 0;
        for (int i = 0, k = image.length; i < k; i++) {
                for (int j = 0, l = image[i].length; j < l; j++) {
                    m += Math.pow(i, p) * Math.pow(j, q) * image[i][j];
                }
        }
        return m;
    }

    public static double getCentralMoment(int p, int q, double[][] img) {
            double mc = 0;
            double m00 = HuMoments.getRawMoment(0, 0, img);
            double m10 = HuMoments.getRawMoment(1, 0, img);
            double m01 = HuMoments.getRawMoment(0, 1, img);
            double x0 = m10 / m00;
            double y0 = m01 / m00;
            for (int i = 0, k = img.length; i < k; i++) {
                    for (int j = 0, l = img[i].length; j < l; j++) {
                            mc += Math.pow((i - x0), p) * Math.pow((j - y0), q) * img[i][j];
                    }
            }
            return mc;
    }

    public static double getCovarianceXY(int p, int q, double[][] image) {
            double mc00 = HuMoments.getCentralMoment(0, 0, image);
            double mc11 = HuMoments.getCentralMoment(1, 1, image);
            return mc11 / mc00;
    }

    public static double getVarianceX(int p, int q, double[][] image) {
            double mc00 = HuMoments.getCentralMoment(0, 0, image);
            double mc20 = HuMoments.getCentralMoment(2, 0, image);
            return mc20 / mc00;
    }

    public static double getVarianceY(int p, int q, double[][] image) {
            double mc00 = HuMoments.getCentralMoment(0, 0, image);
            double mc02 = HuMoments.getCentralMoment(0, 2, image);
            return mc02 / mc00;
    }
    public static double getNormalizedCentralMoment(int p, int q, double[][] image) {
            double gama = ((p + q) / 2) + 1;
            double mpq = HuMoments.getCentralMoment(p, q, image);
            double m00gama = Math.pow(HuMoments.getCentralMoment(0, 0, image), gama);
            return mpq / m00gama;
    }

    public static double getHuMoment (double[][] image,int n) {
            double result = 0.0;

            double
            n20 = HuMoments.getNormalizedCentralMoment(2, 0, image),
            n02 = HuMoments.getNormalizedCentralMoment(0, 2, image),
            n30 = HuMoments.getNormalizedCentralMoment(3, 0, image),
            n12 = HuMoments.getNormalizedCentralMoment(1, 2, image),
            n21 = HuMoments.getNormalizedCentralMoment(2, 1, image),
            n03 = HuMoments.getNormalizedCentralMoment(0, 3, image),
            n11 = HuMoments.getNormalizedCentralMoment(1, 1, image);

            switch (n) {
            case 1:
                    result = n20 + n02;
                    break;
            case 2:
                    result = Math.pow((n20 - 02), 2) + Math.pow(2 * n11, 2);
                    break;
            case 3:
                    result = Math.pow(n30 - (3 * (n12)), 2)
                                    + Math.pow((3 * n21 - n03), 2);
                    break;
            case 4:
                    result = Math.pow((n30 + n12), 2) + Math.pow((n12 + n03), 2);
                    break;
            case 5:
                    result = (n30 - 3 * n12) * (n30 + n12)
                                    * (Math.pow((n30 + n12), 2) - 3 * Math.pow((n21 + n03), 2))
                                    + (3 * n21 - n03) * (n21 + n03)
                                    * (3 * Math.pow((n30 + n12), 2) - Math.pow((n21 + n03), 2));
                    break;
            case 6:
                    result = (n20 - n02)
                                    * (Math.pow((n30 + n12), 2) - Math.pow((n21 + n03), 2))
                                    + 4 * n11 * (n30 + n12) * (n21 + n03);
                    break;
            case 7:
                    result = (3 * n21 - n03) * (n30 + n12)
                                    * (Math.pow((n30 + n12), 2) - 3 * Math.pow((n21 + n03), 2))
                                    + (n30 - 3 * n12) * (n21 + n03)
                                    * (3 * Math.pow((n30 + n12), 2) - Math.pow((n21 + n03), 2));
                    break;

            default:
                    throw new IllegalArgumentException("Invalid number for Hu moment.");
            }
            return result;
    }
}