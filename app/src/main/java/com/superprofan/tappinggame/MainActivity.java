package com.superprofan.tappinggame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView iv_tap;
    TextView tv_result, tv_info;

    int currentTaps = 0;
    boolean gameStarted = false;
    CountDownTimer timer;

    int bestResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_tap = findViewById(R.id.iv_tap);
        tv_result = findViewById(R.id.tv_result);
        tv_info = findViewById(R.id.tv_info);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        bestResult = preferences.getInt("highScore", 0);

        tv_result.setText("best result " + bestResult);

        iv_tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gameStarted) {
                    currentTaps++;
                } else {
                    tv_info.setText("tap-tap-fap");
                    gameStarted = true;
                    timer.start();
                }
            }
        });

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                long timeTillEnd = l/1000 +1;
                tv_result.setText("Time Remaining: " + timeTillEnd);

            }

            @Override
            public void onFinish() {
                iv_tap.setEnabled(false);
                gameStarted = false;
                tv_info.setText("Game over!");


                if (currentTaps > bestResult){
                    bestResult = currentTaps;

                    SharedPreferences preferences1 = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putInt("highscore", bestResult);
                    editor.apply();

                }
                tv_result.setText("best result " + bestResult + "\nCurrent Taps: " + currentTaps);

                new Handler().postDelayed(new Runnable(){

                    @Override
                    public void run(){
                        iv_tap.setEnabled(true);
                        tv_info.setText("Start Tapping!");
                        currentTaps = 0;
                    }
                }, 2000);
            }
        };
    }
}
