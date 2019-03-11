package com.example.damian.spaceinvation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameObject{
    protected int x;
    protected int y;
    protected int xVelocity = 0, yVelocity = 0;
    protected Bitmap image;
    protected Paint paint;

    public GameObject(Bitmap image){
        this.image = image;
    }

    public void draw(Canvas canvas){canvas.drawBitmap(image, x, y, paint);}


    public void update(){}

    public int getY(){return this.y;}

    public int getX(){return this.x;}

    public int getxVelocity(){return this.xVelocity;}
    public int getyVelocity(){return this.yVelocity;}

    public int getWidth(){return this.image.getWidth();}
    public int getHeigth(){return this.image.getHeight();}
}
