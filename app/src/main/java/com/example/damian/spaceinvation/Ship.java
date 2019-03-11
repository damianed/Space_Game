package com.example.damian.spaceinvation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

public class Ship extends GameObject{
//    private View image;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public Ship(Bitmap image) {
        super(image);
        this.image = image;
        this.x = screenWidth/2 - (image.getWidth()/2);
        this.y =  screenHeight - (image.getHeight());
    }

    @Override
    public void update(){
        x += xVelocity;
        y += yVelocity;
        if(x < 0){
            x = 0;
        }
        if(y < 0){
            y = 0;
        }
        if(x > screenWidth - image.getWidth()){
            x = screenWidth - image.getWidth();
        }
        if(y > screenHeight - image.getHeight()){
            y = screenHeight - image.getHeight();
        }
    }
}
