package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.aakira.expandablelayout.Utils;
import com.procasy.dubarah_nocker.Adapter.QuoteRecyclerViewAdapter;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.DividerItemDecoration;
import com.procasy.dubarah_nocker.Utils.ItemModel;

import java.util.ArrayList;
import java.util.List;


public class MessagesFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_messages, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final List<ItemModel> data = new ArrayList<>();
        data.add(new ItemModel(
                "0 ACCELERATE_DECELERATE_INTERPOLATOR",
                Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR)));
        recyclerView.setAdapter(new QuoteRecyclerViewAdapter(data));

        return view;
    }


    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
