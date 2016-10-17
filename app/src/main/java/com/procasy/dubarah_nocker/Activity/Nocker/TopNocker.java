package com.procasy.dubarah_nocker.Activity.Nocker;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Adapter.AdapterCallback;
import com.procasy.dubarah_nocker.Fragments.FragmentService;
import com.procasy.dubarah_nocker.Fragments.FragmentTestimonials;
import com.procasy.dubarah_nocker.Model.Responses.InfoNockerResponse;
import com.procasy.dubarah_nocker.Model.Responses.SkillsResponse;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.SkillsController;
import com.procasy.dubarah_nocker.TestimonialsController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopNocker extends AppCompatActivity {

    CircleImageView user_img;

    TextView user_main_job, user_name, isonline, recommended_by_count;

    ImageView isonline_img;

    RecommendedAdapter adapter;
    FragmentService tab_service = new FragmentService();
    ;
    FragmentTestimonials teb_testimonials = new FragmentTestimonials();

    List<String> data;
    RecyclerView recommended_by_list;
    ViewPagerAdapter viewPagerAdapter;
    SkillsController servicecontroller;
    TestimonialsController testimonialscontroller;

    TabLayout tabLayout;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;
    ViewPager viewPager;

    private void GetData() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(TopNocker.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Info..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<String> call = apiService.Get_top_nocker("email");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("here 2" + t.toString());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });
    }


    @Deprecated
    void FillDemoData() {

        data.add("https://lh3.googleusercontent.com/ZWmuJuXIKQ4jWvZniwhVci-VMUliBQbTnQ1kM3nU3tAdfj4R_WgPrL61JlFwjK39Nw=w300");
        data.add("https://lh3.googleusercontent.com/ZWmuJuXIKQ4jWvZniwhVci-VMUliBQbTnQ1kM3nU3tAdfj4R_WgPrL61JlFwjK39Nw=w300");
        data.add("https://lh3.googleusercontent.com/ZWmuJuXIKQ4jWvZniwhVci-VMUliBQbTnQ1kM3nU3tAdfj4R_WgPrL61JlFwjK39Nw=w300");
        data.add("https://lh3.googleusercontent.com/ZWmuJuXIKQ4jWvZniwhVci-VMUliBQbTnQ1kM3nU3tAdfj4R_WgPrL61JlFwjK39Nw=w300");
        data.add("https://lh3.googleusercontent.com/ZWmuJuXIKQ4jWvZniwhVci-VMUliBQbTnQ1kM3nU3tAdfj4R_WgPrL61JlFwjK39Nw=w300");
        data.add("https://lh3.googleusercontent.com/ZWmuJuXIKQ4jWvZniwhVci-VMUliBQbTnQ1kM3nU3tAdfj4R_WgPrL61JlFwjK39Nw=w300");
        data.add("https://lh3.googleusercontent.com/ZWmuJuXIKQ4jWvZniwhVci-VMUliBQbTnQ1kM3nU3tAdfj4R_WgPrL61JlFwjK39Nw=w300");

        List<String> newdata = new ArrayList<>();
        newdata.addAll(data);

        adapter.updateData(newdata);
        List<SkillsResponse> dataskills = new ArrayList<>();
        dataskills.add(new SkillsResponse(1, "data1", 0));
        dataskills.add(new SkillsResponse(1, "data2", 0));
        dataskills.add(new SkillsResponse(1, "data3", 0));
        dataskills.add(new SkillsResponse(1, "data4", 0));
        dataskills.add(new SkillsResponse(1, "data5", 0));
        servicecontroller = tab_service;
        servicecontroller.onAdapterUpdated(dataskills);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_nocker);

        user_img = (CircleImageView) findViewById(R.id.user_img);
        user_main_job = (TextView) findViewById(R.id.user_main_job);
        user_name = (TextView) findViewById(R.id.user_name);
        isonline = (TextView) findViewById(R.id.online_text);
        isonline_img = (ImageView) findViewById(R.id.is_online);
        recommended_by_count = (TextView) findViewById(R.id.recommended_by_count);
        recommended_by_list = (RecyclerView) findViewById(R.id.recommended_by_list);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        data = new ArrayList<>();
        setupViewPager(viewPager);
        recommended_by_list.setLayoutManager(gaggeredGridLayoutManager);
        adapter = new RecommendedAdapter(getApplicationContext(), data);
        recommended_by_list.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        FillDemoData();

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(tab_service, getString(R.string.str103));

        viewPagerAdapter.addFragment(teb_testimonials, getString(R.string.str104));

        servicecontroller = tab_service;
        testimonialscontroller = teb_testimonials;
        viewPager.setAdapter(viewPagerAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    /////////////////////////////////////////////////////////////////////////////////


    public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {


        private List<String> mValues;
        Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final ImageView img;

            public ViewHolder(View view) {
                super(view);

                img = (ImageView) view.findViewById(R.id.user_img);
            }

            @Override
            public String toString() {
                return "";
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position);
        }

        public RecommendedAdapter(Context context, List<String> items) {
            mValues = items;
            this.context = context;
        }

        private void updateData(List<String> newData) {
            mValues.clear();
            mValues.addAll(newData);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recommended_img, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            Picasso.with(context).load(mValues.get(position)).into(holder.img
            );
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }


}
