package com.daemon.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chang on 06/06/16.
 */
public class BeatBox {
    private static final String TAG = "BeatBox";//用于log
    private static final String SOUNDS_FOLDER = "sample_sounds";//保存音频的文件名
    private static final int MAX_SOUNDS = 5;//最大播放数为5，再播放新歌时，最旧的一首会停止。

    private AssetManager mAssets;//财产管理者
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool; //new-->load-->play-->release.

    public BeatBox(Context context){
        mAssets = context.getAssets();//不同的context，所对应的assets是一样的
        //以下构建方法已过时，在Lollipop后有SoundPool.Builder新方法。但本项目的最低API为16，故继续使用。
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC,0);
        loadSounds();
    }

    private void loadSounds(){
        String[] soundNames = null ;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG,"Found " + soundNames.length + " sounds");

        } catch (IOException e) {
            Log.e(TAG,"Could not list assets",e);
        }

        for(String filename : soundNames){
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                Log.e(TAG,"Could not load sound " + filename , e);
            }
        }
    }

    private void load(Sound sound) throws IOException{
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd,1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if(soundId == null){
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release(){
        mSoundPool.release();//清理soundpool
    }

    public List<Sound> getSounds(){
        return mSounds;
    }
}
