package renderMake.Color;

import renderMake.Shape.Tetrahedron;

import java.awt.*;

public class Interpolate {
    int val1 = 0;
    int val2 = 255;
    int val3 = 127;
    boolean val1asc = true;
    boolean val2asc = true;
    boolean val3asc = true;
    int ascension;
    public Interpolate(int speed) {
        this.ascension = speed;
    }
    public Color increase(){
        if(val1 > 255 - ascension) {
            val1asc = false;
        }
        if(val2 > 255 - ascension) {
            val2asc = false;
        }
        if(val3 > 255 - ascension) {
            val3asc = false;
        }
        if(val1 < 1 + ascension) {
            val1asc = true;
        }
        if(val2 < 1 + ascension) {
            val2asc = true;
        }
        if(val3 < 1 + ascension) {
            val3asc = true;
        }

        if(val1asc == true) {
            val1 = val1 + ascension;
        }else {
            val1 = val1 - ascension;
        }
        if(val2asc == true) {
            val2 = val2 + ascension;
        }else {
            val2 = val2 - ascension;
        }
        if(val3asc == true) {
            val3 = val3 + ascension;
        }else {
            val3 = val3 - ascension;
        }

        return(new Color(val1,val2,val3));
    }
}
