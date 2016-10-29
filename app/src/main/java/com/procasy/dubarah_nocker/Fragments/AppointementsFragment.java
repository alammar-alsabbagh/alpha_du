package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Adapter.AppointementsAdapter;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.Responses.AppointementResponse;
import com.procasy.dubarah_nocker.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointementsFragment extends Fragment {

    public RecyclerView user_appointemnt , nocker_appointement;
    public AppointementsAdapter adapter1,adapter2;
    SessionManager sessionManager;
    public LinearLayout user_layout , nocker_layout;

    public AppointementsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_appointements, container, false);
        user_appointemnt =(RecyclerView) view.findViewById(R.id.user_appointemnt);
        nocker_appointement =(RecyclerView) view.findViewById(R.id.nocekr_appointement);

        user_layout = (LinearLayout) view.findViewById(R.id.theirjob);
        nocker_layout = (LinearLayout) view.findViewById(R.id.myjobs);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        user_layout.setVisibility(View.GONE);
        nocker_layout.setVisibility(View.GONE);


        final ACProgressFlower dialog = new ACProgressFlower.Builder(getContext())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Info..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<AppointementResponse> call = apiService.Get_user_appoitement(sessionManager.getEmail(),sessionManager.getUDID());
        call.enqueue(new Callback<AppointementResponse>() {
            @Override
            public void onResponse(Call<AppointementResponse> call, Response<AppointementResponse> response) {
                System.out.println(response.body().toString());
                if(response.body().nocker_jobs.size() == 0) {
                    nocker_layout.setVisibility(View.GONE);
                }else{
                    nocker_layout.setVisibility(View.VISIBLE);
                    nocker_appointement.setLayoutManager(layoutManager);
                    adapter1 = new AppointementsAdapter(response.body().nocker_jobs);
                    nocker_appointement.setAdapter(adapter1);
                }

                if(response.body().user_jobs.size() == 0) {
                    user_layout.setVisibility(View.GONE);
                }else{
                    user_layout.setVisibility(View.VISIBLE);
                    user_appointemnt.setLayoutManager(layoutManager);
                    adapter2 = new AppointementsAdapter(response.body().user_jobs);
                    user_appointemnt.setAdapter(adapter2);
                }
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<AppointementResponse> call, Throwable t) {
                System.out.println("ERROR 2" + t.toString());
                dialog.dismiss();

            }

        });


        return view;
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
