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
        this.tetra = new Cube(300,150,200,300).getTetra(Color.GREEN);
        this.tetra2 = new Cube(-300,-150,-200,300).getTetra(Color.GREEN);
        this.tetra3 = new Cube(300,-75,-25,300).getTetra(Color.GREEN);
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


        this.tetra.setPolygonColor(rainbow.increase());
        this.tetra2.setPolygonColor(rainbow2.increase());
        this.tetra3.setPolygonColor(rainbow3.increase());
        this.tetra.render(g);
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
