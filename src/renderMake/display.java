package renderMake;

import renderMake.Color.Interpolate;
import renderMake.Point.CPoint;
import renderMake.Point.Empty;
import renderMake.Shape.CPolygon;
import renderMake.Shape.Cube;
import renderMake.Shape.Tetrahedron;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.lang.ref.Cleaner;
import java.util.ArrayList;

public class display extends Canvas implements Runnable {
    ArrayList<Cube> cubeArrayList = new ArrayList<Cube>();
    public Thread thread;
    public JFrame frame;
    public static String title = "CSW";
    public static final int WIDTH = 800;
    public static final int LENGTH = 800;
    public static boolean running = false;
    Interpolate rainbow;
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
    }

    Cleaner cleaner = Cleaner.create();
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs==null){
            createBufferStrategy(4);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,WIDTH,LENGTH);
        //x is the frontal 3d axis. if you shift 90 degrees forward and up, looking down would be a regular xy plane
        Color color = rainbow.increase();
        //first four parameters are to edit the current value of that parameter in the cube object by that amount
        //last three are to move the cube x, y, and z respectively
        Empty empty = new Empty();
        for(int i=0;i<cubeArrayList.size();i++) {
            Cube tCube = cubeArrayList.get(i);
            Tetrahedron tetra;
            if (tCube != null) {
                tCube.updateTetra(color);
                tetra = tCube.tetra;
                tCube.refresh(0, 0, 0, 0, 0, 0, 5);
                tetra.render(g);
                //mark cube as null to make it eligible for garbage collection
                if (tCube.getLocationZ() > 1200) {
                    //using "this" as runnable causes it to rerun
                    cleaner.register(tCube, empty);
                    cubeArrayList.remove(i);
                    System.gc();
                }
            }
        }


        g.dispose();
        bs.show();
    }
    int timer = 0;
    private void update(){
        timer++;
        if(timer>120){
            Cube newCube;
            Cube newCube2;
            newCube = new Cube(300,150,200,300,0,-500,-40000);
            newCube2 = new Cube(300,150,200,300,0,0,-40000);
            newCube.getTetra(Color.GREEN);
            newCube2.getTetra(Color.GREEN);
            cubeArrayList.add(newCube);
            cubeArrayList.add(newCube2);
            timer = 0;
        }
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long currentTime = System.currentTimeMillis();
        final double ns = 16666666.6667;
        double delta = 0;
        int frames = 0;
        init();
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            //System.out.println((now-lastTime)/ns);
            //System.out.println(now);
            //System.out.println(lastTime);
            lastTime = now;
            //System.out.println(frames);
            while (delta >= 1) {
                update();
                delta = 0;
                render();
                frames++;
            }


            if(System.currentTimeMillis()-currentTime > 1000){
                currentTime=System.currentTimeMillis();
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

    int tick;
}
