package com.fuzple.headup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by user on 2018-01-15.
 */

public class HoloAlarmFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_holoalarm, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        ImageView iv = (ImageView) getView().findViewById(R.id.FalalaALarm);
        GlideApp.with(this).asGif().load(R.drawable.holoalarm).placeholder(R.drawable.mirei).fitCenter().into(iv);
    }
}
