package com.fuzple.headup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

/**
 * Created by user on 2017-12-22.
 */

public class alset_fragment extends Fragment {

    TimePicker timePicker;
    ToggleButton mon,tues,wednes,thurs,fri,satur,sun;
    ToggleButton top,mid,bot;
    EditText editText;
    Button btn1,btn2;
    datalistener d_listener;
    String[] d;
    String memo;
    int t,m;
    String day;
    int imp;
    int snz;
    Calendar calendar;
    int ok_cancel;
    AlarmManager am;
    PendingIntent pender;
    Thread thread;
    int d_check;

    public interface datalistener
    {
        public void listitemlistener(String s, int time, int minute, String day, int imp, int imm, int snz, int ok_cancel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        d_listener = (datalistener)context;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v =inflater.inflate(R.layout.alarm_set,container,false);
        day = new String();
        d_check = 0;
        d = new String[7];
        calendar = Calendar.getInstance();
        t = calendar.get(calendar.HOUR_OF_DAY);
        m = calendar.get(calendar.MINUTE);
        timePicker = (TimePicker)v.findViewById(R.id.timerpicker);

        editText = (EditText)v.findViewById(R.id.memo);
        sun = (ToggleButton)v.findViewById(R.id.sun);
        mon = (ToggleButton)v.findViewById(R.id.mon);
        tues = (ToggleButton)v.findViewById(R.id.tues);
        wednes = (ToggleButton)v.findViewById(R.id.wednes);
        thurs = (ToggleButton)v.findViewById(R.id.thurs);
        fri = (ToggleButton)v.findViewById(R.id.fri);
        satur = (ToggleButton)v.findViewById(R.id.satur);
        top = (ToggleButton)v.findViewById(R.id.top);
        mid = (ToggleButton)v.findViewById(R.id.mid);
        bot = (ToggleButton)v.findViewById(R.id.bot);
        btn1 = (Button)v.findViewById(R.id.ok);
        btn2 = (Button)v.findViewById(R.id.cancel);

        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                t = i;
                m = i1;
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imp !=0 && d_check > 0) {
                    memo = editText.getText().toString();
                    for (int i = 0; i < 7; i++) {
                        if (d[i] != null) {
                            day += d[i];
                            day += " ";
                        }
                    }
                    ok_cancel = 1;

                    boolean[] week = {false, sun.isChecked(), mon.isChecked(), tues.isChecked(), wednes.isChecked(), thurs.isChecked(), fri.isChecked(), satur.isChecked()};

                    Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                    intent.putExtra("weekday", week);
                    pender = PendingIntent.getBroadcast(getActivity(),((MainActivity) getActivity()).alarm_num , intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    ((MainActivity) getActivity()).alarm_num++;
                    am = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

                    calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DATE), t, m, 0);

                    thread = new Thread(new AlarmThread(am, calendar, pender));
                    thread.start();

                    d_listener.listitemlistener(memo, t, m, day, imp, imp, snz, ok_cancel);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, ((MainActivity) getActivity()).al_f);
                    fragmentTransaction.commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"선택사항을 확인해주세요", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok_cancel = 0;
                d_listener.listitemlistener(memo,t,m,day,imp,imp,snz,ok_cancel);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, ((MainActivity) getActivity()).al_f);
                fragmentTransaction.commit();
            }
        });

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sun.isChecked())
                {
                    sun.setTextColor(Color.RED);
                    d[0] ="su";
                    d_check++;
                }
                else
                {
                    sun.setTextColor(Color.BLACK);
                    d[0] ="";
                    d_check--;
                }
            }
        });
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mon.isChecked())
                {
                    mon.setTextColor(Color.RED);
                    d[1] ="mo";
                    d_check++;
                }
                else
                {
                    mon.setTextColor(Color.BLACK);
                    d[1] = "";
                    d_check--;
                }
            }
        });
        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tues.isChecked())
                {
                    tues.setTextColor(Color.RED);
                    d[2] ="tu";
                    d_check++;
                }
                else
                {
                    tues.setTextColor(Color.BLACK);
                    d[2] = "";
                    d_check--;
                }
            }
        });
        wednes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wednes.isChecked())
                {
                    wednes.setTextColor(Color.RED);
                    d[3] ="we";
                    d_check++;
                }
                else
                {
                    wednes.setTextColor(Color.BLACK);
                    d[3] = "";
                    d_check--;
                }
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(thurs.isChecked())
                {
                    thurs.setTextColor(Color.RED);
                    d[4] ="th";
                    d_check++;
                }
                else
                {
                    thurs.setTextColor(Color.BLACK);
                    d[4] ="";
                    d_check--;
                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fri.isChecked())
                {
                    fri.setTextColor(Color.RED);
                    d[5] ="fr";
                    d_check++;
                }
                else
                {
                    fri.setTextColor(Color.BLACK);
                    d[5] ="";
                    d_check--;
                }
            }
        });
        satur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(satur.isChecked())
                {
                    satur.setTextColor(Color.RED);
                    d[6] ="sa";
                    d_check++;
                }
                else
                {
                    satur.setTextColor(Color.BLACK);
                    d[6] ="";
                    d_check--;
                }
            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(top.isChecked())
                {
                    top.setBackgroundColor(Color.RED);
                    mid.setBackgroundColor(Color.WHITE);
                    bot.setBackgroundColor(Color.WHITE);
                    imp = 3;
                }
                else
                {
                    top.setBackgroundColor(Color.WHITE);
                    imp = 0;
                }
            }
        });
        mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mid.isChecked())
                {
                    top.setBackgroundColor(Color.WHITE);
                    mid.setBackgroundColor(Color.YELLOW);
                    bot.setBackgroundColor(Color.WHITE);
                    imp = 2;
                }
                else
                {
                    mid.setBackgroundColor(Color.WHITE);
                    imp = 0;
                }
            }
        });
        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bot.isChecked())
                {
                    top.setBackgroundColor(Color.WHITE);
                    mid.setBackgroundColor(Color.WHITE);
                    bot.setBackgroundColor(Color.GREEN);
                    imp = 1;
                }
                else
                {
                    bot.setBackgroundColor(Color.WHITE);
                    imp = 0;
                }
            }
        });

        return v;
    }

}
