package renderMake.Shape;

import java.awt.*;

public class Tetrahedron {
    private CPolygon[] polygons;
    private Color color;
    public Tetrahedron(Color color, CPolygon... polygons){
        this.color = color;
        this.polygons = polygons;
        this.setPolygonColor();
    }
    public Tetrahedron(CPolygon... polygons){
        this.color = Color.BLACK;
        this.polygons = polygons;
    }
    public void render(Graphics g){
        for(CPolygon poly : this.polygons){
            poly.frameRender(g);
        }
    }
    public void sortPolygons(){

    }

    public void setPolygonColor(){
        for (CPolygon poly : this.polygons){
            poly.setColor(this.color);
        }
    }
    public void setPolygonColor(Color color){
        for (CPolygon poly : this.polygons){
            poly.setColor(color);
            this.color = color;
        }
    }
}
