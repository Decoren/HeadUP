package com.fuzple.headup;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements alset_fragment.datalistener {

    al_fragment al_f;
    status_fragment s_f;
    static Myadapter adapter;
    FragmentManager fm;
    FragmentTransaction tranc;
    int alarm_num =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        tranc = fm.beginTransaction();

        al_f = new al_fragment();
        s_f = new status_fragment();

        adapter = new Myadapter();
        tranc.replace(R.id.status_container,s_f);
        tranc.replace(R.id.main_container,al_f);
        tranc.commit();
    }

    @Override
    public void listitemlistener(String s, int time, int minute, String day, int imp, int imm, int snz, int ok_cancel) {

        Bundle bundle = new Bundle();
        bundle.putString("memo",s);
        bundle.putInt("time",time);
        bundle.putInt("minute",minute);
        bundle.putString("day",day);
        bundle.putInt("imp",imp);
        bundle.putInt("imm",imm);
        bundle.putInt("snz",snz);
        bundle.putInt("ok_cancel",ok_cancel);

        al_f.setArguments(bundle);
    }

    /*public void addadapter(String t,String d)
    {
        adapter.addItem(t,d);
        adapter.notifyDataSetChanged();
    }*/
}
