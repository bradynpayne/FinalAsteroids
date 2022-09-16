package com.company;

import java.awt.*;

public class Bullet {

    public double xpos;
    public double ypos;
    public int width;
    public int height;
    public double dx;
    public double dy;


    public Rectangle rect;

    public Bullet(double xParamater, double yParamater, double rocketDX, double rocketDY) {
        xpos = xParamater;
        ypos = yParamater;
        dx = rocketDX * 2;
        dy = rocketDY * 2;
        width = 4;
        height = 4;

        rect = new Rectangle((int)xpos, (int)ypos, width, height);

    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;

//        if((int)xpos > BasicGameApp.WIDTH + width) {
//            //xpos = -width;
//        } if ((int)xpos < -width) {
//            //xpos = BasicGameApp.WIDTH + width;
//        } if ((int)ypos > BasicGameApp.HEIGHT + height) {
//            //ypos = -height;
//        } if ((int)ypos <  -height) {
//            //ypos = BasicGameApp.HEIGHT + height;
//        }
//        if (dx*dx + dy*dy < 50) {
//            dx = dx + ax;
//            dy = dy + ay;
//        }
        rect = new Rectangle((int)xpos, (int)ypos, width, height);

    }
}
