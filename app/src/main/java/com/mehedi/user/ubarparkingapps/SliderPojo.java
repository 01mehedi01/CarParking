package com.mehedi.user.ubarparkingapps;

import android.graphics.Bitmap;

public class SliderPojo {

    private int image;

    public SliderPojo() {
    }

    public SliderPojo(int bitmap) {
        this.image = bitmap;
    }

    public int getBitmap() {
        return image;
    }

    public void setBitmap(int bitmap) {
        this.image = bitmap;
    }
}
