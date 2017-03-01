package edu.feicui.app.phone.biz;

import edu.feicui.app.phone.entity.Mp3Info;

/**
 * Created by Administrator on 2017/2/19.
 */
public interface MusicGressChangeListener {
    void playStatChange(Mp3Info mp3Info,boolean isPlaying,boolean stoped);
    void playProgressUpdate(int progress);
}
