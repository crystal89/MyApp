package com.crystal.hq.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 102003449 on 2017/5/2.
 */

public class MyGridViewAdapter extends BaseAdapter {
    private Context _con;
    private String[] _items;
    private int[] _icons;

    public MyGridViewAdapter(Context con, String[] items, int[] icons) {
        _con = con;
        _items = items;
        _icons = icons;
    }

    @Override
    public int getCount() {
        return _items.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater factory = LayoutInflater.from(_con);
        //.xml为每一个item的Layout
        View v = factory.inflate(R.layout.slidingdrawer_layout, null);
        //获取View
        ImageView iv = (ImageView) v.findViewById(R.id.sliding_icon);
        iv.setImageResource(_icons[position]);
        TextView tv = (TextView) v.findViewById(R.id.sliding_text);
        tv.setText(_items[position]);
        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return _items[position];
    }
}
