package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Model.ServiceModel;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 31/07/2016.
 */
public class FragmentService extends Fragment {

    ServiceAdapter adapter;
    List<ServiceModel> data;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;

    private void demodata()
    {
        data.add(new ServiceModel("1","servicename","description"));
        data.add(new ServiceModel("1","servicename","description"));
        data.add(new ServiceModel("1","servicename","description"));
        data.add(new ServiceModel("1","servicename","description"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_service, container, false);

        recyclerView = (RecyclerView)layout.findViewById(R.id.services_list);
        data = new ArrayList<>();
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        demodata();
        adapter = new ServiceAdapter(getActivity() ,data);

        recyclerView.setAdapter(adapter);

        return layout;
    }



    private class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

        List<ServiceModel> mdata;
        Context mContext;

        public ServiceAdapter(Context context , List<ServiceModel> newdata) {
            mContext = context;
            mdata = newdata;
        }


        // Notify Data Changed

        public void setData(List<ServiceModel> newdata) {

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
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_service_listing, null);
            return new ServiceViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(ServiceAdapter.ServiceViewHolder holder, int position) {
            ServiceModel data = mdata.get(position);

            holder.name.setText(data.servicename);
            holder.description.setText(data.Description);

        }

        ///  inner class
        // Put Your Layout Model Here

        public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView description;
            public TextView name;

            public ServiceViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                name  = ((TextView) itemView.findViewById(R.id.service_name));
                description = ((TextView) itemView.findViewById(R.id.service_description));
            }

            @Override
            public void onClick(View v) {
                System.out.println(" Clicked ");
            }
        }
    }


}
