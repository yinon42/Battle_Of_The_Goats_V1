package com.example.obstacleracegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityGame_KB extends AppCompatActivity {
    AppCompatImageView game_IMG_background;
    private FloatingActionButton game_FAB_left;
    private FloatingActionButton game_FAB_right;
    private MaterialButton game_BTN_goBack;

    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[][] game_IMG_obstacle;
    private ShapeableImageView[] game_IMG_player;
    private Random r;
    private int playerLocation;
    private int life;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_kb);

        findViews();
        initBackground();
        r=new Random();
        playerLocation =1;
        life =3;

        refreshUI();
        checkIfPlayerMove();

        game_BTN_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Close the current activity and go back to the previous one
            }
        });

    }

    private void refreshUI() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkIfCollision();
                        updateHeartsUi();
                        moveObstaclesMatrixDown();
                    }
                });
            }
        }, 2000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshUI();
    }

    private void checkIfCollision() {
        if(game_IMG_obstacle[3][playerLocation].getVisibility()==View.VISIBLE){
            if(life >1){
                life--;
                Toast.makeText(this, "You Lost 1 Life", Toast.LENGTH_SHORT).show();
            }
            else if (life==1){
                Toast.makeText(this, "You Lost 1 Life", Toast.LENGTH_SHORT).show();
                restartLives();
                Toast.makeText(this, "Life's Full", Toast.LENGTH_SHORT).show();
            }
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    private void moveObstaclesMatrixDown() {
        for(int j=0;j<3;j++) {
            for (int i = 3; i > 0; i--) {
                game_IMG_obstacle[i][j].setVisibility(game_IMG_obstacle[i - 1][j].getVisibility());
            }
        }
        for(int i=0;i<3;i++){
            game_IMG_obstacle[0][i].setVisibility(View.INVISIBLE);
        }
        game_IMG_obstacle[0][r.nextInt(3)].setVisibility(View.VISIBLE);

    }

    private void checkIfPlayerMove() {
        game_FAB_left.setOnClickListener(view -> {
            movePlayer(true);
        });
        game_FAB_right.setOnClickListener(view -> {
            movePlayer(false);
        });
    }
    private void movePlayer(boolean isLeft) {
        game_IMG_player[playerLocation].setVisibility(View.INVISIBLE);
        if (isLeft && playerLocation >0) {
            playerLocation--;

        } else if(!isLeft && playerLocation <2) {
            playerLocation++;
        }
        game_IMG_player[playerLocation].setVisibility(View.VISIBLE);
    }

    private boolean crash() {
        return true;
    }

    private void initBackground() {
        Glide
                .with(this)
                .load(R.drawable.bbal_floor)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(game_IMG_background);
    }
    private void findViews() {
        game_IMG_background = findViewById(R.id.game_IMG_background);
        game_FAB_left = findViewById(R.id.game_FAB_left);
        game_FAB_right = findViewById(R.id.game_FAB_right);
        game_BTN_goBack = findViewById(R.id.game_BTN_goBack);
        initHearts();
        initObstaclesMatrix();
        initPlayerPosition();
    }
    private void initHearts(){
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3),
        };
    }
    private void initObstaclesMatrix(){
        game_IMG_obstacle = new ShapeableImageView[][]{
                {findViewById(R.id.game_IMG_kb_1), findViewById(R.id.game_IMG_kb_2), findViewById(R.id.game_IMG_kb_3)},
                {findViewById(R.id.game_IMG_kb_4), findViewById(R.id.game_IMG_kb_5), findViewById(R.id.game_IMG_kb_6)},
                {findViewById(R.id.game_IMG_kb_7), findViewById(R.id.game_IMG_kb_8), findViewById(R.id.game_IMG_kb_9)},
                {findViewById(R.id.game_IMG_kb_10), findViewById(R.id.game_IMG_kb_11), findViewById(R.id.game_IMG_kb_12)},
        };
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                game_IMG_obstacle[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }
    private void initPlayerPosition(){
        game_IMG_player = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_mj_1),
                findViewById(R.id.game_IMG_mj_2),
                findViewById(R.id.game_IMG_mj_3),

        };
        game_IMG_player[0].setVisibility(View.INVISIBLE);
        game_IMG_player[2].setVisibility(View.INVISIBLE);

    }
    private void updateHeartsUi() {
        if(life <game_IMG_hearts.length){
            game_IMG_hearts[life].setVisibility(View.INVISIBLE);
        }
    }
    private void restartLives(){
        life =3;
        game_IMG_hearts[0].setVisibility(View.VISIBLE);
        game_IMG_hearts[1].setVisibility(View.VISIBLE);
        game_IMG_hearts[2].setVisibility(View.VISIBLE);


    }
}
