package com.example.obstacleracegame;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class StartMenu extends AppCompatActivity {

    private MaterialButton startAsMJ;
    private MaterialButton startAsKB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);
        findViews();
        initViews();
    }

    private void initViews() {

        startAsMJ.setOnClickListener(view -> {
           // finish();
            startActivity(new Intent(StartMenu.this, ActivityGame_MJ.class));

        });

        startAsKB.setOnClickListener(view -> {
            //finish();
            startActivity(new Intent(StartMenu.this, ActivityGame_KB.class));

        });

    }

    private void findViews() {
        startAsMJ = findViewById(R.id.startMenu_BTN_MJ);
        startAsKB = findViewById(R.id.startMenu_BTN_KB);

    }


}
