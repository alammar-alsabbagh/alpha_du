package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Activity.Nocker.OtherProfileActivity;
import com.procasy.dubarah_nocker.Adapter.NearByNockersAdapter;
import com.procasy.dubarah_nocker.Model.FavModel;
import com.procasy.dubarah_nocker.Model.Responses.NockerResponse;
import com.procasy.dubarah_nocker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 09/08/2016.
 */
public class FavFragment extends Fragment {


    FavAdapter adapter;
    RecyclerView recyclerView;
    List<FavModel> mdata;

    void DemoData()
    {
        mdata.add(new FavModel("","",""));
        mdata.add(new FavModel("","",""));
        mdata.add(new FavModel("","",""));
        mdata.add(new FavModel("","",""));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.allfavorite);
        mdata = new ArrayList<>();

        DemoData();
        adapter = new FavAdapter(getActivity(), mdata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return layout;
    }


    ///////////////////////////////////////////////////////////////////////////////////


    public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {


        private List<FavModel> mValues;
        Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View view, final Context context) {
                super(view);

            }


        }

        public FavAdapter(Context context, List<FavModel> items) {
            mValues = items;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false);
            return new ViewHolder(view, context);
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
