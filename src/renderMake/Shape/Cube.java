package renderMake.Shape;

import renderMake.Point.CPoint;

import java.awt.*;

public class Cube {
    int d;

    //right/left shift
    int z;

    int s;
    int y;
    int h;
    int m;
    int d1;
    int d2;
    int d3;

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

    public Cube(int s, int m, int y, int h, int b3, int b2, int b1){
        //s = size
        //y=side
        //h=height
        //k=up/down shift
        //g = right/left shift
        this.s=s;
        this.m=m;
        this.y=y;
        this.h=h;
        this.d1=b1;
        this.d2=b2;
        this.d3=b3;
        d=s/2;
        z=d+m;
        p1 = new CPoint(y+d1, z+d2, -h+d3);
        p2 = new CPoint(y+d1, y+d2, -h+d3);
        p3 = new CPoint(y+d1, y+d2, h+d3);
        p4 = new CPoint(y+d1, z+d2, h+d3);
        p5 = new CPoint(z+d1, z+d2, -h+d3);
        p6 = new CPoint(z+d1, y+d2, -h+d3);
        p7 = new CPoint(z+d1, y+d2, h+d3);
        p8 = new CPoint(z+d1, z+d2, h+d3);
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
    public void refresh(int a,int b,int c,int e, int b2, int b3, int b1){
        this.s=this.s+a;
        this.m=this.m+b;
        this.y=this.y+c;
        this.h=this.h+e;
        this.d1=this.d1+b1;
        this.d2=this.d2+b2;
        this.d3=this.d3+b3;
        this.d=s/2;
        this.z=this.d+this.m;
        p1 = new CPoint(y+d1, z+d2, -h+d3);
        p2 = new CPoint(y+d1, y+d2, -h+d3);
        p3 = new CPoint(y+d1, y+d2, h+d3);
        p4 = new CPoint(y+d1, z+d2, h+d3);
        p5 = new CPoint(z+d1, z+d2, -h+d3);
        p6 = new CPoint(z+d1, y+d2, -h+d3);
        p7 = new CPoint(z+d1, y+d2, h+d3);
        p8 = new CPoint(z+d1, z+d2, h+d3);

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
