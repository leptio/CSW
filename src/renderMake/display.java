package renderMake;

import renderMake.Color.Interpolate;
import renderMake.Point.CPoint;
import renderMake.Shape.CPolygon;
import renderMake.Shape.Cube;
import renderMake.Shape.Tetrahedron;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class display extends Canvas implements Runnable {

    public Thread thread;
    public JFrame frame;
    public static String title = "CSW";
    public static final int WIDTH = 800;
    public static final int LENGTH = 800;
    public static boolean running = false;
    Cube cube;
    Cube cube2;
    Cube cube3;
    Tetrahedron tetra;
    Tetrahedron tetra2;
    Tetrahedron tetra3;
    Interpolate rainbow;
    Interpolate rainbow2;
    Interpolate rainbow3;
    public display(){
        frame = new JFrame();

        Dimension size = new Dimension(WIDTH, LENGTH);
        setPreferredSize(size);
    }
    public synchronized void start(){
        running = true;
        thread = new Thread(this, "render.display");
        thread.start();
    }
    public synchronized void stop() throws InterruptedException {
        running = false;
        thread.join();
    }

    private void init(){
        this.rainbow = new Interpolate(1);
        this.rainbow2 = new Interpolate(5);
        this.rainbow3 = new Interpolate(10);
        this.cube = new Cube(300,150,200,300,0,0,0);
        this.cube2 = new Cube(-300,-150,-200,300,0,0,0);
        this.cube3 = new Cube(300,-75,-25,300,0,0,0);
    }


    private void render() {

        BufferStrategy bs = getBufferStrategy();
        if (bs==null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,WIDTH,LENGTH);
        //x is the frontal 3d axis. if you shift 90 degrees forward and up, looking down would be a regular xy plane

        this.tetra2 = cube2.getTetra(Color.GREEN);
        this.tetra3 = cube3.getTetra(Color.GREEN);
        //first four parameters are to edit the current value of that parameter in the cube object by that amount
        //last three are to move the cube x, y, and z respectively
        if(this.cube!=null){

            this.tetra = cube.getTetra(Color.GREEN);
            this.cube.refresh(0, 0, 0, 0, 0, 0, 1);
            this.tetra.render(g);
            this.tetra.setPolygonColor(rainbow.increase());
            System.out.println(cube.getLocationZ());

            //mark cube as null to make it eligible for garbage collection
            if(cube.getLocationZ()>900){
                cube = null;
            }
        }



        this.tetra2.setPolygonColor(rainbow2.increase());
        this.tetra3.setPolygonColor(rainbow3.increase());

        this.tetra2.render(g);
        this.tetra3.render(g);

        g.dispose();
        bs.show();
    }

    private void update(){

    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long currentTime = System.currentTimeMillis();
        final double ns = 1000000000 / 60;
        double delta = 0;
        int frames = 0;
        init();
        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime) / ns;
            lastTime = now;
            while(delta>=1){
                update();
                delta--;
                render();
                frames++;
            }


            if(System.currentTimeMillis()-currentTime > 1000){
                currentTime+=1000;
                frame.setTitle(title + " / " + frames + " fps");
                frames=0;
            }
        }

        try {
            stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        display thisDisplay = new display();
        thisDisplay.frame.setTitle(title);
        thisDisplay.frame.add(thisDisplay);
        thisDisplay.frame.pack();
        thisDisplay.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thisDisplay.frame.setLocationRelativeTo(null);
        thisDisplay.frame.setResizable(false);
        thisDisplay.frame.setVisible(true);

        thisDisplay.start();
    }
}
