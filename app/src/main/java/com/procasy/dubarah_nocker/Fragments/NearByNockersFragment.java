package com.procasy.dubarah_nocker.Fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Adapter.NearByNockersAdapter;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.Responses.NearByNockerResponse;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.ConnectionsConstants;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NearByNockersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NearByNockersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearByNockersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    NearByNockersAdapter adapter;
    private OnFragmentInteractionListener mListener;
    SessionManager sessionManager;

    public NearByNockersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearByNockersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearByNockersFragment newInstance(String param1, String param2) {
        NearByNockersFragment fragment = new NearByNockersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void GetNockers() {

        refreshLayout.setRefreshing(true);

//        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
//                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
//                .themeColor(Color.WHITE)
//                .text("Getting Nockers")
//                .fadeColor(Color.DKGRAY).build();
//        dialog.show();
//
        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<NearByNockerResponse> call = apiService.GetNearByNockers(sessionManager.getEmail(), sessionManager.getUDID(), 0);
        call.enqueue(new Callback<NearByNockerResponse>() {
            @Override
            public void onResponse(Call<NearByNockerResponse> call, Response<NearByNockerResponse> response) {
                System.out.println(response.body().toString());
                adapter = new NearByNockersAdapter(getActivity(), response.body().getUsers());
                refreshLayout.setRefreshing(false);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                // dialog.dismiss();

                ConnectionsConstants.NockerDataIsLoaded = true;
            }

            @Override
            public void onFailure(Call<NearByNockerResponse> call, Throwable t) {

                refreshLayout.setRefreshing(false);
                //dialog.dismiss();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_near_by_nockers, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_near_by_nockers);
        refreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.refresh_nockers);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetNockers();
            }
        });
        sessionManager = new SessionManager(getActivity());

        if (!ConnectionsConstants.NockerDataIsLoaded)
            GetNockers();
        System.out.println("************************************************************************* nearbynockers");

        return layout;
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
}
