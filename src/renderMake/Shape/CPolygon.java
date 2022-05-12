package renderMake.Shape;

import renderMake.Point.CPoint;
import renderMake.Point.pointConverter;

import java.awt.*;

public class CPolygon {
    Color color = Color.BLACK;
    private CPoint[] points;
    /* "..." notation says that the parameter is a CPoint array of any size
    it is named the same as the existing array by coincidence: they are not the same thing. parameter "points" is its own array
    thus, inside of the constructor, the existing array "points" should be referred to as "this.points"*/
    public CPolygon(Color color, CPoint...points) {
        this.color = color;
        //assign size of array "points" to size of array submitted as parameter
        this.points = new CPoint[points.length];
        for (int i = 0; i < points.length; i++){
            CPoint c = points[i];
            //create a new CPoint because you don't want to modify the existing value in parameter "points"
            this.points[i] = new CPoint(c.x, c.y, c.z);
        }
    }
    public CPolygon(CPoint...points) {
        this.color = Color.BLACK;
        //assign size of array "points" to size of array submitted as parameter
        this.points = new CPoint[points.length];
        for (int i = 0; i < points.length; i++){
            CPoint c = points[i];
            //create a new CPoint because you don't want to modify the existing value in parameter "points"
            this.points[i] = new CPoint(c.x, c.y, c.z);
        }
    }
    //requires graphics object to draw
    public void render(Graphics g){
        Polygon poly = new Polygon();
        for(int i = 0; i < points.length; i++){
            Point p1 = pointConverter.convertPoint(points[i]);
            poly.addPoint(p1.x, p1.y);
        }
        g.setColor(this.color);
        g.fillPolygon(poly);
    }
    public void frameRender(Graphics g){
        g.setColor(this.color);
        Polygon poly = new Polygon();
        for(int i = 0; i < points.length; i++){
            Point p1 = pointConverter.convertPoint(points[i]);
            for(int z = 0; z < points.length; z++){
                Point p2 = pointConverter.convertPoint(points[z]);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
    public void setColor(Color color){
        this.color = color;
    }
}
