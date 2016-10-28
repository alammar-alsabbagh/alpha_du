package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Adapter.QuoteRecyclerViewAdapter;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.Responses.UserQoutesResponse;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.DividerItemDecoration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessagesFragment extends Fragment {

    SessionManager sessionManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
        Log.e("TAG",sessionManager.getEmail()+"   "+sessionManager.getUDID());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_messages, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<UserQoutesResponse> call = apiService.get_user_qoutes(sessionManager.getEmail(),sessionManager.getUDID());
        call.enqueue(new Callback<UserQoutesResponse>() {
            @Override
            public void onResponse(Call<UserQoutesResponse> call, Response<UserQoutesResponse> response) {
                System.out.println(response.body().toString());
                recyclerView.setAdapter(new QuoteRecyclerViewAdapter(response.body().helpRequests));

            }
            @Override
            public void onFailure(Call<UserQoutesResponse> call, Throwable t) {
                System.out.println("ERROR 2" + t.toString());
            }

        });


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
