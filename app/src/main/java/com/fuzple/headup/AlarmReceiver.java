package com.fuzple.headup;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import java.util.Calendar;

/**
 * Created by user on 2017-12-26.
 */

public class AlarmReceiver extends BroadcastReceiver {
    Calendar calendar;

    @Override
    public void onReceive(Context context, Intent intent) {
        calendar = Calendar.getInstance();
        String[] time;
        boolean[] week = intent.getBooleanArrayExtra("weekday");
        intent = new Intent(context, alarmtime.class);

        if(week[calendar.get(Calendar.DAY_OF_WEEK)] )
        {
            for (int i = 0; i < MainActivity.adapter.getCount(); i++) {
                int t;
                Listviewitem listview_item = (Listviewitem) MainActivity.adapter.getItem(i);
                if(listview_item.getSwitch()) {
                    time = listview_item.getTime().split(":");

                    if (listview_item.getAp().equals("PM")) {
                        t = Integer.parseInt(time[0]) + 12;
                    } else {
                        t = Integer.parseInt(time[0]);
                    }
                    if (calendar.get(Calendar.HOUR_OF_DAY) == t && calendar.get(Calendar.MINUTE) == Integer.parseInt(time[1])) {
                        MainActivity.adapter.notifyDataSetChanged();
                        context.startActivity(intent);
                        //((DigitalClockActivity)DigitalClockActivity.mContext).FalalaALarm();
                    }
                }
            }
        }
    }
}
