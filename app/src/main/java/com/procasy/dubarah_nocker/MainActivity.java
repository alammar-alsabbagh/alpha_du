package com.procasy.dubarah_nocker;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Activity.Nocker.EditProfileActivtiy;
import com.procasy.dubarah_nocker.Activity.Nocker.MyProfileActivity;
import com.procasy.dubarah_nocker.Fragments.AppointementsFragment;
import com.procasy.dubarah_nocker.Fragments.FragmentDrawerNocker;
import com.procasy.dubarah_nocker.Fragments.FragmentDrawerUser;
import com.procasy.dubarah_nocker.Fragments.MainFragment;
import com.procasy.dubarah_nocker.Fragments.MessagesFragment;
import com.procasy.dubarah_nocker.Fragments.NotificationsFragment;
import com.procasy.dubarah_nocker.Helper.Notification;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.Responses.InfoNockerResponse;
import com.procasy.dubarah_nocker.Services.LocationService;
import com.procasy.dubarah_nocker.gcm.GCMIntentService;

import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener, CommuncationChannel , BadgeInterface {

    DrawerLayout mDrawerLayout;
    LinearLayout notification_items;
    private Toolbar mtoolbar;
    private Context mContext;
    private static Button message, appoitements;
    private static Button notification;
    private ImageView drawer;
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    // Declaring a Location Manager
    protected LocationManager locationManager;
    APIinterface apiService;
    SessionManager sessionManager;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    Notification mNotification;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static int notificationCount = 0;
    private static int messagesCount = 0;
    private static int appointementCount = 0;


    TextView MessageBadge , AppointementBadge , NotificationsBadge ;
    static MainActivity ins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        ins = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        notification_items = (LinearLayout) mtoolbar.findViewById(R.id.main_notification_items);
        message = (Button) notification_items.findViewById(R.id.message1);
        notification = (Button) notification_items.findViewById(R.id.notification1);
        appoitements = (Button) notification_items.findViewById(R.id.aptmnts1);


        drawer = (ImageView) mtoolbar.findViewById(R.id.drawer_btn);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

updateNotification();

        int off = 0;
        try {
            off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if(off==0){
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(onGPS);
        }






        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("here");
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.container_body, new MessagesFragment()).addToBackStack("tag").commit();
            }
        });

        appoitements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.container_body, new AppointementsFragment()).addToBackStack("tag").commit();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("here");
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.container_body, new NotificationsFragment()).addToBackStack("tag").commit();
            }
        });

        sessionManager = new SessionManager(this);



        if (checkPlayServices()) {
            Log.e("register gcm : ", "GCM");
            registerGCM();
        }



        final ACProgressFlower dialog = new ACProgressFlower.Builder(MainActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Info..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<InfoNockerResponse> call = apiService.GetInfoNocker(sessionManager.getEmail(), sessionManager.getUDID());
        call.enqueue(new Callback<InfoNockerResponse>() {
            @Override
            public void onResponse(Call<InfoNockerResponse> call, Response<InfoNockerResponse> response) {
                //     System.out.println(response.body().getUser().toString());
                try {
                    sessionManager.setEmail(response.body().getUser().getUser_email());
                    sessionManager.setFName(response.body().getUser().getUser_fname());
                    sessionManager.setLName(response.body().getUser().getUser_lname());
                    sessionManager.setPP(response.body().getUser().getUser_img());
                    sessionManager.setAVG(response.body().getAvg_charge());
                    sessionManager.setKeyIsNocker(response.body().getUser().is_nocker());
                    Log.d("nocker_data", response.body().toString() + "");


                    Log.e("user_img", sessionManager.getPP() + " ff");

                    if (dialog.isShowing())
                        dialog.dismiss();
                } catch (NullPointerException ex) {
                    sessionManager.setEmail("");
                    sessionManager.setPassword("");
                    sessionManager.setLogin(false);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<InfoNockerResponse> call, Throwable t) {
                System.out.println("here 2" + t.toString());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("onrec", "success");

            }

        };


        setSupportActionBar(mtoolbar);
        if (sessionManager.getKeyNocker() == 1) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_navigation_drawer, new FragmentDrawerNocker()).commit();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_navigation_drawer, new FragmentDrawerUser()).commit();
        }
        setTitle("");
        mContext = getApplicationContext();
        marshmallowGPSPremissionCheck();

        Activity activity = getInstance();
        if (activity != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_body, new MainFragment()).commit();
        }

    }

    @Override
    protected void onResume(){
        System.out.println("Resuming");
    }

    public static MainActivity  getInstance(){
        return ins;
    }

    public void updateNotification() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                LinearLayout notification_items = (LinearLayout)mtoolbar.findViewById(R.id.main_notification_items);
                TextView NotificationsBadge = (TextView) notification_items.findViewById(R.id.badge_notification_1);
                mNotification = new Notification(getApplicationContext());
                mNotification.open();
                Cursor cursor = mNotification.getNumberOfUnReadNotifications();
                notificationCount = cursor.getCount();
                if(notificationCount != 0) {
                    NotificationsBadge.setVisibility(View.VISIBLE);
                    NotificationsBadge.setText(notificationCount + "");
                }
                else
                {
                    NotificationsBadge.setVisibility(View.INVISIBLE);
                }
                mNotification.close();
            }
        });
    }

    public static void incrementMessage(){
        System.out.println("hahahahaah");
    }

    public static void incrementAppointement(){
        System.out.println("hahahahaah");
    }

    private void subscribeToGlobalTopic() {
        Intent intent = new Intent(this, GCMIntentService.class);
        intent.putExtra(GCMIntentService.KEY, GCMIntentService.SUBSCRIBE);
        //intent.putExtra(GCMIntentService.TOPIC, Config.TOPIC_GLOBAL);
        startService(intent);
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("GCM", "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }


    // starting the service to register with GCM
    private void registerGCM() {
        Intent intent = new Intent(this, GCMIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
            startService(new Intent(this, LocationService.class));
        }

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                System.out.println("here");
            }
        }
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    private void marshmallowGPSPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getApplicationContext().checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && getApplicationContext().checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    5);
        } else {

            //     Log.e("Loction", getLocation().toString());
            startService(new Intent(this, LocationService.class));
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        // return latitude
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        // return longitude
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location currentLocation) {
// TODO Auto-generated method stub
        this.location = currentLocation;
        getLatitude();
        getLongitude();

    }

    @Override
    public void onProviderDisabled(String provider) {
// TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
// TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
// TODO Auto-generated method stub
    }

    @Override
    public void setCommunication(String msg) {
        FragmentManager fragmentManager = getSupportFragmentManager();
       // DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        switch (msg) {
            case "activateSub":
                fragmentManager.beginTransaction().add(R.id.container_body, new MainFragment()).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case "myShop":
                startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case "promotion":
                fragmentManager.beginTransaction().add(R.id.container_body, new MainFragment()).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case "help":
                fragmentManager.beginTransaction().add(R.id.container_body, new MainFragment()).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                sessionManager.setEmail("");
                sessionManager.setPassword("");
                sessionManager.setLogin(false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case "settings":
                fragmentManager.beginTransaction().add(R.id.container_body, new MainFragment()).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);

                break;
            case "editProfile":
                startActivity(new Intent(getApplicationContext(), EditProfileActivtiy.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);

                break;
            case "message":
                fragmentManager.beginTransaction().replace(R.id.container_body, new MessagesFragment()).addToBackStack("tag").commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case "appoitement":
                fragmentManager.beginTransaction().replace(R.id.container_body, new AppointementsFragment()).addToBackStack("tag").commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case "notification":
                fragmentManager.beginTransaction().add(R.id.container_body, new NotificationsFragment()).addToBackStack("tag").commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
    }

/*
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
*/


    @Override
    public void onBackPressed() {
        //  System.out.println(getFragmentManager().getBackStackEntryCount());

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
        {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0 ){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else
                super.onBackPressed();




        }

    }

    @Override
    public void setBadgeCount(int count) {
        System.out.println("hello world");
    }
}
