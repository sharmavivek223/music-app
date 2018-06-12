 package com.example.user.musicapp;

 import android.content.Context;
 import android.media.AudioManager;
 import android.media.MediaPlayer;
 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;

 public class SampleMusic extends AppCompatActivity {
 private MediaPlayer mp;
 private AudioManager mAudioManager;
 private Button b2;
 private Button b3;
 private Button b4;


 AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        // Pause playback because your Audio Focus was
                        // temporarily stolen, but will be back soon.
                        // i.e. for a phone call
                        mp.pause();

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback, because you lost the Audio Focus.
                        // i.e. the user started some other playback app
                        // Remember to unregister your controls/buttons here.
                        // And release the kra — Audio Focus!
                        // You’re done.
                        releaseMediaPlayer();
                    } else if (focusChange ==
                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, because something else is also
                        // playing audio over you.
                        // i.e. for notifications or navigation directions
                        // Depending on your audio playback, you may prefer to
                        // pause playback here instead. You do you.

                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN ||
                            focusChange==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT ||
                            focusChange==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE) {
                        // Resume playback, because you hold the Audio Focus
                        // again!
                        // i.e. the phone call ended or the nav directions
                        // are finished
                        // If you implement ducking and lower the volume, be
                        // sure to return it to normal here, as well.
                        mp.start();
                    }

                }
            };

/*
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_music);

        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

Button b1=(Button)findViewById(R.id.button1);
b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();
                int result=mAudioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    //  mAudioManager.registerMediaButtonEventReceiver(RemoteController);
                    mp=MediaPlayer.create(SampleMusic.this,R.raw.sample2);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp1) {
                            releaseMediaPlayer();// finish current activity
                        }
                    });

                }
            }
        });

b2=(Button)findViewById(R.id.button2);
b2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mp.pause();
    }
});

b3=(Button)findViewById(R.id.button3);
b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                releaseMediaPlayer();
            }
        });


b4=(Button)findViewById(R.id.button4);
b4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mp.start();
    }
});



    } //end of onCreate method


    public void playSample(View view)
    {  releaseMediaPlayer();

        int result=mAudioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
        if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
        {
            //  mAudioManager.registerMediaButtonEventReceiver(RemoteController);
            mp=MediaPlayer.create(this,R.raw.sample2);
            mp.start();



        }

    }
    //Clean up the media player by releasing its resources.

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp= null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }


}
