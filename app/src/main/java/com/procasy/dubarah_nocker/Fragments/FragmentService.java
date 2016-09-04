package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Activity.AskForHelpActivity;
import com.procasy.dubarah_nocker.Model.Responses.SkillsResponse;
import com.procasy.dubarah_nocker.Model.ServiceModel;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.SkillsController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 31/07/2016.
 */
public class FragmentService extends Fragment implements SkillsController {

    ServiceAdapter adapter;
    List<SkillsResponse> data;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_service, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.services_list);
        data = new ArrayList<>();
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        adapter = new ServiceAdapter(getActivity(), data);

        recyclerView.setAdapter(adapter);

        return layout;
    }

    @Override
    public void onAdapterUpdated(List<SkillsResponse> newdata) {
        adapter.setData(newdata);
    }


    private class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

        List<SkillsResponse> mdata;
        Context mContext;

        public ServiceAdapter(Context context, List<SkillsResponse> newdata) {
            mContext = context;
            mdata = newdata;
        }


        // Notify Data Changed

        public void setData(List<SkillsResponse> newdata) {

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
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, null);
            return new ServiceViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(ServiceAdapter.ServiceViewHolder holder, int position) {
            SkillsResponse data = mdata.get(position);

            holder.name.setText(data.getSkill_name());

        }

        public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView description;
            public TextView name;
            public Button request_nocker;

            public ServiceViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                request_nocker = (Button)itemView.findViewById(R.id.request_nocker);
                name = ((TextView) itemView.findViewById(R.id.service_name));
                description = ((TextView) itemView.findViewById(R.id.service_description));
                request_nocker.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                System.out.println(" Clicked ");
                Intent i = new Intent(getActivity() , AskForHelpActivity.class);
                i.putExtra(AskForHelpActivity.SKILL_NAME , mdata.get(getPosition()).getSkill_name());
                startActivity(i);

            }
        }
    }


}
