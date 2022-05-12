package renderMake.Shape;

import renderMake.Point.CPoint;

import java.awt.*;

public class Cube {
    int d;

    //right/left shift
    int z;

    //side
    int y;

    //height
    int h;

    //up/down shift
    int k;

    //right/left shift
    int g;

    CPoint p1;
    CPoint p2;
    CPoint p3;
    CPoint p4;
    CPoint p5;
    CPoint p6;
    CPoint p7;
    CPoint p8;

    //front face
    CPolygon c1;
    //back face
    CPolygon c2;
    //bottom face
    CPolygon c3;
    //left face
    CPolygon c4;
    //right face
    CPolygon c5;
    //top face
    CPolygon c6;

    public Cube(int s, int m, int y, int h){
        //s = size
        //m = right/left shift
        //y=side
        //h=height
        //k=up/down shift
        //g = right/left shift
        d=s/2;
        z=d+m;
        p1 = new CPoint(y, z, -h);
        p2 = new CPoint(y, y, -h);
        p3 = new CPoint(y, y, h);
        p4 = new CPoint(y, z, h);
        p5 = new CPoint(z, z, -h);
        p6 = new CPoint(z, y, -h);
        p7 = new CPoint(z, y, h);
        p8 = new CPoint(z, z, h);

        //front face
        c1 = new CPolygon(Color.WHITE, p1, p2, p3, p4);

        //back face
        c2 = new CPolygon(Color.BLACK, p5, p6, p7, p8);

        //bottom face
        c3 = new CPolygon(Color.GREEN, p1, p2, p6, p5);

        //left face
        c4 = new CPolygon(Color.BLUE, p1, p5, p8, p4);

        //right face
        c5 = new CPolygon(Color.RED, p2, p6, p7, p3);

        //top face
        c6 = new CPolygon(Color.ORANGE, p4, p3, p7, p8);
    }
    public Tetrahedron getTetra(Color color){
        return(new Tetrahedron(color, c1, c2, c3, c4, c5, c6));
    }
}