package com.procasy.dubarah_nocker.Activity.Nocker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.AboutController;
import com.procasy.dubarah_nocker.Fragments.FavFragment;
import com.procasy.dubarah_nocker.Fragments.FragmentAbout;
import com.procasy.dubarah_nocker.Fragments.FragmentService;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.Responses.UserInfoResponse;
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

public class MyProfileActivity extends AppCompatActivity {


    public static final String NOCKER_EMAIL = "nocker_email";
    public static final String NOCKER_NAME = "nocker_name";

    TestimonialsController testimonialsController;
    CircleImageView imguser;
    AboutController aboutController;
    SkillsController skillsController;
    TextView avg_charge;
    Context mContext;
    FragmentAbout tab1 = new FragmentAbout();
    FavFragment tab3 = new FavFragment();
    FragmentService tab2 = new FragmentService();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar mtoolbar;
    SessionManager sessionManager;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        avg_charge = (TextView) findViewById(R.id.avg_charge);
        imguser = (CircleImageView) findViewById(R.id.user_img);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        sessionManager = new SessionManager(this);
        setupViewPager(viewPager);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setTitle(sessionManager.getFName() + sessionManager.getLName());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //  Connect To server

        final ACProgressFlower dialog = new ACProgressFlower.Builder(MyProfileActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Info..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        mContext = this;

        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<UserInfoResponse> call = apiService.GetUserInfo(sessionManager.getEmail());
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {

                Log.e("response", "success " + response.body().getUser().getUser_img());
                Picasso.with(getApplicationContext())
                        .load(sessionManager.getPP())
                        .placeholder(R.drawable.drawable_photo)
                        .error(R.drawable.profile)
                        .into(imguser);

                Log.e("tab1", tab1.isVisible() + "");
                Log.e("tab2", tab2.isVisible() + "");
                Log.e("tab3", tab3.isVisible() + "");


                avg_charge.setText(response.body().getAvg_charge() + "");
                skillsController.onAdapterUpdated(response.body().getSkills());
//                testimonialsController.UpdateAdapter(response.body().getTestimonials());
                Log.e("fname", response.body().getUser().getUser_fname());

                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                System.out.println("here 2" + t.toString());
                Toast.makeText(getApplicationContext(), "Request Time Out , please try again.", Toast.LENGTH_LONG).show();

                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });
        //
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
        Services.setText("Assigments");
        tabLayout.getTabAt(1).setCustomView(Services);

        TextView Testimonials = (TextView) LayoutInflater.from(this).inflate(R.layout.tablayout_item, null);
        Testimonials.setText("Fav List");
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

        aboutController = tab1;
        adapter.addFragment(tab1, "About");


        skillsController = tab2;
        adapter.addFragment(tab2, "Service");


        //testimonialsController = tab3;
        adapter.addFragment(tab3, "Testimonials");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

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

