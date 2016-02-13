package com.tutos.maths.mathstutor.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.tutos.maths.mathstutor.R;
import com.tutos.maths.mathstutor.controller.DatabaseHelper;
import com.tutos.maths.mathstutor.model.Marque;
import com.tutos.maths.mathstutor.model.QuestionTest;

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 1000; //change the value for customize the splash screen duration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        DatabaseHelper dh = new DatabaseHelper(this);

        try {
            if (dh.getMarqueText() == null) {
                QuestionTest q = new QuestionTest();
                q.setQuestion("(3 - 1) x (9 - 7)");
                q.setAnswer("4");
                q.setMultipleChoice("4#5#6");

                dh.createQuestion(q);

                Marque mq = new Marque();
                mq.setMarque("Test\\n\n" +
                        "\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\\\n\\n\" +\n" +
                        "                        \"        Tutorial Description - Marque\\n");

                dh.updateMarqueText(mq.getMarque());
            }
        }
        catch(Exception ex){
            Log.d("Error Create DB", ex+"");
        }

        //Show the splass screen with time_out
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, TutorialDescription.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
