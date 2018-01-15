package com.fuzple.headup;

import android.app.Activity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by user on 2018-01-02.
 */

public class TwitchViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //몰입모드---
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
        // 몰입 모드를 꼭 적용해야 한다면 아래의 3가지 속성을 모두 적용시켜야 합니다
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //----몰입모드

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitch);

/*
        WebView webview = (WebView) findViewById(R.id.twitchView);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        String data = "<iframe\n" +
                "    src=\"http://player.twitch.tv/?video=v212677287&muted=false\"\n" +
                "    height=\"100%\"\n" +
                "    width=\"100%\"\n" +
                "    frameborder=\"0\"\n" +
                "    scrolling=\"no\"\n" +
                "    allowfullscreen=\"true\"\n" +
                "</iframe>";
        String data2 = "<iframe src=\"https://clips.twitch.tv/embed?clip=BadAmericanKangarooTheThing&autoplay=true&tt_medium=clips_embed\" width=\"640\" height=\"360\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"true\"></iframe>";

        webview.getSettings().getJavaScriptEnabled();
        webview.loadData(data, "text/html; charset=UTF-8", null);
        */

        status_fragment statusbar = new status_fragment();
        statusbar.setArguments(getIntent().getExtras());
        YoutubeFragment yv = YoutubeFragment.newInstance("YTU5tC65GOQ");

        getSupportFragmentManager().beginTransaction().replace(R.id.YTFragment, statusbar).commit();

    }
}
