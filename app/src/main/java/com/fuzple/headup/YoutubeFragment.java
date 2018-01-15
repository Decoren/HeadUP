package com.fuzple.headup;

import android.os.Bundle;

import android.app.Fragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by user on 2018-01-05.
 */

public class YoutubeFragment extends YouTubePlayerSupportFragment {
    public YoutubeFragment() {}
    public static YoutubeFragment newInstance(String url){
        YoutubeFragment f = new YoutubeFragment();

        Bundle b = new Bundle();
        b.putString("url",url);

        f.setArguments(b);
        f.init();

        return f;
    }
    private void init(){
        initialize("AIzaSyBmrgeiteZt14pQp26a4bH8XO61K7ADdvM", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b){
                    youTubePlayer.cueVideo(getArguments().getString("url"));
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
