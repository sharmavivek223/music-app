package com.example.user.musicapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SampleMusic extends AppCompatActivity {
public MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_music);


    }

    public void playSample(View view)
    {
       mp=MediaPlayer.create(this,R.raw.sample);
       mp.start();
    }
}
