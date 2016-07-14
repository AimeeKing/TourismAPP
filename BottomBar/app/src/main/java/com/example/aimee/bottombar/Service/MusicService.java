package com.example.aimee.bottombar.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.aimee.bottombar.newServer.model.Song;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Aimee on 2016/4/24.
 */
public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener{
    private MediaPlayer player;
    private ArrayList<Song> songs;
    private int songPson;
    private MusicBinder musicBinder = new MusicBinder();
    private int duration;
    @Override
    public void onCreate() {
        super.onCreate();
        songPson = 0;
        player = new MediaPlayer();
        initMusicPlayer();
    }



    private void initMusicPlayer() {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnErrorListener(this);
        player.setOnCompletionListener(this);
        player.setOnPreparedListener(this);
        player.setLooping(true);
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public long getCurrentTime() {
        return player.getCurrentPosition();
    }

    public long getDuration() {
        return  duration;
    }

    public void seekTo(int startTime) {
        player.seekTo(startTime);
    }


    public class MusicBinder extends Binder{
       public MusicService getService()
        {
            return MusicService.this;
        }
    }
    public void play() {
        player.reset();
        Song song = songs.get(songPson);
        String trackurl= song.getUrl();
        try {
            player.setDataSource(trackurl);
        } catch (IOException e) {

            Log.e("音乐服务出问题了","音乐服务setData 出错",e);
        }

        player.prepareAsync();
    }

    public void pause()
    {
        this.player.pause();
    }

    public void restart()
    {
        this.player.start();
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        duration = player.getDuration();
        mp.start();
    }
}
