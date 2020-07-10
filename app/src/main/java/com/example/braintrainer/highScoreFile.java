package com.example.braintrainer;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.braintrainer.MainActivity.sqLiteDatabase;

public class highScoreFile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    int max =0,index,correct;
    TextView highScore;
    Cursor cursor;

    public int getData(int in){
        cursor=sqLiteDatabase.rawQuery("SELECT MAX(correctq) FROM users GROUP BY indexI HAVING indexI=" + in,null);
        cursor.moveToFirst();
        int t = 0;
        try {
            if (cursor.getString(0) != null) {
                Log.i("Name", cursor.getString(0));
                t=Integer.parseInt(cursor.getString(0));
                cursor.moveToNext();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        cursor.close();
        return t;
    }

    public void easy(View view){
        Button button = (Button) view;
        int tag = Integer.parseInt(button.getTag().toString());
        highScore.setVisibility(View.VISIBLE);
        int t=getData(tag);
        if(tag==0) {
            highScore.setText("Easy: " +t + " questions Corrected at max!!");
        }else if(tag==1){
            highScore.setText("Medium: " + t  + " questions Corrected at max!!");
        } else if(tag==2){
            highScore.setText("Hard: " + t + " questions Corrected at max!!");
        }
        didTapButton();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_file);
        highScore = (TextView) findViewById(R.id.highScoreField);
        sqLiteDatabase = this.openOrCreateDatabase("Users",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (indexI int(3),correctq int(3),totalQues int(3))");


        //Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        //highScore.startAnimation(myAnim);
    }

    public void didTapButton() {

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        highScore.startAnimation(myAnim);
    }
}

   // <the> (if we complete the hard deficulty the star come out);

