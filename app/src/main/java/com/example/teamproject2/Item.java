package com.example.teamproject2;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class Item {

    public Bitmap icon;
    public String shelterName;
    public String writer,location;

    public Item(Bitmap icon, String shelterName, String writer, String location) {
        this.icon = icon;
        this.shelterName = shelterName;
        this.writer = writer;
        this.location=location;

    }

}
