package com.example.damian.spaceinvation;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.ArrayList;

import static java.lang.System.nanoTime;
import static java.lang.System.setOut;

public class GameLoop extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private Canvas canvas;
    private int targetFPS = 60;
    private double averageFPS;

    public GameLoop(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetFPS;
        while(running) {
            startTime = System.nanoTime();
            canvas = null;
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gameView.update();
                    this.gameView.draw(canvas);
                    this.gameView.checkCollisions(canvas);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try{
                this.sleep(waitTime);
            }catch (Exception e){
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == targetFPS){
                averageFPS = 1000 / ((totalTime / frameCount)/ 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("FPS: " + averageFPS);
            }
        }
    }

    public void pause(){
        running = false;
    }
    public void setRunning(boolean isRunning){
        this.running = isRunning;
    }
}
