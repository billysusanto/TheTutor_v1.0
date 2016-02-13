package com.tutos.maths.mathstutor.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.tutos.maths.mathstutor.R;
import com.tutos.maths.mathstutor.controller.VariabelConfig;


public class WelcomeMessage extends ActionBarActivity {
    private static int SPLASH_TIME_OUT = 1000; //change the value for customize the splash screen duration

    TextView welcomeMsg;
    VariabelConfig publicVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_message);

        //initialization singleton object to load username to welcome message screen
        publicVar = new VariabelConfig();

        //initialization text view (username & message)
        welcomeMsg = (TextView) findViewById(R.id.welcomeText);
        welcomeMsg.setText("Hi " + publicVar.getUserName() + "...\n Get ready for Tutorial!!!");

        //Thread to show splash screen with time_out
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(WelcomeMessage.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
