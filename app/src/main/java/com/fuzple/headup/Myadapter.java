package com.fuzple.headup;

/**
 * Created by user on 2017-12-21.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2017-12-12.
 */

public class Myadapter extends BaseAdapter {
    private ArrayList<Listviewitem> listViewItemList = new ArrayList<Listviewitem>();

    // ListViewAdapter의 생성자
    public Myadapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    Listviewitem item;
    int pos;
    public View getView(int position, View convertView, ViewGroup parent) {
        pos = position;
        final Context context = parent.getContext();
        View v = convertView;
        ViewHolder viewholder  = new ViewHolder();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_item, parent, false);
            v.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
            viewholder.iv2 = (ImageView) v.findViewById(R.id.im_image);
            viewholder.tv1 = (TextView) v.findViewById(R.id.memo);
            viewholder.tv2 = (TextView) v.findViewById(R.id.time);
            viewholder.tv3 = (TextView) v.findViewById(R.id.day);
            viewholder.ap = (TextView) v.findViewById(R.id.ap);
            viewholder.sw = (Switch)v.findViewById(R.id.list_switch);
            v.setTag(viewholder);
        }
        else
        {
            viewholder = (ViewHolder)convertView.getTag();
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        item = (Listviewitem)getItem(position);
        viewholder.sw.setTag(item);

        viewholder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                Listviewitem lv = (Listviewitem)v.getTag();

                if(isChecked==true)
                {
                    lv.setSwitch(true);
                    notifyDataSetChanged();
                }
                else
                {
                    lv.setSwitch(false);
                    notifyDataSetChanged();
                }
            }
        });
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Listviewitem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        viewholder.tv1.setText(listViewItem.getMemo());
        viewholder.tv2.setText(listViewItem.getTime());
        viewholder.tv3.setText(listViewItem.getDay());
        viewholder.ap.setText(listViewItem.getAp());
        viewholder.iv2.setBackgroundColor(listViewItem.getimimage());
        viewholder.sw.setChecked(listViewItem.getSwitch());

        return v;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String memo, String time, String day, String ap, int im, int imm, boolean sc) {
        item = new Listviewitem();

        item.setTime(time);
        item.setDay(day);
        item.setMemo(memo);
        item.setap(ap);
        item.setimimage(im);
        item.setim(imm);
        item.setSwitch(sc);

        listViewItemList.add(item);
    }

    public void deleteItem(int i)
    {
        listViewItemList.remove(i);
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView iv2;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView ap;
        Switch sw;
    }

}
