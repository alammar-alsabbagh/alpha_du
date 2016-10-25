package com.procasy.dubarah_nocker.Utils;

/**
 * Created by Omar on 10/25/2016.
 */

import android.animation.TimeInterpolator;

public class ItemModel {
    public final String description;
    public final TimeInterpolator interpolator;

    public ItemModel(String description ,TimeInterpolator interpolator) {
        this.description = description;
        this.interpolator = interpolator;
    }
}