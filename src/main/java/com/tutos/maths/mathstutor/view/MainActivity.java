package com.tutos.maths.mathstutor.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutos.maths.mathstutor.R;
import com.tutos.maths.mathstutor.controller.VariabelConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    TextView tutorialTitle;
    int once = 0;
    double finalTime=0, timeElapsed=0;
    int forwardTime = 2000, backwardTime = 2000;
    ImageButton prev, next, rew, ff;
    Button startDescription;
    VariabelConfig publicVar = new VariabelConfig();
    Context context = this;
    WebView webView;

    MediaPlayer mp;
    SeekBar seekBar;
    ImageButton play, pause;
    Handler durationHandler = new Handler();

    List tutorialContent1 = new ArrayList <String> ();
    List tutorialTitleList = new ArrayList <String> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Initialization view - start

        //get content string from string.xml
        tutorialContent1 = Arrays.asList(getResources().getStringArray(R.array.tutorial_content_1));
        tutorialTitleList = Arrays.asList(getResources().getStringArray(R.array.tutorial_title));

        //web view (to decode HTML format)
        webView = (WebView) findViewById(R.id.webView);
        webView.setBackgroundColor(Color.parseColor("#a2a2a2"));

        //content text view
        tutorialTitle = (TextView) findViewById(R.id.tutorialTitle);
        tutorialTitle.setText(tutorialTitleList.get(publicVar.getCount()).toString());

        //media player & seekbar
        mp = MediaPlayer.create(this, publicVar.getResId());
        finalTime = mp.getDuration();
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax((int) finalTime);

        //button
        play = (ImageButton) findViewById(R.id.playButton);
        pause = (ImageButton) findViewById(R.id.pauseButton);
        prev = (ImageButton) findViewById(R.id.prev);
        next = (ImageButton) findViewById(R.id.next);
        rew = (ImageButton) findViewById(R.id.rewindButton);
        ff = (ImageButton) findViewById(R.id.forwardButton);

        startDescription = (Button) findViewById(R.id.description);
        //Initialization view - end


        //load the default tutorial page (page 1)
        webView.loadData(tutorialContent1.get(publicVar.getCount()).toString(), "text/html", "ISO-8859-1");

        startDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sequence process when the start a test pressed
                mediaStop();
                publicVar.setCount(0);
                Intent i = new Intent(MainActivity.this, TestDescription.class);
                startActivity(i);
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlay();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        rew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewind(v);
            }
        });

        ff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                forward(v);
            }
        });

        prev.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(publicVar.getCount() > 0){
                    publicVar.setCount(publicVar.getCount()-1);

                    //Set tutorial title
                    tutorialTitle.setText(tutorialTitleList.get(publicVar.getCount()).toString());

                    //Load tutorial content in HTML format
                    webView.loadData(tutorialContent1.get(publicVar.getCount()).toString(), "text/html", "utf-8");
                    //reset scroll to top position after load prev page
                    webView.scrollTo(0,0);

                    //Load prev audio
                    mp.reset();
                    try {
                        //set audio content
                        mp.setDataSource(context, Uri.parse("android.resource://" + getPackageName() + "/" + publicVar.getResId()));
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Reset Maximum seekBar duration
                    seekBar.setMax((int) mp.getDuration());
                }
                else{
                    Toast.makeText(context, "This is the first page", Toast.LENGTH_LONG).show();
                }
            }
        });

        next.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(publicVar.getCount() < tutorialTitleList.size()-1){
                    publicVar.setCount(publicVar.getCount()+1);

                    //Set tutorial title
                    tutorialTitle.setText(tutorialTitleList.get(publicVar.getCount()).toString());

                    //Load tutorial content in HTML format
                    webView.loadData(tutorialContent1.get(publicVar.getCount()).toString(), "text/html", "utf-8");
                    //reset scroll to top position after load next page
                    webView.scrollTo(0,0);

                    //Load next audio
                    mp.reset();
                    try {
                        //set audio content
                        mp.setDataSource(context, Uri.parse("android.resource://" + getPackageName() + "/" + publicVar.getResId()));
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Reset Maximum seekBar duration
                    seekBar.setMax((int) mp.getDuration());
                }
                else{
                    Toast.makeText(context, "You have reach the last page", Toast.LENGTH_LONG).show();
                    startDescription.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //Method for update seekBar when the audio played
    Runnable updateSeekBarTime = new Runnable(){
        public void run(){
            timeElapsed = mp.getCurrentPosition();
            seekBar.setProgress((int) timeElapsed);
            durationHandler.postDelayed(this, 100);
        }
    };

    //Method for trigger command when back button pressed
    @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Quit Tutorial")
                    .setMessage("Are you sure want to quit this tutorial?")
                    .setPositiveButton("Yes, i'm done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Stop the activity
                            publicVar.setCount(0);
                            mediaStop();
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Method for trigger command when menu button pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.mainMenu:
                break;
            case R.id.aboutMenu:
                publicVar.setCount(0);
                Intent i = new Intent(MainActivity.this, About.class);
                startActivity(i);
                finish();
                break;
            case R.id.creditMenu:
                publicVar.setCount(0);
                Intent j = new Intent(MainActivity.this, Credits.class);
                startActivity(j);
                finish();
                break;
        }
        return true;
    }

    void mediaPlay(){
        //sequence process to play the audio
        mp.create(context, publicVar.getResId());
        mp.start();
        timeElapsed = mp.getCurrentPosition();
        seekBar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 100);
    }

    void mediaStop(){
        //sequence process to stop the audio
        mp.stop();
        seekBar.setProgress(0);
    }

    public void forward(View view) {
        //sequence process to forward the audio
        if ((timeElapsed + forwardTime) <= finalTime) {
            timeElapsed = timeElapsed + forwardTime;

            mp.seekTo((int) timeElapsed);
        }
    }

    public void rewind(View view) {
        //sequence process to rewind the audio
        if ((timeElapsed - backwardTime) > 0) {
            timeElapsed = timeElapsed - backwardTime;

            mp.seekTo((int) timeElapsed);
        }
    }
}
