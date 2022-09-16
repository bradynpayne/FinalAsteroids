package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ship {

    public double xpos;
    public double ypos;
    public int width;
    public int height;
    public double dx;
    public double dy;
    public double ax;
    public double ay;
    public int deathCount;

    public int[] xpoly;
    public int[] ypoly;
    public Polygon poly;

    public int rotateValue;


    public Ship(double xParamater, double yParamater) {
        xpos = xParamater;
        ypos = yParamater;
        dx = 0;
        dy = 0;
        width = 25;
        height = 40;

        xpoly = new int[]{(int) xpos, (int) xpos + width / 2, (int)xpos, (int) xpos - width / 2};
        ypoly = new int[]{(int)ypos, (int)ypos + height, (int)ypos + height - height / 4, (int)ypos + height};
        poly = new Polygon(xpoly, ypoly, xpoly.length);

    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;

        if((int)xpos > BasicGameApp.WIDTH + width) {
            xpos = -width;
        } if ((int)xpos < -width) {
            xpos = BasicGameApp.WIDTH + width;
        } if ((int)ypos > BasicGameApp.HEIGHT + height) {
            ypos = -height;
        } if ((int)ypos <  -height) {
            ypos = BasicGameApp.HEIGHT + height;
        }

        xpoly = new int[]{(int) xpos, (int) xpos + width / 2, (int)xpos, (int) xpos - width / 2};
        ypoly = new int[]{(int)ypos, (int)ypos + height, (int)ypos + height - height / 4, (int)ypos + height};
        poly = new Polygon(xpoly, ypoly, xpoly.length);

    }

    public void reset() {
        xpos = BasicGameApp.WIDTH / 2;
        ypos = BasicGameApp.HEIGHT / 2;
        dx = 0;
        dy = 0;
    }

    public void accelerate(){
         ax = Math.cos(Math.toRadians(rotateValue - 90));
         ay = Math.sin(Math.toRadians(rotateValue - 90));

        if (dx*dx + dy*dy < 40) {
            dx = dx + ax;
            dy = dy + ay;
        }


    }

    public void counterClockwise(){
        rotateValue = rotateValue - 15;
    }
    public void clockwise(){
        rotateValue = rotateValue + 15;
    }

}