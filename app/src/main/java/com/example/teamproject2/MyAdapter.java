package com.example.teamproject2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<Item> items;

    public TextView txt_shelter;
    public TextView txt_writer;
    public ImageView img_shelter;

    public MyAdapter(Context context, ArrayList list){
        this.ctx = context;
        this.items= list;
    }
    @Override
    public int getCount() {
        return items.size();
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
            view = layoutInflater.inflate(R.layout.item_list, viewGroup, false);
        }
        txt_shelter = (TextView) view.findViewById(R.id.item_shelter);
        txt_writer = (TextView) view.findViewById(R.id.item_writer);
        img_shelter = (ImageView) view.findViewById(R.id.item_icon);

        Item item = (Item) items.get(position);
        txt_shelter.setText(item.shelterName);
        txt_writer.setText(item.writer);
       // Bitmap bitmap = BitmapFactory.decodeByteArray( item.icon, 0, item.icon.length ) ;
        img_shelter.setImageBitmap(item.icon);

        return view;
    }
}
