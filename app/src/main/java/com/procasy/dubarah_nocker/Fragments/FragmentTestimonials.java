package com.procasy.dubarah_nocker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.procasy.dubarah_nocker.R;

/**
 * Created by DELL on 31/07/2016.
 */
public class FragmentTestimonials extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_testimonials, container, false);

        return layout;
    }
}
