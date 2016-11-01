package com.procasy.dubarah_nocker.Activity.V1.Recommend;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanks.library.AnimateCheckBox;
import com.procasy.dubarah_nocker.Model.WhyUJoinModel;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

public class WhyFriendJoiningNockerV1Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    JoinAdapter adapter;
    List<WhyUJoinModel> data;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;


    @Deprecated
    void DemoData() {
        for (int i = 0; i < 10; i++)
            data.add(new WhyUJoinModel("Reason " + i, false));

    }

    // Get All User Reasons
    private List<String> GetReasons() {
        List<String> newdata = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).IsCheck)
                newdata.add(data.get(i).name);

        }

        return newdata;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why_you_join_nocker);
        recyclerView = (RecyclerView) findViewById(R.id.all_reason);

        data = new ArrayList<>();

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        // TODO Replace This Fucking Method with your Fucking Real Data
        DemoData();

        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        adapter = new JoinAdapter(getApplicationContext(), data);

        recyclerView.setAdapter(adapter);

    }


    /////////////////////////////////////////////////////////////////////////


    private class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.ProductViewHolder> {

        List<WhyUJoinModel> mdata;
        Context mContext;

        // Conctracture
        public JoinAdapter(Context context, List<WhyUJoinModel> newdata) {
            this.mdata = newdata;
            mContext = context;
        }


        // Notify Data Changed

        public void setData(List<WhyUJoinModel> homeGroups) {

            mdata.clear();
            mdata.addAll(homeGroups);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return mdata.size();
        }


        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.why_u_join_activity, null);
            return new ProductViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder productViewHolder, int position) {

            // Get one item of AdapterView
            WhyUJoinModel group = mdata.get(position);
            // Set Data One Holder
            productViewHolder.reason_anme.setText(group.name);

        }

        ///  inner class
        // Put Your Layout Model Here

        public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AnimateCheckBox.OnCheckedChangeListener {

            public AnimateCheckBox isreason;
            public TextView reason_anme;

            public ProductViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                isreason = ((AnimateCheckBox) itemView.findViewById(R.id.reason_state));
                reason_anme = ((TextView) itemView.findViewById(R.id.reason_name));
                isreason.setOnCheckedChangeListener(this);
            }

            @Override
            public void onClick(View v) {
                System.out.println("Group ");


            }

            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {

                mdata.get(getPosition()).IsCheck = isChecked;

            }
        }
    }


}
