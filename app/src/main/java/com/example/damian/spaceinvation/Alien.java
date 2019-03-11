package com.example.damian.spaceinvation;

import android.content.res.Resources;
import android.graphics.Bitmap;

import java.util.Random;

public class Alien extends GameObject {
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public Alien(Bitmap image) {
        super(image);
        this.xVelocity = new Random().nextBoolean() ? 4 : -4;
        this.yVelocity = 4;

        this.x = (new Random()).nextInt(screenWidth - this.getWidth());
        this.y = 0;
    }

    @Override
    public void update(){
        x += xVelocity;
        y += yVelocity;

        if(x <= 0 || x >= screenWidth - this.getWidth()){
            xVelocity = -xVelocity;
        }
    }

    public void setXVelocity(int vel){
        this.xVelocity = vel;
    }
    public void setYVelocity(int vel){
        this.yVelocity = vel;
    }
}
