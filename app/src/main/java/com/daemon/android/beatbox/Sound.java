package com.daemon.android.beatbox;

/**
 * Created by Chang on 06/06/16.
 */
public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;//用Integer而不用int，是因为:当soundId没有值时，可赋值为null

    public Sound(String assetPath){
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];//取最后的字符串
        mName = filename.replace(".wav","");//把扩展名去掉
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
