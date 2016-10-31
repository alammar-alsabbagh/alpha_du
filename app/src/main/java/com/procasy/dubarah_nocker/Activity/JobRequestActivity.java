package com.procasy.dubarah_nocker.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.Model.Responses.NormalResponse;
import com.procasy.dubarah_nocker.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobRequestActivity extends AppCompatActivity {

    LinearLayout quota;
    ScrollView scrollView;
    Button accept;
    Button pass,send;
    TextView skill_name , call_time,call_date,call_address,call_desc , date,time ;
    EditText money,desc;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    ImageView play_bnt;
    String   hr_voice_record;
    /**
     * remain false till media is not completed, inside OnCompletionListener make it true.
     */
    private boolean intialStage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_request);

        quota = (LinearLayout) findViewById(R.id.quota);
        quota.setVisibility(View.GONE);
        accept = (Button) findViewById(R.id.accept);
        pass = (Button) findViewById(R.id.pass);
        send = (Button) findViewById(R.id.send);
        scrollView = (ScrollView) findViewById(R.id.scroll);
        play_bnt = (ImageView) findViewById(R.id.play_sound);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        money = (EditText) findViewById(R.id.money);
        desc = (EditText) findViewById(R.id.desc);


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quota.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        scrollView.smoothScrollTo(0, scrollView.getMaxScrollAmount() + send.getHeight());

                    }
                }, 500);
            }
        });

        skill_name = (TextView) findViewById(R.id.skill_name);
        call_time = (TextView) findViewById(R.id.call_time);
        call_date = (TextView) findViewById(R.id.call_date);
        call_address = (TextView) findViewById(R.id.call_address);
        call_desc = (TextView) findViewById(R.id.call_desc);

        ArrayList<ImageView> imageViewArrayList = new ArrayList<>();
        imageViewArrayList.add((ImageView) findViewById(R.id.img1));
        imageViewArrayList.add((ImageView) findViewById(R.id.img2));
        imageViewArrayList.add((ImageView) findViewById(R.id.img3));

        final String hr_id = getIntent().getExtras().getString("hr_id");
        String hr_user_id = getIntent().getExtras().getString("hr_user_id");
        String hr_description = getIntent().getExtras().getString("hr_description");
        String hr_est_date = getIntent().getExtras().getString("hr_est_date");
        String hr_est_time = getIntent().getExtras().getString("hr_est_time");
        String hr_skill_id = getIntent().getExtras().getString("hr_skill_id");
        String hr_ua_id = getIntent().getExtras().getString("hr_ua_id");
        hr_voice_record = getIntent().getExtras().getString("hr_voice_record");
        String hr_language = getIntent().getExtras().getString("hr_language");
        String hr_lat = getIntent().getExtras().getString("hr_lat");
        String hr_lon = getIntent().getExtras().getString("hr_lon");
        String hr_address = getIntent().getExtras().getString("hr_address");
        ArrayList<String> array = getIntent().getExtras().getStringArrayList("album");
        for (int i = 0; i < array.size(); i++) {
            Picasso.with(getApplicationContext()).load(ApiClass.Pic_Base_URL + array.get(i)).into(imageViewArrayList.get(i));
        }

        Skills skills = new Skills(getApplicationContext());
        skills.open();
        Cursor cr = skills.getSkillNameByID(hr_skill_id);
        cr.moveToFirst();
        skill_name.setText(cr.getString(0));

        skills.close();

        call_time.setText(hr_est_time);
        call_date.setText(hr_est_date);
        call_address.setText(hr_address);
        call_desc.setText(hr_description);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        play_bnt.setOnClickListener(pausePlay);


        final SessionManager sessionManager = new SessionManager(getApplicationContext());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ACProgressFlower dialog = new ACProgressFlower.Builder(JobRequestActivity.this)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Responding ... ")
                        .fadeColor(Color.DKGRAY).build();
                dialog.show();

                APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
                Call<NormalResponse> call = apiService.accept_qoute_from_nocker(sessionManager.getEmail(), sessionManager.getUDID(), "1", Double.parseDouble(money.getText().toString()), date.getText().toString(), time.getText().toString(), desc.getText().toString(), Integer.parseInt(hr_id));
                call.enqueue(new Callback<NormalResponse>() {
                    @Override
                    public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<NormalResponse> call, Throwable t) {
                        System.out.println("faaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaak");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                    }

                });


            }
        });


        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ACProgressFlower dialog = new ACProgressFlower.Builder(JobRequestActivity.this)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Getting Info..")
                        .fadeColor(Color.DKGRAY).build();
                dialog.show();
                APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
                Call<NormalResponse> call = apiService.refuse_qoute_from_nocker(sessionManager.getEmail(), sessionManager.getUDID(), "2", Integer.parseInt(hr_id));
                call.enqueue(new Callback<NormalResponse>() {
                    @Override
                    public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                        dialog.dismiss();
                        System.out.println("faaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaak");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<NormalResponse> call, Throwable t) {
                        dialog.dismiss();
                    }

                });
            }
        });
    }



    private View.OnClickListener pausePlay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub

            if (!playPause) {
                play_bnt.setBackgroundResource(R.drawable.stop);
                if (intialStage)
                    new Player().execute(ApiClass.Pic_Base_URL+hr_voice_record);
                else {
                    if (!mediaPlayer.isPlaying())
                        mediaPlayer.start();
                }
                playPause = true;
            } else {
                play_bnt.setBackgroundResource(R.drawable.record_voice);
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                playPause = false;
            }
        }
    };
    /**
     * preparing mediaplayer will take sometime to buffer the content so prepare it inside the background thread and starting it on UI thread.
     * @author piyush
     *
     */

    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {

                mediaPlayer.setDataSource(params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause=false;
                        play_bnt.setBackgroundResource(R.drawable.record_voice);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            mediaPlayer.start();

            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(JobRequestActivity.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
