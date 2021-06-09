package com.example.teamproject2;

import android.graphics.Bitmap;

public class Item {

    public Bitmap icon;
    public String shelterName;
    public String writer,location,memo;

    public Item(Bitmap icon, String shelterName, String writer, String location,String memo) {
        this.icon = icon;
        this.shelterName = shelterName;
        this.writer = writer;
        this.location=location;
        this.memo = memo;
    }

}
