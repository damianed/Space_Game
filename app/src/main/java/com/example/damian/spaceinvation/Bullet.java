package com.example.damian.spaceinvation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet extends GameObject {
    public Bullet(Bitmap image, int x, int y) {
        super(image);
        yVelocity = -20;
        this.x = x;
        this.y = y;
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRoundRect((float)this.getX(), (float)this.getY(), (float) this.getX() + this.getWidth(), (float)this.getY() + this.getHeigth(),0,0, paint);
    }
    @Override
    public void update() {
        y += yVelocity;
    }
}
