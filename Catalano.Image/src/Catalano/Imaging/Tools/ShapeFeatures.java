/*
 * Shape Features v0.2
 * Total: 15
 * 
 * Area: Total of pixels white.
 * Area Equivalent Diameter: sqrt((4/Pi)*Area)
 * Aspect Ratio:                                                 IMPLEMENTAR
 * Circularity: 4*Pi*Area/Perimeter^2
 * Compactness: AreaEquivalentDiameter / feretDiameter;
 * Euler Number:                                                 IMPLEMENTAR
 * Feret's Diameter: Maximum diameter.
 * Feret's Points: Points of Feret's Diameter
 * Irregularity: 1/ThinnessRatio
 * Perimeter: Area of contour                                    IMPLEMENTAR
 * Perimeter Equivalent Diameter: area / Pi
 * Perimeter Points: points of perimeter
 * Roudness: 4*Area/(Pi*Feret^2)
 * Shape: Perimeter^2 / area
 * ThinnessRatio: 4*Pi*(area/perimeter)
 */
package Catalano.Imaging.Tools;

import Catalano.Core.IntPoint;
import Catalano.Imaging.FastBitmap;
import Catalano.Math.Distance;
import java.util.ArrayList;

/**
 *
 * @author Diego Catalano
 */
public final class ShapeFeatures {
    
    private ShapeFeatures(){};
    
    public static int Area(FastBitmap fastBitmap){
        int area = 0;
        for (int x = 0; x < fastBitmap.getHeight(); x++) {
            for (int y = 0; y < fastBitmap.getWidth(); y++) {
                if (fastBitmap.getGray(x, y) == 255) {
                    area++;
                }
            }
        }
        return area;
    }
    
    public static double AreaEquivalentDiameter(int area){
        // Formula: sqrt((4/Pi)*Area)
        double v = 1.2732395447351626861510701069801;
        return Math.sqrt(v*area);
    }
    
    //Implementar
    public double AspectRatio(){
        return 0;
    }
    
    public double Circularity(int area, int perimeter){
        // Circularity = 4*Pi*Area/Perimeter^2
        double v = 12.566370614359172953850573533118;
        return (v*area) / (perimeter*perimeter);
    }
    
    public double Compactness(int area, double feretDiameter){
        return AreaEquivalentDiameter(area) / feretDiameter;
    }
    
    //Implementar
    public static int EulerNumber(){
        return 0;
    }
    
    public static double FeretDiameter(ArrayList<IntPoint> contour){
        double maxDiameter = 0;
        for (IntPoint p : contour) {
            for (IntPoint pp : contour) {
                double d = Distance.Euclidean(p.x, p.y, pp.x, pp.y);
                if (d > maxDiameter) {
                    maxDiameter = d;
                }
            }
        }
        
        return maxDiameter;
    }
    
    public static ArrayList<IntPoint> FeretPoints(ArrayList<IntPoint> contour){
        ArrayList<IntPoint> lst = new ArrayList<IntPoint>();
        
        IntPoint p1 = new IntPoint();
        IntPoint p2 = new IntPoint();
        
        double maxDiameter = 0;
        for (IntPoint p : contour) {
            for (IntPoint pp : contour) {
                double d = Distance.Euclidean(p.x, p.y, pp.x, pp.y);
                if (d > maxDiameter) {
                    maxDiameter = d;
                    p1 = p;
                    p2 = pp;
                }
            }
        }
        
        lst.add(p1);
        lst.add(p2);
        
        return lst;
    }
    
    public static double Irregularity(double thinnessRatio){
        return 1/thinnessRatio;
    }
    
    public static int Perimeter(){
        return 0;
    }
    
    public static double PerimeterEquivalentDiameter(int area){
        return area / Math.PI;
    }
    
    public static int PerimeterPoints(){
        return 0;
    }
    
    public static double Roundness(int area, double feretDiameter){
        return (4 * area) / (Math.PI * (feretDiameter * feretDiameter));
    }
    
    public static double Shape(int area, int perimeter){
        return (perimeter * perimeter) / area;
    }
    
    
    public static double ThinnessRatio(int area, int perimeter){
        // Original: 4 * Math.Pi * (area/perimeter)
        double v = 12.566370614359172953850573533118;
        return v * (area/perimeter);
    }
}