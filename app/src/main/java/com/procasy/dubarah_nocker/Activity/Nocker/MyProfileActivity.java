package com.procasy.dubarah_nocker.Activity.Nocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Fragments.AskForHelpFragment;
import com.procasy.dubarah_nocker.Fragments.FragmentAbout;
import com.procasy.dubarah_nocker.Fragments.FragmentService;
import com.procasy.dubarah_nocker.Fragments.FragmentTestimonials;
import com.procasy.dubarah_nocker.Fragments.NearByNockersFragment;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

public class MyProfileActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        loadBackdrop();

        TextView About = (TextView) LayoutInflater.from(this).inflate(R.layout.tablayout_item, null);
        About.setText("About");
        tabLayout.getTabAt(0).setCustomView(About);

        TextView Services = (TextView) LayoutInflater.from(this).inflate(R.layout.tablayout_item, null);
        Services.setText("Services");
        tabLayout.getTabAt(1).setCustomView(Services);

        TextView Testimonials = (TextView) LayoutInflater.from(this).inflate(R.layout.tablayout_item, null);
        Testimonials.setText("Testimonials");
        tabLayout.getTabAt(2).setCustomView(Testimonials);

    }

    private void loadBackdrop() {
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.backdrop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAbout(), "About");
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

