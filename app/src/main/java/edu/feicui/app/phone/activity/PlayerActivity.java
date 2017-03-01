package edu.feicui.app.phone.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.MusicControl;
import edu.feicui.app.phone.biz.MusicNotifiMgr;
import edu.feicui.app.phone.biz.MusicGressChangeListener;
import edu.feicui.app.phone.biz.util.Tools;
import edu.feicui.app.phone.entity.Mp3Info;
import edu.feicui.app.phone.service.MusicService;

public class PlayerActivity extends BaseActivity implements MusicGressChangeListener, Tools.Mp3InfoListListener {
    Button mBtnPlay, mBtnNext, mBtnStop;
    TextView mTxtMusicTitle;
    ProgressBar mPgbLoadMusicList;
    SeekBar mSkbMusic;
    MusicControl mMyPlayer;
    MusicNotifiMgr mNotifiMgr;
    boolean mMusicService;
    int mLength;
    int mPosition;
    int mSkbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        setToolbar();
        mBtnPlay = (Button) findViewById(R.id.btn_playerActivity_play);
        mBtnNext = (Button) findViewById(R.id.btn_playerActivity_next);
        mBtnStop = (Button) findViewById(R.id.btn_playerActivity_stop);
        mSkbMusic = (SeekBar) findViewById(R.id.btn_playerActivity_skb);
        mTxtMusicTitle = (TextView) findViewById(R.id.txt_player_title);
        mPgbLoadMusicList = (ProgressBar) findViewById(R.id.pgb_player_load);

        mMyPlayer = MusicControl.getInstance(this);
        MusicControl.setMusicGressChangeListener(PlayerActivity.this);
        getMusicFileList();
    }

    public void initSkbProgress() {
        if (mMyPlayer == null) {
            mMyPlayer = MusicControl.getInstance(this);
        }
        if (mMyPlayer.isPlaying()) {
            mLength = mMyPlayer.getDuration();
            mPosition = mMyPlayer.getCurrentPosition();
            double progress = ((double) mPosition / mLength) * 100;
            int gress = (int) progress;
            mSkbMusic.setProgress(gress);
        }
    }

    @Override
    protected void onStart() {
        if(mMyPlayer==null){
            mMyPlayer = MusicControl.getInstance(this);
        }
        mNotifiMgr = MusicNotifiMgr.getInstance(this);
        mNotifiMgr.createNotification();
        initSkbProgress();
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.custom_title_onlyback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void playStatChange(Mp3Info mp3Info,boolean isPlaying,boolean stoped) {
        if (mBtnPlay != null && mMyPlayer != null) {
            if (isPlaying) {
                mBtnPlay.setText("Play");
            } else {
                mBtnPlay.setText("Pause");
            }
        }
        if (mTxtMusicTitle != null) {
            if(!stoped){
                mTxtMusicTitle.setText(mp3Info.mTitle + "-" + mp3Info.mPersion);
            }else if(mSkbMusic!=null){
                mSkbMusic.setProgress(0);
                mTxtMusicTitle.setText("");
            }
        }
    }

    @Override
    public void playProgressUpdate(int progress) {
        mSkbMusic.setProgress(progress);
    }

    public void getMusicFileList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Tools.getExternalMp3FileInfo(PlayerActivity.this, PlayerActivity.this);
            }
        }).start();
    }

    @Override
    public void over(List<Mp3Info> list){
        Log.i("##################","完成");
        if(list!=null&&list.size()!=0){
            mMyPlayer.setMusicFileList(list);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initLayout("");
                    initOnClick();
                }
            });
        }else {
            initLayout("歌曲加载失败");
        }
    }

    public void initLayout(String value) {
        mBtnPlay.setVisibility(View.VISIBLE);
        mBtnStop.setVisibility(View.VISIBLE);
        mBtnNext.setVisibility(View.VISIBLE);
        mSkbMusic.setVisibility(View.VISIBLE);
        mTxtMusicTitle.setText(value);
        mPgbLoadMusicList.setVisibility(View.INVISIBLE);
    }
    public void initOnClick(){
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMusicService) {
                    mMusicService = true;
                    startService(MusicService.class);
                }
                if (mMyPlayer != null && !mMyPlayer.isPlaying()) {
                    mMyPlayer.replayMusic();
                } else {
                    mMyPlayer.PauseMusic();
                }
                mNotifiMgr.sendNotification();
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyPlayer.nextSong();
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyPlayer != null) {
                    mMyPlayer.stopMusic();
                }
            }
        });
        mSkbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mMyPlayer != null && mMyPlayer.isPlaying()) {
                    mLength = mMyPlayer.getDuration();
                    mPosition = mMyPlayer.getCurrentPosition();
                    double progress = ((double) mPosition / mLength) * 100;
                    mSkbProgress = seekBar.getProgress();
                    int gress = (int) progress;
                    if (gress != mSkbProgress) {
                        double d = (double) mSkbProgress / 100;
                        int a = (int) ((double) mLength * d);
                        mMyPlayer.seekTo(a);
                    }
                }
            }
        });
    }

}
