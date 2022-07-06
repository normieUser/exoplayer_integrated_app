package com.task.exoplayerintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class SongPlayer extends AppCompatActivity {
    private TextView play,duration;
    private SeekBar progressBar;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;
    boolean isPausable;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);
        play = findViewById(R.id.play);
        progressBar = findViewById(R.id.progress_horizontal);
        duration = findViewById(R.id.duration);
        mediaPlayer = MediaPlayer.create(this,Uri.parse("https://ovamusic.com/wp-content/uploads/2020/07/Panipaali-Ovamusic.com_.mp3"));
        progressBar.setMax(mediaPlayer.getDuration());
        long min = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration());
        duration.setText(""+min+":"+(TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration())-(min*60)));
        runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,100);
            }
        };
       play.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(isPausable){
                    play.setBackgroundResource(R.drawable.ic_baseline_play_circle_24);
                    mediaPlayer.pause();
               } else {
                   play.setBackgroundResource(R.drawable.ic_baseline_pause_circle_24);
                   mediaPlayer.start();
                   handler.postDelayed(runnable,0);
               }

               isPausable = !isPausable;
           }
       });

       progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               if(b){
                   mediaPlayer.seekTo(i);
               }

               if(i == mediaPlayer.getDuration()){
                   play.setBackgroundResource(R.drawable.ic_baseline_play_circle_24);
                   isPausable=false;
               }
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });



    }


    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isPausable)
            mediaPlayer.start();
    }
}