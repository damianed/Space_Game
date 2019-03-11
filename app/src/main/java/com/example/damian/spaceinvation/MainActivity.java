package com.example.damian.spaceinvation;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity{
    GameView gameView;
    FrameLayout game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameView = new GameView(this);
        game = new FrameLayout(this);
        RelativeLayout buttons = new RelativeLayout(this);
        Button restartGame = new Button(this);
        restartGame.setBackgroundResource(R.drawable.restart);

        RelativeLayout.LayoutParams b1 = new RelativeLayout.LayoutParams(
                200,
                200
        );
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        buttons.setLayoutParams(params);
        buttons.addView(restartGame);
        b1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        b1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        restartGame.setLayoutParams(b1);
        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gameView.restart();
            }
        });
        game.addView(gameView);
        game.addView(buttons);
        setContentView(game);
    }
}
