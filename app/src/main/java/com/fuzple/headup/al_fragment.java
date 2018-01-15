package com.fuzple.headup;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2017-12-21.
 */

public class al_fragment extends Fragment {

    ListView listview;
    Bundle bundle;
    Button alset_btn;
    alset_fragment alset_f;
    TextView t_t;
    Timer timer;
    Handler mHandler  = new Handler();
    ImageView a_i;

    Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String dateString = formatter.format(System.currentTimeMillis());
            String[] s = dateString.split(" ");
            Calendar calendar = Calendar.getInstance();
            t_t.setText(r_day(calendar.get(calendar.DAY_OF_WEEK))+"\n"+s[0]+"\n"+s[1]);
        }
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v =inflater.inflate(R.layout.alarm,container,false);

        alset_f = new alset_fragment();
        timer = new Timer();

        a_i = (ImageView)v.findViewById(R.id.a_image);
        listview = (ListView)v.findViewById(R.id.m_listview);
        alset_btn = (Button)v.findViewById(R.id.al_set);
        t_t = (TextView)v.findViewById(R.id.t_timer);
        MainTimerTask timerTask = new MainTimerTask();
        timer.schedule(timerTask,0,1000);

        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(getActivity()).load(R.drawable.c).apply(options).into(a_i);

        listview.setAdapter(((MainActivity)getActivity()).adapter);

        alset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,alset_f);
                fragmentTransaction.commit();
            }
        });
        return v;
    }

    class MainTimerTask extends TimerTask {

        public void run()
        {
            mHandler.post(mUpdateTimeTask);
        }
    }
    public String r_day(int i)
    {
        String day;
        if(i == Calendar.MONDAY)
        {
            day = "월요일";
        }
        else if(i == Calendar.TUESDAY)
        {
            day = "화요일";
        }
        else if(i == Calendar.WEDNESDAY)
        {
            day = "수요일";
        }
        else if(i == Calendar.THURSDAY)
        {
            day = "목요일";
        }
        else if(i == Calendar.FRIDAY)
        {
            day = "금요일";
        }
        else if(i == Calendar.SATURDAY)
        {
            day = "토요일";
        }
        else
        {
            day = "일요일";
        }

        return day;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bundle = getArguments();

        if (bundle != null) {
            if( bundle.getInt("ok_cancel") == 1) {
                int t = bundle.getInt("time");
                String ap = new String();
                int imp;
                String time = new String();
                if (bundle.getInt("time") >= 13) {
                    t -= 12;
                    if (t < 10) {
                        time += "0" + (bundle.getInt("time") - 12) + ":";
                        ap = "PM";
                    } else {
                        time += (bundle.getInt("time") - 12) + ":";
                        ap = "PM";
                    }
                } else {
                    if (t < 10) {
                        time += "0" + (bundle.getInt("time")) + ":";
                        ap = "AM";
                    } else {
                        time += (bundle.getInt("time")) + ":";
                        ap = "AM";
                    }
                }

                if (bundle.getInt("minute") < 10) {
                    time += "0" + (bundle.getInt("minute"));
                } else {
                    time += (bundle.getInt("minute"));
                }
                if (bundle.getInt("imp") == 3) {
                    imp = Color.RED;
                } else if (bundle.getInt("imp") == 2) {
                    imp = Color.YELLOW;
                } else if (bundle.getInt("imp") == 1) {
                    imp = Color.GREEN;
                } else {
                    imp = Color.WHITE;
                }

                ((MainActivity) getActivity()).adapter.addItem(bundle.getString("memo"), time, bundle.getString("day"), ap, imp,bundle.getInt("imm"));

                Myadapter md = new Myadapter();
                Listviewitem lt = (Listviewitem) MainActivity.adapter.getItem(MainActivity.adapter.getCount()-1);

                for (int i = 0; i < MainActivity.adapter.getCount()-1; i++)
                {
                    Listviewitem l_t = (Listviewitem) MainActivity.adapter.getItem(i);

                    if(lt.getim() > l_t.getim())
                    {
                        md.addItem(lt.getMemo(),lt.getTime(),lt.getDay(),lt.getAp(),lt.getimimage(),lt.getim());
                        lt = l_t;
                    }
                    else if(lt.getim() == l_t.getim())
                    {
                        int t1,t2;
                        String[] tm1 = lt.getTime().split(":");
                        String[] tm2 = l_t.getTime().split(":");
                        if (lt.getAp().equals("PM")) {
                            t1 = (Integer.parseInt(tm1[0])) + 12;
                        } else {
                            t1 = (Integer.parseInt(tm1[0]));
                        }
                        if (l_t.getAp().equals("PM")) {
                            t2 = (Integer.parseInt(tm2[0])) + 12;
                        } else {
                            t2 = (Integer.parseInt(tm2[0]));
                        }

                        if(t1 > t2)
                        {
                            md.addItem(l_t.getMemo(),l_t.getTime(),l_t.getDay(),l_t.getAp(),l_t.getimimage(),l_t.getim());
                        }
                        else if(t1 == t2)
                        {
                            if(Integer.parseInt(tm1[1]) > Integer.parseInt(tm2[1]))
                            {
                                md.addItem(l_t.getMemo(),l_t.getTime(),l_t.getDay(),l_t.getAp(),l_t.getimimage(),l_t.getim());
                            }
                            else
                            {
                                md.addItem(lt.getMemo(),lt.getTime(),lt.getDay(),lt.getAp(),lt.getimimage(),lt.getim());
                                lt = l_t;
                            }
                        }
                        else
                        {
                            md.addItem(lt.getMemo(),lt.getTime(),lt.getDay(),lt.getAp(),lt.getimimage(),lt.getim());
                            lt = l_t;
                        }
                    }
                    else
                    {
                        md.addItem(l_t.getMemo(),l_t.getTime(),l_t.getDay(),l_t.getAp(),l_t.getimimage(),l_t.getim());
                    }
                }

                md.addItem(lt.getMemo(),lt.getTime(),lt.getDay(),lt.getAp(),lt.getimimage(),lt.getim());

                ((MainActivity) getActivity()).adapter = md;
                ((MainActivity) getActivity()).adapter.notifyDataSetChanged();
            }
        }
    }
}
