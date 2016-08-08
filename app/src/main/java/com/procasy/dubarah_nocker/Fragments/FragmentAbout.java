package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.procasy.dubarah_nocker.AboutController;
import com.procasy.dubarah_nocker.Model.AboutModel;
import com.procasy.dubarah_nocker.Model.ServiceModel;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 31/07/2016.
 */

public class FragmentAbout extends Fragment implements AboutController {

    AboutAdapter adapter;
    List<AboutModel> data;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;


    private void demodata()
    {
        data.add(new AboutModel("Data"));
        data.add(new AboutModel("Data"));
        data.add(new AboutModel("Data"));
        data.add(new AboutModel("Data"));
        data.add(new AboutModel("Data"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_about, container, false);


        recyclerView = (RecyclerView)layout.findViewById(R.id.about_card);
        data = new ArrayList<>();
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        demodata();
        adapter = new AboutAdapter(getActivity() ,data);

        recyclerView.setAdapter(adapter);


        return layout;
    }

    @Override
    public void updateAdapter(List<String> newdata) {

        //adapter.setData(newdata);
    }


    private class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ServiceViewHolder> {

        List<AboutModel> mdata;
        Context mContext;

        public AboutAdapter(Context context, List<AboutModel> newdata) {
            mContext = context;
            mdata = newdata;
        }


        // Notify Data Changed

        public void setData(List<AboutModel> newdata) {

            mdata.clear();
            mdata.addAll(newdata);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return mdata.size();
        }


        @Override
        public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_item, null);
            return new ServiceViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(AboutAdapter.ServiceViewHolder holder, int position) {
            AboutModel data = mdata.get(position);

            holder.name.setText(data.data);
        }

        public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView name;

            public ServiceViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                name = ((TextView) itemView.findViewById(R.id.data_txt));
            }

            @Override
            public void onClick(View v) {
                System.out.println(" Clicked ");
            }
        }
    }


}
