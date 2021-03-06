package com.tutos.maths.mathstutor.controller;

import android.app.Application;

import com.tutos.maths.mathstutor.R;

/**
 * Created by billysusanto on 7/16/2015.
 */


//This object is used for sharing global variabel between pages, such username, result, id, etc.
//Just have set, and get method. without algorithm
    public class VariabelConfig extends Application {
    //private VariabelConfig vg;

    int resId[] = {
            R.raw.audio_1
            ,R.raw.audio_2
            ,R.raw.audio_3
            ,R.raw.audio_4
            ,R.raw.audio_5
            ,R.raw.audio_6
            // ,R.raw.audio_7
            // ,R.raw.audio_8
            };       //id of audio content

    private int count=0;                                //count for current page on tutorial
    private int result = 0;                             //user test result
    private String userName = "";                       //user name

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getResId(){
        return this.resId[this.count];
    }

    public int getResult(){
        return this.result;
    }

    public void incResult(){
        this.result+=10;
    }

    public void resetResult(){
        this.result=0;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUsername(String username){
        this.userName = username;
    }
}
