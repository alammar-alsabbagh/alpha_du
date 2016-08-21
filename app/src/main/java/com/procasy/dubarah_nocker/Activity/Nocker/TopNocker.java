package com.procasy.dubarah_nocker.Activity.Nocker;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Fragments.FragmentService;
import com.procasy.dubarah_nocker.Fragments.FragmentTestimonials;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopNocker extends AppCompatActivity {

    CircleImageView user_img;

    TextView user_main_job, user_name, isonline, recommended_by_count;

    ImageView isonline_img;

    RecyclerView recommended_by_list;


    TabLayout tabLayout;

    ViewPager viewPager;


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

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentService(), "Service");
        adapter.addFragment(new FragmentTestimonials(), "Testimonials");
        viewPager.setAdapter(adapter);
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

}
