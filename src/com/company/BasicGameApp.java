//Basic Gam Application
package com.company;

//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;
//
//Graphics Libraries


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.lang.Math;
import java.util.ArrayList;

public class BasicGameApp implements Runnable, MouseListener, KeyListener {
    // Setting width and height variables
    static final int WIDTH = 1000;
    static final int HEIGHT = 700;

    // Variables needed for graphics (???)
    public JFrame frame;
    public static Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    // Images -- one for now to make sure works
    public Image astroImage;
    public Image shipImage;
    public Image bulletImage;
    public Image livesImage;

    // Declare objects here -- ex. private Spaceman space;

    public Ship rocket;

    public Boolean running;
    ArrayList<Asteroid> astros = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();

    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();
        new Thread(ex).start();

    }

    public BasicGameApp() {
        setUpGraphics();
        running = true;




        astroImage = Toolkit.getDefaultToolkit().getImage("simple_asteroid.png"); //load the picture
        shipImage = Toolkit.getDefaultToolkit().getImage("rocket_ship.png"); //load the picture
        bulletImage = Toolkit.getDefaultToolkit().getImage("bullet.png"); //load the picture
        livesImage = Toolkit.getDefaultToolkit().getImage("lives.png"); //load the picture

        rocket = new Ship(WIDTH / 2, HEIGHT / 2);
        //bullet = new Bullet(600, 600);

        for(int x = 0; x < 10; x++) {
            astros.add(new Asteroid((int)(Math.random()*WIDTH), (int)(Math.random()*HEIGHT), 50));
        }
    }

    public void run() {
        //Do stuff when runs
        while (true) {
            moveThings();
            render();
            pause(20);
            crash();
        }
    }

    public void moveThings() {
        for(Asteroid x : astros) {
            x.move();
        }
        rocket.move();
        for(Bullet b : bullets) {
            b.move();
        }

    }
    public void crash() {
        Asteroid astroDelete = null;
        Bullet bulletDelete = null;
        Boolean intersect = false;
        Boolean hitShip = false;
        double deletex = 0;
        double deletey = 0;
        for (Asteroid x : astros) {
            Area area = new Area(x.poly);
            area.intersect(new Area(rocket.poly));
            if (area.isEmpty() == false) {
                rocket.deathCount++;
                rocket.reset();
                astroDelete = x;
                hitShip = true;
            }
            for (Bullet b : bullets) {
                if (x.poly.intersects(b.rect)) {
                    astroDelete = x;
                    bulletDelete = b;
                    deletex = x.xpos;
                    deletey = x.ypos;
                    intersect = true;
                }
            }

        }
        if (intersect == true) {
            astros.remove(astroDelete);
            bullets.remove(bulletDelete);
            if (astroDelete.width == 50) {
                astros.add(new Asteroid(deletex, deletey, 30));
                astros.add(new Asteroid(deletex, deletey, 30));
            } else if (astroDelete.width == 30) {
                astros.add(new Asteroid(deletex, deletey, 15));
                astros.add(new Asteroid(deletex, deletey, 15));
            }

        }
        if(hitShip == true) {
            astros.remove(astroDelete);
        }
        if(rocket.deathCount == 3) {
            astros.removeAll(astros);
            running = false;
        }
    }
    public void pause(int time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e){

        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);

        for(Asteroid x : astros) {
            g.drawPolygon(x.poly);
        }
        
        AffineTransform old = g.getTransform();
        g.translate(rocket.xpos, rocket.ypos + rocket.height / 2);
        g.rotate(Math.toRadians(rocket.rotateValue));
        rocket.xpoly = new int[]{0, rocket.width / 2, 0, -rocket.width / 2};
        rocket.ypoly = new int[]{-rocket.height / 2, rocket.height - rocket.height / 2, rocket.height - rocket.height / 4 - rocket.height / 2, rocket.height - rocket.height / 2};
        rocket.poly = new Polygon(rocket.xpoly, rocket.ypoly, rocket.xpoly.length);
        g.drawPolygon(rocket.poly);
        g.setTransform(old);

        rocket.xpoly = new int[]{(int)rocket.xpos, (int)rocket.xpos + rocket.width / 2, (int)rocket.xpos, (int) rocket.xpos - rocket.width / 2};
        rocket.ypoly = new int[]{(int)rocket.ypos, (int)rocket.ypos + rocket.height, (int)rocket.ypos + rocket.height - rocket.height / 4, (int)rocket.ypos + rocket.height};
        rocket.poly = new Polygon(rocket.xpoly, rocket.ypoly, rocket.xpoly.length);


        for(Bullet b : bullets) {
            g.draw(b.rect);
        }
        for(int q = 0; q < 3 - rocket.deathCount; q++) {
            g.drawImage(livesImage, WIDTH - 90 - (q*70), 35,70,70, null);
        }

        g.dispose();

        bufferStrategy.show();
    }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        canvas.addMouseListener(this);
        canvas.addKeyListener(this);
        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    @Override
    public void mouseClicked(MouseEvent e) {
//        if (rocket.dx*rocket.dx + rocket.dy*rocket.dy < 500) {
//            rocket.dx = rocket.dx + rocket.ax;
//            rocket.dy = rocket.dy + rocket.ay;
//        }

        bullets.add(new Bullet(rocket.xpos, rocket.ypos, Math.cos(Math.toRadians(rocket.rotateValue - 90)), Math.sin(Math.toRadians(rocket.rotateValue - 90))));

        if (running == false) {
            for(int x = 0; x < 10; x++) {
                astros.add(new Asteroid((int)(Math.random()*WIDTH), (int)(Math.random()*HEIGHT), 50));
            }
            running = true;
            rocket.deathCount = 0;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37) {
            rocket.counterClockwise();
        }
        if (e.getKeyCode() == 39) {
            rocket.clockwise();
        }
        if (e.getKeyCode() == 38) {
            rocket.accelerate();
            //rocket.ax = 0.1;

        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
//        if (e.getKeyCode() == 38) {
//            rocket.ax = 0;
//            rocket.ay = 0;
//
//        }
    }
}



