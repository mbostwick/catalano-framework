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

package Catalano.Imaging.Tools;

/**
 * Convert between different color spaces supported.
 * RGB -> YIQ -> RGB
 * RGB -> YCbCr -> RGB
 * RGB -> YUV -> RGB
 * RGB -> RGChromaticity
 * RGB -> HSV -> RGB
 * RGB -> YCC -> RGB
 * RGB -> YCoCg -> RGB
 * RGB -> XYZ -> RGB
 * RGB -> HunterLAB -> RGB
 * XYZ -> HunterLAB -> XYZ
 * @author Diego Catalano
 */
public class ColorConverter {
    
    public static enum YCbCrColorSpace {ITU_BT_601,ITU_BT_709_HDTV};
    
    //2o (CIE 1931)
    // X2, Y2, Z2
    public static float[] CIE2_A = {109.850f, 100f, 35.585f}; //Incandescent
    public static float[] CIE2_C = {98.074f, 100f, 118.232f};
    public static float[] CIE2_D50 = {96.422f, 100f, 82.521f};
    public static float[] CIE2_D55 = {95.682f, 100f, 92.149f};
    public static float[] CIE2_D65 = {95.047f, 100f, 108.883f}; //Daylight
    public static float[] CIE2_D75 = {94.972f, 100f, 122.638f};
    public static float[] CIE2_F2 = {99.187f, 100f, 67.395f}; //Fluorescent
    public static float[] CIE2_F7 = {95.044f, 100f, 108.755f};
    public static float[] CIE2_F11 = {100.966f, 100f, 64.370f};
    
    //10o (CIE 1964)
    // X2, Y2, Z2
    public static float[] CIE10_A = {111.144f, 100f, 35.200f}; //Incandescent
    public static float[] CIE10_C = {97.285f, 100f, 116.145f};
    public static float[] CIE10_D50 = {96.720f, 100f, 81.427f};
    public static float[] CIE10_D55 = {95.799f, 100f, 90.926f};
    public static float[] CIE10_D65 = {94.811f, 100f, 107.304f}; //Daylight
    public static float[] CIE10_D75 = {94.416f, 100f, 120.641f};
    public static float[] CIE10_F2 = {103.280f, 100f, 69.026f}; //Fluorescent
    public static float[] CIE10_F7 = {95.792f, 100f, 107.687f};
    public static float[] CIE10_F11 = {103.866f, 100f, 65.627f};
    
    /**
     * RGB -> YUV.
     * Y in the range [0..1].
     * U in the range [-0.5..0.5].
     * V in the range [-0.5..0.5].
     * @param red Values in the range [0..255].
     * @param green Values in the range [0..255].
     * @param blue Values in the range [0..255].
     * @return YUV color space.
     */
    public static float[] RGBtoYUV(int red, int green, int blue){
        
        float r = (float)red / 255;
        float g = (float)green / 255;
        float b = (float)blue / 255;
        
        float[] yuv = new float[3];
        float y,u,v;
        
        y = (float)(0.299 * r + 0.587 * g + 0.114 * b);
        u = (float)(-0.14713 * r - 0.28886 * g + 0.436 * b);
        v = (float)(0.615 * r - 0.51499 * g - 0.10001 * b);
        
        yuv[0] = y;
        yuv[1] = u;
        yuv[2] = v;
        
        return yuv;
    }
    
    /**
     * YUV -> RGB.
     * @param y Luma. In the range [0..1].
     * @param u Chrominance. In the range [-0.5..0.5].
     * @param v Chrominance. In the range [-0.5..0.5].
     * @return RGB color space.
     */
    public static int[] YUVtoRGB(float y, float u, float v){
        int[] rgb = new int[3];
        float r,g,b;
        
        r = (float)((y + 0.000 * u + 1.140 * v) * 255);
        g = (float)((y - 0.396 * u - 0.581 * v) * 255);
        b = (float)((y + 2.029 * u + 0.000 * v) * 255);
        
        rgb[0] = (int)r;
        rgb[1] = (int)g;
        rgb[2] = (int)b;
        
        return rgb;
    }
    
    /**
     * RGB -> YIQ.
     * @param red Values in the range [0..255].
     * @param green Values in the range [0..255].
     * @param blue Values in the range [0..255].
     * @return YIQ color space.
     */
    public static float[] RGBtoYIQ(int red, int green, int blue){
        float[] yiq = new float[3];
        float y,i,q;
        
        float r = (float)red / 255;
        float g = (float)green / 255;
        float b = (float)blue / 255;
        
        y = (float)(0.299 * r + 0.587 * g + 0.114 * b);
        i = (float)(0.596 * r - 0.275 * g - 0.322 * b);
        q = (float)(0.212 * r - 0.523 * g + 0.311 * b);
        
        yiq[0] = y;
        yiq[1] = i;
        yiq[2] = q;
        
        return yiq;
    }
    
    /**
     * YIQ -> RGB.
     * @param y Luma. Values in the range [0..1].
     * @param i In-phase. Values in the range [-0.5..0.5].
     * @param q Quadrature. Values in the range [-0.5..0.5].
     * @return RGB color space.
     */
    public static int[] YIQtoRGB(double y, double i, double q){
        int[] rgb = new int[3];
        int r,g,b;
        
        r = (int)((y + 0.956 * i + 0.621 * q) * 255);
        g = (int)((y - 0.272 * i - 0.647 * q) * 255);
        b = (int)((y - 1.105 * i + 1.702 * q) * 255);
        
        r = Math.max(0,Math.min(255,r));
        g = Math.max(0,Math.min(255,g));
        b = Math.max(0,Math.min(255,b));
        
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;
        
        return rgb;
    }
    
    public static float[] RGBtoYCbCr(int red, int green, int blue, YCbCrColorSpace colorSpace){
        
        float r = (float)red / 255;
        float g = (float)green / 255;
        float b = (float)blue / 255;
        
        float[] YCbCr = new float[3];
        float y,cb,cr;
        
        if (colorSpace == YCbCrColorSpace.ITU_BT_601) {
            y = (float)(0.299 * r + 0.587 * g + 0.114 * b);
            cb = (float)(-0.169 * r - 0.331 * g + 0.500 * b);
            cr = (float)(0.500 * r - 0.419 * g - 0.081 * b);
        }
        else{
            y = (float)(0.2215 * r + 0.7154 * g + 0.0721 * b);
            cb = (float)(-0.1145 * r - 0.3855 * g + 0.5000 * b);
            cr = (float)(0.5016 * r - 0.4556 * g - 0.0459 * b);
        }
        
        YCbCr[0] = (float)y;
        YCbCr[1] = (float)cb;
        YCbCr[2] = (float)cr;
        
        return YCbCr;
    }
    
    public static int[] YCbCrtoRGB(float y, float cb, float cr, YCbCrColorSpace colorSpace){
        int[] rgb = new int[3];
        float r,g,b;
        
        if (colorSpace == YCbCrColorSpace.ITU_BT_601) {
            r = (float)(y + 0.000 * cb + 1.403 * cr) * 255;
            g = (float)(y - 0.344 * cb - 0.714 * cr) * 255;
            b = (float)(y + 1.773 * cb + 0.000 * cr) * 255;
        }
        else{
            r = (float)(y + 0.000 * cb + 1.5701 * cr) * 255;
            g = (float)(y - 0.1870 * cb - 0.4664 * cr) * 255;
            b = (float)(y + 1.8556 * cb + 0.000 * cr) * 255;
        }
        
        rgb[0] = (int)r;
        rgb[1] = (int)g;
        rgb[2] = (int)b;
        
        return rgb;
    }
    
    /**
     * Rg-Chromaticity space is already known to remove ambiguities due to illumination or surface pose.
     * @see Neural Information Processing - Chi Sing Leung. p. 668
     * @param red Red coefficient.
     * @param green Green coefficient.
     * @param blue Blue coefficient.
     * @return Normalized RGChromaticity. Range[0..1].
     */
    public static double[] RGChromaticity(int red, int green, int blue){
        double[] color = new double[5];
        
        double sum = red + green + blue;
        
        //red
        color[0] = red / sum;
        
        //green
        color[1] = green / sum;
        
        //blue
        color[2] = 1 - color[0] - color[1];
        
        double rS = color[0] - 0.333;
        double gS = color[1] - 0.333;
        
        //saturation
        color[3] = Math.sqrt(rS * rS + gS * gS);
        
        //hue
        color[4] = Math.atan(rS / gS);
        
        return color;
    }
    
    /**
     * RGB -> HSV.
     * Adds (hue + 360) % 360 for represent hue in the range [0..359].
     * @param red Red coefficient. Values in the range [0..255].
     * @param green Green coefficient. Values in the range [0..255].
     * @param blue Blue coefficient. Values in the range [0..255].
     * @return HSV color space.
     */
    public static float[] RGBtoHSV(int red, int green, int blue){
        float[] hsv = new float[3];
        float r = red / 255f;
        float g = green / 255f;
        float b = blue / 255f;
        
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        float delta = max - min;
        
        // Hue
        if (max == min){
            hsv[0] = 0;
        }
        else if (max == r){
            hsv[0] = ((g - b) / delta) * 60f;
        }
        else if (max == g){
            hsv[0] = ((b - r) / delta + 2f) * 60f;
        }
        else if (max == b){
            hsv[0] = ((r - g) / delta + 4f) * 60f;
        }
        
        // Saturation
        if (delta == 0)
            hsv[1] = 0;
        else
            hsv[1] = delta / max;
        
        //Value
        hsv[2] = max;
        
        return hsv;
    }
    
    /**
     * HSV -> RGB.
     * @param hue Hue.
     * @param saturation Saturation. In the range[0..1].
     * @param value Value. In the range[0..1].
     * @return RGB color space. In the range[0..255].
     */
    public static int[] HSVtoRGB(float hue, float saturation, float value){
        int[] rgb = new int[3];
        
        float hi = (float)Math.floor(hue / 60.0) % 6;
        float f =  (float)((hue / 60.0) - Math.floor(hue / 60.0));
        float p = (float)(value * (1.0 - saturation));
        float q = (float)(value * (1.0 - (f * saturation)));
        float t = (float)(value * (1.0 - ((1.0 - f) * saturation)));
        
        if (hi == 0){
            rgb[0] = (int)(value * 255);
            rgb[1] = (int)(t * 255);
            rgb[2] = (int)(p * 255);
        }
        else if (hi == 1){
            rgb[0] = (int)(q * 255);
            rgb[1] = (int)(value * 255);
            rgb[2] = (int)(p * 255);
        }
        else if (hi == 2){
            rgb[0] = (int)(p * 255);
            rgb[1] = (int)(value * 255);
            rgb[2] = (int)(t * 255);
        }
        else if (hi == 3){
            rgb[0] = (int)(p * 255);
            rgb[1] = (int)(value * 255);
            rgb[2] = (int)(q * 255);
        }
        else if (hi == 4){
            rgb[0] = (int)(t * 255);
            rgb[1] = (int)(value * 255);
            rgb[2] = (int)(p * 255);
        }
        else if (hi == 5){
            rgb[0] = (int)(value * 255);
            rgb[1] = (int)(p * 255);
            rgb[2] = (int)(q * 255);
        }
        
        return rgb;
    }
    
    public static float[] RGBtoYCC(int red, int green, int blue){
        float[] ycc = new float[3];
        
        float r = red / 255f;
        float g = green / 255f;
        float b = blue / 255f;
        
        float y = 0.213f * r + 0.419f * g + 0.081f * b;
        float c1 = -0.131f * r - 0.256f * g + 0.387f * b + 0.612f;
        float c2 = 0.373f * r - 0.312f * r - 0.061f * b + 0.537f;
        
        ycc[0] = y;
        ycc[1] = c1;
        ycc[2] = c2;
        
        return ycc;
    }
    
    public static int[] YCCtoRGB(float y, float c1, float c2){
        int[] rgb = new int[3];
        
        float r = 0.981f * y + 1.315f * (c2 - 0.537f);
        float g = 0.981f * y - 0.311f * (c1 - 0.612f)- 0.669f * (c2 - 0.537f);
        float b = 0.981f * y + 1.601f * (c1 - 0.612f);
        
        rgb[0] = (int)(r * 255f);
        rgb[1] = (int)(g * 255f);
        rgb[2] = (int)(b * 255f);
        
        return rgb;
    }
    
    public static float[] RGBtoYCoCg(int red, int green, int blue){
        float[] yCoCg = new float[3];
        
        float r = red / 255f;
        float g = green / 255f;
        float b = blue / 255f;
        
        float y = r / 4f + g / 2f + b / 4f;
        float co = r / 2f - b / 2f;
        float cg = -r / 4f + g / 2f - b / 4f;
        
        yCoCg[0] = y;
        yCoCg[1] = co;
        yCoCg[2] = cg;
        
        return yCoCg;
    }
    
    public static int[] YCoCgtoRGB(float y, float co, float cg){
        int[] rgb = new int[3];
        
        float r = y + co - cg;
        float g = y + cg;
        float b = y - co - cg;
        
        rgb[0] = (int)(r * 255f);
        rgb[1] = (int)(g * 255f);
        rgb[2] = (int)(b * 255f);
        
        return rgb;
    }
    
    public static float[] RGBtoXYZ(int red, int green, int blue){
        float[] xyz = new float[3];
        
        float r = red / 255f;
        float g = green / 255f;
        float b = blue / 255f;
        
        float x = 0.412453f * r + 0.35758f * g + 0.180423f * b;
        float y = 0.212671f * r + 0.71516f * g + 0.072169f * b;
        float z = 0.019334f * r + 0.119193f * g + 0.950227f * b;
        
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
        
        return xyz;
    }
    
    public static int[] XYZtoRGB(float x, float y, float z){
        int[] rgb = new int[3];
        
        float r = 3.240479f * x - 1.53715f * y - 0.498535f * z;
        float g = -0.969256f * x + 1.875991f * y + 0.041556f * z;
        float b = 0.055648f * x - 0.204043f * y + 1.057311f * z;
        
        rgb[0] = (int)(r * 255);
        rgb[1] = (int)(g * 255);
        rgb[2] = (int)(b * 255);
        
        return rgb;
    }
    
    public static float[] XYZtoHunterLAB(float x, float y, float z){
        float[] hunter = new float[3];
        
        
        float sqrt = (float)Math.sqrt(y);
        
        float l = 10 * sqrt;
        float a = 17.5f * (((1.02f * x) - y) / sqrt);
        float b = 7f * ((y - (0.847f * z)) / sqrt);
        
        hunter[0] = l;
        hunter[1] = a;
        hunter[2] = b;
        
        return hunter;
    }
    
    public static float[] HunterLABtoXYZ(float l, float a, float b){
        float[] xyz = new float[3];
        
        
        float tempY = l / 10f;
        float tempX = a / 17.5f * l / 10f;
        float tempZ = b / 7f * l / 10f;
        
        float y = tempY * tempY;
        float x = (tempX + y) / 1.02f;
        float z = -(tempZ - y) / 0.847f;
        
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
        
        return xyz;
    }
    
    public static float[] RGBtoHunterLAB(int red, int green, int blue){
        float[] xyz = RGBtoXYZ(red, green, blue);
        return XYZtoHunterLAB(xyz[0], xyz[1], xyz[2]);
    }
    
    public static int[] HunterLABtoRGB(float l, float a, float b){
        float[] xyz = HunterLABtoXYZ(l, a, b);
        return XYZtoRGB(xyz[0], xyz[1], xyz[2]);
    }
}
