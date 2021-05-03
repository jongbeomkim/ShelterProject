package com.example.teamproject2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
public class MyAdapter extends BaseAdapter {
    private Context ctx;
    ArrayList<MyData> list;

    public TextView txt_name1;
    public TextView txt_name2;
    public ImageView img_shelter;

    public MyAdapter(Context context, ArrayList list){
        this.ctx = context;
        this.list= list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.shelter_list, viewGroup, false);
        }
        txt_name1 = (TextView) view.findViewById(R.id.list_name1);
        txt_name2 = (TextView) view.findViewById(R.id.list_name2);
        img_shelter = (ImageView) view.findViewById(R.id.list_icon);
        MyData myData = (MyData) list.get(position);
        txt_name1.setText(myData.name1);
        txt_name2.setText(myData.name2);
        img_shelter.setImageResource(myData.icon);
        Glide
                .with(ctx)
                .load(myData.icon)
                .centerCrop()
                .apply(new RequestOptions().override(24, 24))
                .into(img_shelter);
        return view;
    }
}
