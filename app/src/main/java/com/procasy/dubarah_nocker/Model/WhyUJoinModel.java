package com.procasy.dubarah_nocker.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.procasy.dubarah_nocker.R;

import java.util.List;

/**
 * Created by DELL on 18/08/2016.
 */
public class WhyUJoinModel {

    public String name;
    public boolean IsCheck;

    public WhyUJoinModel(String name, boolean IsCheck) {
        this.name = name;
        this.IsCheck = IsCheck;
    }

}
