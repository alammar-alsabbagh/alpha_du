package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Model.JobModel;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

public class JobFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    JobAdapter adapter;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;
    List<JobModel> mdata;

    public JobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void DemoData() {
        mdata.add(new JobModel("title", "", "", ""));
        mdata.add(new JobModel("title", "", "", ""));
        mdata.add(new JobModel("title", "", "", ""));
        mdata.add(new JobModel("title", "", "", ""));
        mdata.add(new JobModel("title", "", "", ""));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.alljobs);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        mdata = new ArrayList<>();
        DemoData();
        adapter = new JobAdapter(getActivity(), mdata);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /////////////////////////////////////////////


    public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {


        private List<JobModel> mValues;
        Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView skill_name;
            public final ImageView skill_add;
            public final LinearLayout linearLayout;

            public ViewHolder(View view) {
                super(view);
                skill_name = (TextView) view.findViewById(R.id.skill_name);
                skill_add = (ImageView) view.findViewById(R.id.add_skill);
                linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + skill_name.getText();
            }
        }


        public JobAdapter(Context context, List<JobModel> items) {
            mValues = items;
            this.context = context;

        }

        private void updateData(List<JobModel> newData) {
            mValues.clear();
            mValues.addAll(newData);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.job_item, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

}
