package com.example.teamproject2;

import android.graphics.drawable.Drawable;

public class Item {

    public Drawable icon;
    public String shelterName;
    public String writer,location;

    public Item(Drawable icon, String shelterName, String writer, String location) {
        this.icon = icon;
        this.shelterName = shelterName;
        this.writer = writer;
        this.location=location;

    }

}
