package com.example.damian.spaceinvation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;


public class GameView extends SurfaceView implements SensorEventListener, SurfaceHolder.Callback {
    private GameLoop thread;
    private Ship ship;
    private ArrayList<Bullet> bullets = new ArrayList<>(50);
    private ArrayList<Alien> aliens = new ArrayList<>();
    private int score;
    private int speedCounter = 1;
    SensorManager sensorManager;
    Sensor accelerometer;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        thread = new GameLoop(getHolder(), this);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Bitmap bulletImage = Bitmap.createBitmap(10, 20, Bitmap.Config.ARGB_8888);
                    Bullet bullet = new Bullet(bulletImage, ship.x + ship.getWidth()/2, ship.y);
                    bullets.add(bullet);
                    return true;
                }
                return false;
            }
        });

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Bitmap alienimage = BitmapFactory.decodeResource(getResources(), R.drawable.alien);
                    Alien alien = new Alien(Bitmap.createScaledBitmap(alienimage,200, 200, true));
                    alien.setYVelocity(4 + (int)(speedCounter * 0.1));
                    aliens.add(alien);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    speedCounter++;
                }
            }
        });

        t.start();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap shipImage = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
        ship = new Ship(Bitmap.createScaledBitmap(shipImage, 200, 200, true));
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(true);
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(){
        ship.update();
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.update();
            if(bullet.getY() < 0){
                bullets.remove(bullet);
            }
        }

        for(int i = 0; i < aliens.size(); i++){
            Alien alien = aliens.get(i);
            if(alien.getY() > screenHeight){
                aliens.remove(alien);
            }
            alien.update();
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            ship.draw(canvas);
            for(int i = 0; i < bullets.size(); i++){
                bullets.get(i).draw(canvas);
            }
            for(int i = 0; i < aliens.size(); i++){
                aliens.get(i).draw(canvas);
            }

            Paint p = new Paint();
            p.setColor(Color.WHITE);
            p.setTextSize(50);
            canvas.drawText("Score: " + score, 10,50, p);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if(ship != null) {
                ship.xVelocity = -(int) sensorEvent.values[0] * 2;
                ship.yVelocity = (int) sensorEvent.values[1] * 2;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void checkCollisions(Canvas canvas) {
        bulletAlienCollition();
        if(playerAlienCollition()){
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setTextSize(100);
            canvas.drawText("Perdiste :(", ship.x, ship.y, p);
        }
    }

    private boolean playerAlienCollition() {
        for(int i = 0; i < aliens.size(); i++){
            if(collition(ship, aliens.get(i))){
                thread.pause();
                return true;
            }
        }
        return false;
    }

    public void bulletAlienCollition(){
        for(int i = 0; i < bullets.size(); i++){
            for(int j = 0; j < aliens.size(); j++){
                if(collition(bullets.get(i), aliens.get(j))){
                    bullets.remove(i);
                    aliens.remove(j);
                    score++;
                }
            }
        }
    }
    public boolean collition(GameObject o1, GameObject o2){
        if(((o1.getX() < o2.getX() && o1.getX() + o1.getWidth() > o2.getX()) || (o2.getX() < o1.getX() && o2.getX() + o2.getWidth() > o1.getX())) && ((o1.getY() <= o2.getY() && o1.getY() + o1.getHeigth() >= o2.getY()) || (o2.getY() <= o1.getY() && o2.getY() + o2.getHeigth() >= o1.getY()))){
            return true;
        }
        return false;
    }

    public void restart(){
        Bitmap shipImage = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
        ship = new Ship(Bitmap.createScaledBitmap(shipImage, 200, 200, true));
        score = 0;
        speedCounter = 1;
        aliens = new ArrayList<>();
        bullets = new ArrayList<>(50);
        thread.pause();
        thread = new GameLoop(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
}
