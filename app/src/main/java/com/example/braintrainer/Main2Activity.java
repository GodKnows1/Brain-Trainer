package com.example.braintrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    public void whichDifficulty(View view){
        Button button = (Button) view;
        String difficulty = button.getTag().toString();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("Index",difficulty);
        startActivity(intent);
    }

    public void highScore(View view){
        Intent intent  = new Intent(getApplicationContext(),highScoreFile.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
