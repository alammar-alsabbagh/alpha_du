package com.procasy.dubarah_nocker.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Adapter.NavigationDrawerAdapter;
import com.procasy.dubarah_nocker.CommuncationChannel;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.NavDrawerItem;
import com.procasy.dubarah_nocker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Omar on 7/14/2016.
 */
public class FragmentDrawerUser extends Fragment implements View.OnClickListener {

    private static String TAG = FragmentDrawerUser.class.getSimpleName();
    CommuncationChannel mCommChListner = null;

    public TextView fullName, avg_txt_title, title_happy_customers_title,
            edit_profile, promotion_txt, myshop,
            setting, help;
    public CircleImageView profileImage;
    TextView nockerScore;
    TextView averageCharge;
    TextView happyCustomers;
    Button drawer_btn, message, notification;

    ImageView Messages, Notifcations;
    Button boost;
    TableRow editProfile, promotion, activateSubscription, myShop, Settings, Help;
    SessionManager sessionManager;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.activateSubscription:
                mCommChListner.setCommunication(getString(R.string.str109));
                break;
            case R.id.myShop:
                mCommChListner.setCommunication(getString(R.string.str110));

                break;
            case R.id.promotion:
                mCommChListner.setCommunication(getString(R.string.str111));

                break;
            case R.id.help:
                mCommChListner.setCommunication(getString(R.string.str112));

                break;
            case R.id.settings:
                mCommChListner.setCommunication(getString(R.string.str113));

                break;
            case R.id.editProfile:
                mCommChListner.setCommunication(getString(R.string.str114));

                break;
        }
    }


    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        mCommChListner = (CommuncationChannel) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer_user, container, false);
        //recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        editProfile = (TableRow) layout.findViewById(R.id.editProfile);
        promotion = (TableRow) layout.findViewById(R.id.promotion);
        myShop = (TableRow) layout.findViewById(R.id.myShop);
        Settings = (TableRow) layout.findViewById(R.id.settings);
        Help = (TableRow) layout.findViewById(R.id.help);
        activateSubscription = (TableRow) layout.findViewById(R.id.activateSubscription);
        fullName = (TextView) layout.findViewById(R.id.fullName);
        profileImage = (CircleImageView) layout.findViewById(R.id.profile_image);
        averageCharge = (TextView) layout.findViewById(R.id.averageCharge);
        happyCustomers = (TextView) layout.findViewById(R.id.happyCustomers);


        fullName = (TextView) layout.findViewById(R.id.fullName);
        profileImage = (CircleImageView) layout.findViewById(R.id.profile_image);
        averageCharge = (TextView) layout.findViewById(R.id.averageCharge);
        happyCustomers = (TextView) layout.findViewById(R.id.happyCustomers);
        avg_txt_title = (TextView) layout.findViewById(R.id.title_avarge_charge);
        title_happy_customers_title = (TextView) layout.findViewById(R.id.title_happy_customers);
        edit_profile = (TextView) layout.findViewById(R.id.edit_profile_txt);
        promotion_txt = (TextView) layout.findViewById(R.id.promotion_txt);
        myshop = (TextView) layout.findViewById(R.id.myShop_txt);
        setting = (TextView) layout.findViewById(R.id.settings_txt);
        help = (TextView) layout.findViewById(R.id.help_txt);

        //activate_sub = (TextView)layout.findViewById(R.id.activateSubscription_txt);
        Typeface typface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font1.ttf");
        fullName.setTypeface(typface);
        averageCharge.setTypeface(typface);
        avg_txt_title.setTypeface(typface);
        happyCustomers.setTypeface(typface);
        title_happy_customers_title.setTypeface(typface);
        edit_profile.setTypeface(typface);
        promotion_txt.setTypeface(typface);
        //activate_sub.setTypeface(typface);
        myshop.setTypeface(typface);
        setting.setTypeface(typface);
        help.setTypeface(typface);
//        nockerScore.setTypeface(typface);


        editProfile.setOnClickListener(this);
        promotion.setOnClickListener(this);
        myShop.setOnClickListener(this);
        Settings.setOnClickListener(this);
        Help.setOnClickListener(this);
        sessionManager = new SessionManager(getActivity());
        fullName.setText(sessionManager.getFName() + " " + sessionManager.getLName());
        if (!sessionManager.getPP().equals(""))
            Picasso.with(getActivity()).load(sessionManager.getPP()).placeholder(R.drawable.drawable_photo).into(profileImage);
        averageCharge.setText(sessionManager.getAVG() + "");
        adapter = new NavigationDrawerAdapter(getActivity(), getData());


        return layout;
    }


}
