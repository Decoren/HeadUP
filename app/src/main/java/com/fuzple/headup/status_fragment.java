package com.fuzple.headup;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Created by user on 2017-12-21.
 */

public class status_fragment extends Fragment {

    BluetoothAdapter mbta;
    ImageView iv;
    LinearLayout ll;
    TextView tv;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v =inflater.inflate(R.layout.status,container,false);
        iv = new ImageView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        ll = (LinearLayout)v.findViewById(R.id.status_linear);
        tv = (TextView)v.findViewById(R.id.s_text);
        tv.setText("배터리");

        mbta = BluetoothAdapter.getDefaultAdapter();

        if(mbta.isEnabled())
        {
            iv.setLayoutParams(lp);
            ll.addView(iv);
        }
        else
        {
            ll.removeView(iv);
        }

       // Log.d("battery",getBatteryPercentage(getContext()));
        return v;
    }

    public void onStart(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        super.onStart();
    }

    public static int getBatteryPercentage(Context context) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }
}
