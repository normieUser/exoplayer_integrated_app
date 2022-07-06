package com.task.exoplayerintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class MainActivity extends AppCompatActivity {
    private PlayerView exoPlayer;
    private SimpleExoPlayer player;
    private MediaItem mediaItem;
    private  long currPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exoPlayer = findViewById(R.id.video_view);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        exoPlayer.setPlayer(player);
        mediaItem = MediaItem.fromUri("https://cdn.videvo.net/videvo_files/video/free/2017-12/small_watermarked/171124_B1_HD_001_preview.webm");
        player.seekTo(currPosition);
        player.addMediaItem(mediaItem);
        player.setPlayWhenReady(true);
        player.prepare();
    }

    @Override
    protected void onPause() {
        super.onPause();
        currPosition = player.getCurrentPosition();
        player.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializePlayer();    }


    public void goToAudio(View view) {
        startActivity(new Intent(this,SongPlayer.class));
    }
}