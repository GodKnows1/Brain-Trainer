package com.example.braintrainer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String index ; int intIndex=0;
    Button goButton,button1,button2,button3,button4,button,button8;
    TextView textView3,textView,textView2,textView4;
    GridLayout gridLayout;
    ImageView imageView;
    public static SQLiteDatabase sqLiteDatabase ;
    ConstraintLayout constraintLayout;
    int c,correct=0,noOfQuestions=0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void callAlert(){
        new AlertDialog.Builder(this)
                .setTitle("Timer Says...")
                .setMessage("Oops... Time Over :)")
                .setPositiveButton("Okayssss...", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Want to try again Go on!", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    public void goBack(View view){
        onBackPressed();
    }

    public void resetActivity(View view){
        for(int i=0;i<gridLayout.getChildCount();i++){
            Button button=(Button) gridLayout.getChildAt(i);
            button.setText(null);
        }
        textView4.setVisibility(View.INVISIBLE);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button8.setVisibility(View.INVISIBLE);

        correct=0;noOfQuestions=0;
        String sTextView2="0/0";
        textView2.setText(sTextView2);
        button.setVisibility(View.INVISIBLE);
        timer();
        generate();
    }

    public void generate(){
        Random random = new Random();
        int a = 0,b = 0;
        if(intIndex==0) {
            a = random.nextInt(20);
            b = random.nextInt(20);
        } else if(intIndex == 1){
            a = random.nextInt(40);
            b = random.nextInt(40);
            if(a<15 || b<15)
                generate();
        } else if(intIndex == 2){
            a = random.nextInt(60);
            b = random.nextInt(60);
            if(a<25 || b<25)
                generate();
        }
        c=a+b;
        String showQuestion=Integer.toString(a) + " + " + Integer.toString(b);
        textView3.setText(showQuestion);
        ArrayList<Integer> answerList = new ArrayList<Integer>();
        answerList.add(c);
        for(int j=0;j<3;j++){
            a = random.nextInt(40);
            b = random.nextInt(40);
            while((a+b)==c){
                a = random.nextInt(40);
                b = random.nextInt(40);
            }
            answerList.add(a+b);
        }
        a=random.nextInt(4);
        button1.setText(Integer.toString(answerList.get(a)));
        b=random.nextInt(4);
        while(b==a)
            b=random.nextInt(4);
        button2.setText(Integer.toString(answerList.get(b)));
        int e=random.nextInt(4);
        while(e==a || e==b)
            e=random.nextInt(4);
        button3.setText(Integer.toString(answerList.get(e)));
        int d=random.nextInt(4);
        while(d==a || d==b || d==e)
            d=random.nextInt(4);
        button4.setText(Integer.toString(answerList.get(d)));
    }

    public void timer(){
        new CountDownTimer(30000,1000){
            public  void onTick(long milisecondsUntilDone){
                Log.i("Seconds Done",String.valueOf(milisecondsUntilDone/1000));
                String setTime= String.valueOf(milisecondsUntilDone/1000)+"s";
                textView.setText(setTime);
            }
            public void onFinish(){
                try {
                    String setTime = "0s";
                    textView.setText(setTime);
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    button4.setEnabled(false);
                    textView4.setVisibility(View.INVISIBLE);
                    button = (Button) findViewById(R.id.button);
                    button.setVisibility(View.VISIBLE);
                    button8.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    callAlert();
                    sqLiteDatabase.execSQL("INSERT INTO users VALUES(" + intIndex + "," + correct + "," + noOfQuestions + ")");
                }catch (Exception e){}
            }
        }.start();
    }

    public void goHide(View view){
        goButton=(Button) findViewById(R.id.goButton);
        goButton.setVisibility(View.INVISIBLE);
        gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setVisibility(View.VISIBLE);
        button1=(Button) findViewById(R.id.button1);
        button2=(Button) findViewById(R.id.button2);
        button3=(Button) findViewById(R.id.button3);
        button4=(Button) findViewById(R.id.button4);
        button8 = (Button) findViewById(R.id.button8);
        button8.setVisibility(View.INVISIBLE);
        textView3=(TextView) findViewById(R.id.textView3);
        textView=(TextView) findViewById(R.id.textView);
        textView2=(TextView) findViewById(R.id.textView2);
        textView3.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        timer();
        generate();
    }

    public void checkAnswer(View view){
        Button button = (Button) view;
        int clickedAnswer = Integer.parseInt(button.getText().toString());
        textView4=(TextView) findViewById(R.id.textView4);
        imageView.setVisibility(View.VISIBLE);
        if (c == clickedAnswer){
            textView4.setVisibility(View.VISIBLE);
            textView4.setText("CORRECT :)");
            imageView.setImageResource(R.drawable.ic_mood_black_24dp);
            correct++;
        } else{
            textView4.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_mood_bad_black_24dp);
            textView4.setText("WRONG :(");
        }
        noOfQuestions++;
        String sTextView2=Integer.toString(correct)+ "/" +Integer.toString(noOfQuestions);
        textView2.setText(sTextView2);
        generate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteDatabase = this.openOrCreateDatabase("Users",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (indexI int(3),correctq int(3),totalQues int(3))");
        imageView = (ImageView) findViewById(R.id.imageView);
        constraintLayout=(ConstraintLayout) findViewById(R.id.constraintLayout);
        Intent intent = getIntent();
        index = intent.getStringExtra("Index");
        intIndex = Integer.parseInt(index);
    }
}
