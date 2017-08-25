package com.yc.mdemos2.mydemos2.fanxinlangvideo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yc.mdemos2.mydemos2.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class FangXinLangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fang_xin_lang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(VPApplication.instance.VideoPlaying!=null)
        {
            if(VPApplication.instance.VideoPlaying.currentState== JCVideoPlayer.CURRENT_STATE_PLAYING)
            {
                VPApplication.instance.VideoPlaying.startButton.performClick();
            }else if (VPApplication.instance.VideoPlaying.currentState== JCVideoPlayer.CURRENT_STATE_PREPAREING)
            {
                JCVideoPlayer.releaseAllVideos();
            }
        }
    }
}

