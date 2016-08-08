package com.procasy.dubarah_nocker.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Model.Responses.SkillsResponse;
import com.procasy.dubarah_nocker.Model.TestimonialsModel;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.TestimonialsController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 31/07/2016.
 */
public class FragmentTestimonials extends Fragment implements TestimonialsController {


    static TestimonialsAdapter adapter;
    List<TestimonialsModel> data;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;

    View layout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        layout = inflater.inflate(R.layout.fragment_testimonials, container, false);


        recyclerView = (RecyclerView) layout.findViewById(R.id.testimonials);
        data = new ArrayList<>();
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        adapter = new TestimonialsAdapter(getActivity(), data);

        recyclerView.setAdapter(adapter);

        return layout;
    }

    @Override
    public void UpdateAdapter(List<TestimonialsModel> newmodel) {
        Log.e("UpdateAdapter", "success");
        adapter.setData(newmodel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    public class TestimonialsAdapter extends RecyclerView.Adapter<TestimonialsAdapter.TaistViewHolder> {

        List<TestimonialsModel> mdata;
        Context mContext;

        public TestimonialsAdapter(Context context, List<TestimonialsModel> newdata) {
            mContext = context;
            mdata = newdata;
        }


        // Notify Data Changed

        public void setData(List<TestimonialsModel> newdata) {

            mdata.clear();
            mdata.addAll(newdata);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return mdata.size();
        }


        @Override
        public TaistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_review_listing, null);
            return new TaistViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(TestimonialsAdapter.TaistViewHolder holder, int position) {
            TestimonialsModel data = mdata.get(position);

            holder.name.setText(data.getUsername());
            holder.description.setText(data.descr);

        }

        public class TaistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView description;
            public TextView name;
            public TextView date;
            //public ImageView img;


            public TaistViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);


                name = ((TextView) itemView.findViewById(R.id.username));
                description = ((TextView) itemView.findViewById(R.id.description));
                date = (TextView) itemView.findViewById(R.id.date);
                // img = (ImageView) itemView.findViewById(R.id.user_img);


            }

            @Override
            public void onClick(View v) {
                System.out.println(" Clicked ");
            }
        }
    }


}
