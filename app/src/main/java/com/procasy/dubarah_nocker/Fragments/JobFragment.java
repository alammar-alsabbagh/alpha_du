package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Adapter.NearByNockersAdapter;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.JobModel;
import com.procasy.dubarah_nocker.Model.Responses.NearByNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.ResponseJob;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.ConnectionsConstants;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    JobAdapter adapter;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;
    List<JobModel> mdata;
    SwipeRefreshLayout refreshLayout;
    SessionManager sessionManager;

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

    private void GetJobs() {

        refreshLayout.setRefreshing(true);

//        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
//                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
//                .themeColor(Color.WHITE)
//                .text(getString(R.string.str115))
//                .fadeColor(Color.DKGRAY).build();
//
//        dialog.show();

        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<ResponseJob> call = apiService.GetJobs(sessionManager.getEmail(), sessionManager.getUDID(), 0);
        call.enqueue(new Callback<ResponseJob>() {
            @Override
            public void onResponse(Call<ResponseJob> call, Response<ResponseJob> response) {
                System.out.println(response.body().toString());
                adapter = new JobAdapter(getActivity(), response.body().getJobs());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                refreshLayout.setRefreshing(false);

                ConnectionsConstants.JobsDataIsLoaded = true;
//                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseJob> call, Throwable t) {
                refreshLayout.setRefreshing(false);

                // dialog.dismiss();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_jobs);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetJobs();
            }
        });
        sessionManager = new SessionManager(getActivity());

        if (!ConnectionsConstants.JobsDataIsLoaded)
            GetJobs();

        recyclerView = (RecyclerView) view.findViewById(R.id.alljobs);

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

            public TextView job_name, job_descr, job_distance, job_address;

            public ViewHolder(View view) {
                super(view);

                job_name = (TextView) view.findViewById(R.id.job_title);
                job_descr = (TextView) view.findViewById(R.id.job_description);
                job_distance = (TextView) view.findViewById(R.id.job_distance);
                job_address = (TextView) view.findViewById(R.id.job_country);

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

            JobModel data = mValues.get(position);

            holder.job_name.setText(data.job_title);
            holder.job_descr.setText(data.job_descr);
            holder.job_distance.setText(data.job_distance);
            holder.job_address.setText(data.job_country);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

}
