package com.company;

import java.awt.*;
import java.lang.Math;
public class Asteroid {

    public double xpos;
    public double ypos;
    public int width;
    public int height;
    public double dx;
    public double dy;
    public Polygon poly;
    public int[] xpoly;
    public int[] ypoly;
    public int intXpos;
    public int intYpos;

    public Asteroid(double xParamater, double yParamater, int wide) {
        xpos = xParamater;
        ypos = yParamater;
        intXpos = (int)xpos;
        intYpos = (int)ypos;


        width = wide;
        height = width;

        dx = (Math.random() - 0.5) * (11 - width / 10);
        dy = (Math.random()- 0.5) * (11 - width / 10);

        xpoly = new int[]{intXpos, intXpos + width / 2, intXpos + width, intXpos + width - width / 10, intXpos + width / 2, intXpos + width / 3, intXpos - width / 50, intXpos + width / 5};
        ypoly = new int[]{intYpos, intYpos - height / 10, intYpos + height / 5, intYpos + height / 1, intYpos + height - height / 5, intYpos + height - height / 6, intYpos + height, intYpos + height / 2};
        poly = new Polygon(xpoly,ypoly, xpoly.length);

    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        intXpos = (int)xpos;
        intYpos = (int)ypos;

        if((int)xpos > BasicGameApp.WIDTH + width) {
            xpos = -width;
        } if ((int)xpos < -width) {
            xpos = BasicGameApp.WIDTH + width;
        } if ((int)ypos > BasicGameApp.HEIGHT + height) {
            ypos = -height;
        } if ((int)ypos <  -height) {
            ypos = BasicGameApp.HEIGHT + height;
        }

        intXpos = (int)xpos;
        intYpos = (int)ypos;

        xpoly = new int[]{intXpos, intXpos + width / 2, intXpos + width, intXpos + width - width / 10, intXpos + width / 2, intXpos + width / 3, intXpos - width / 50, intXpos + width / 5};
        ypoly = new int[]{intYpos, intYpos - height / 10, intYpos + height / 5, intYpos + height / 1, intYpos + height - height / 5, intYpos + height - height / 6, intYpos + height, intYpos + height / 2};
        poly = new Polygon(xpoly,ypoly, xpoly.length);    }

}
