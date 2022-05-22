package renderMake.Point;

import renderMake.display;

import java.awt.*;

import static java.lang.Math.sqrt;

public class pointConverter {
    public static double scale = 1;
    public static Point convertPoint(CPoint point3D){
        double x3d = point3D.y * scale;
        double y3d = point3D.z * scale;
        double depth = point3D.x * scale;

        ///x=y, y=z when converting from 2d to 3d (if int x2d = point3d.y and y2d = point3d.z)
        ///center of screen(origin) should be 0, 0, divide width/height by 2


        double[] newVal = scale(x3d, y3d, depth);

        int x2d = (int) (display.WIDTH/2 + newVal[0]);
        int y2d = (int) (display.LENGTH/2 - newVal[1]);

        Point point2d = new Point(x2d, y2d);
        return point2d;
    }
    public static double[] scale(double x3d, double y3d, double depth){
        //distance from origin, AKA the pythagorean theorem
        double distance =  sqrt(x3d*x3d + y3d*y3d);
        //atan is reverse operation of tangent > atan(tan(30)) would produce 30
        double theta = Math.atan2(y3d, x3d);

        //cam is at 0,0,-15 (z being depth)
        double depth2=15-depth;


        double localScale = 1200/(depth2+1200);
        distance *= localScale;
        double[] newVal = new double[2];
        newVal[0] = distance * Math.cos(theta);
        newVal[1] = distance * Math.sin(theta);
        return(newVal);
    }
}
