package com.procasy.dubarah_nocker.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Helper.Language;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.Model.Responses.AllSkillsAndLanguageResponse;
import com.procasy.dubarah_nocker.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskForHelpActivity extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String SKILL_NAME = "skill_name";


    ImageView img1, img2, img3;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ImageView take, browse;
    ImageView reord, start, stop;
    String FilePath = new String("");
    List<String> allimages = new ArrayList<>();
    private MediaRecorder myAudioRecorder;
    ProgressBar progressBar;
    private String outputFile = "";
    Button where, request;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    List<String> skills_list;
    List<String> language_list;
    private int PLACE_PICKER_REQUEST = 10;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SessionManager sessionManager;
    private Skills mskills;
    private Language mlanguage;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AutoCompleteTextView actv, language;
    private OnFragmentInteractionListener mListener;

    public AskForHelpActivity() {
        // Required empty public constructor
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        Log.e("PermissionsResult", "success " + requestCode);

        if (requestCode == 5 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();

            try {

                myAudioRecorder = new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(outputFile);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ask_for_help);
        sessionManager = new SessionManager(this);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);

        request = (Button) findViewById(R.id.request);
        take = (ImageView) findViewById(R.id.take_picture);
        browse = (ImageView)findViewById(R.id.browser_apicture);
        language = (AutoCompleteTextView)findViewById(R.id.language);
        actv = (AutoCompleteTextView) findViewById(R.id.skills_auto_complete);

        actv.setText(getIntent().getStringExtra(SKILL_NAME));

        mlanguage = new Language(this);
        where = (Button)findViewById(R.id.where);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        reord = (ImageView) findViewById(R.id.record_sound);
        start = (ImageView) findViewById(R.id.start_record);
        stop = (ImageView) findViewById(R.id.stop_record);

        stop.setEnabled(false);
        start.setEnabled(false);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadFile(null);

            }
        });

        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            requestPermissions(
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        requestPermissions(
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                } else {
                    cameraIntent();
                }


            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {


                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.


                            requestPermissions(
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    0);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        requestPermissions(
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    }
                } else {

                    galleryIntent();
                }

            }
        });

        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.
                            requestPermissions(
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        requestPermissions(
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                } else {
                    cameraIntent();
                }
            }
        });

        reord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    try {
                        requestPermissions(
                                new String[]{Manifest.permission.RECORD_AUDIO,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                52);


                        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + System.currentTimeMillis() + ".3gp";

                        myAudioRecorder = new MediaRecorder();
                        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                        myAudioRecorder.setOutputFile(outputFile);

                        myAudioRecorder.prepare();
                        myAudioRecorder.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.VISIBLE);
                reord.setEnabled(false);
                stop.setEnabled(true);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                stop.setEnabled(false);
                start.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), getString(R.string.str107), Toast.LENGTH_LONG).show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), getString(R.string.str107), Toast.LENGTH_LONG).show();
            }
        });

        where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    startActivityForResult(builder.build(AskForHelpActivity.this), PLACE_PICKER_REQUEST);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                //startActivityForResult(new Intent(getActivity(), MapsActivity.class),1);

            }
        });
        mskills = new Skills(this);

        mskills.open();
        Log.e("loool ", mskills.getSkillIdByName(getIntent().getStringExtra(SKILL_NAME)).moveToFirst() + "");
        Log.e("loool ",mskills.getSkillIdByName(getIntent().getStringExtra(SKILL_NAME)).getString(0)+"");

        mskills.close();

        skills_list = new ArrayList<>();
        language_list = new ArrayList<>();

        mskills.open();
        Cursor cursor = mskills.getAllEntries();
        cursor.moveToFirst();

        try {
            skills_list.add(cursor.getString(1));

            while (cursor.moveToNext()) {
                skills_list.add(cursor.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mskills.close();

        }

        mlanguage.open();
        Cursor cursor_language = mlanguage.getAllEntries();
        cursor_language.moveToFirst();

        try {

            language_list.add(cursor_language.getString(1));
            while (cursor_language.moveToNext()) {
                language_list.add(cursor_language.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mlanguage.close();
        }


        Log.e(" list size ", skills_list.size() + "");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, skills_list);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, language_list);
        language.setAdapter(adapter2);

        final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(getString(R.string.str105))
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        Log.e("AskForHelp", "Success");

        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<AllSkillsAndLanguageResponse> call = apiService.GetAllSkills(sessionManager.getEmail(), sessionManager.getUDID());
        call.enqueue(new Callback<AllSkillsAndLanguageResponse>() {
                         @Override
                         public void onResponse(Call<AllSkillsAndLanguageResponse> call, Response<AllSkillsAndLanguageResponse> response) {

                             mskills.open();
                             mlanguage.open();
                             try {
                                 if (mskills.getAllEntries().getCount() != response.body().getAllSkills().size()) {
                                     Log.e("remove state ", mskills.removeAllEntry() + "");
                                     skills_list.clear();

                                     try {

                                         for (int i = 0; i < response.body().getAllSkills().size(); i++) {
                                             ContentValues contentValues = new ContentValues();
                                             contentValues.put(Skills.COL_skillname, response.body().getAllSkills().get(i).getSkill_name());
                                             contentValues.put(Skills.COL_is_hidden, response.body().getAllSkills().get(i).getIs_hidden());
                                             contentValues.put(Skills.COL_SKILL_ID, response.body().getAllSkills().get(i).getSkill_id());
                                             long state = mskills.insertEntry(contentValues);
                                             Log.e("insert state ", state + "");
                                             skills_list.add(response.body().getAllSkills().get(i).getSkill_name());
                                         }
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }
                                 }
                             } catch (NullPointerException ex) {
                                 ex.printStackTrace();
                             }
                             try {
                                 if (mlanguage.getAllEntries().getCount() != response.body().getAllLanguage().size()) {
                                     mlanguage.removeAllEntry();
                                     language_list.clear();
                                     for (int i = 0; i < response.body().getAllLanguage().size(); i++) {
                                         ContentValues contentValues = new ContentValues();
                                         contentValues.put(Language.COL_language_code, response.body().getAllLanguage().get(i).getLang_code());
                                         contentValues.put(Language.COL_language_name, response.body().getAllLanguage().get(i).getLang_name());
                                         contentValues.put(Language.COL_language_Id, response.body().getAllLanguage().get(i).getlang_id());

                                         long state = mlanguage.insertEntry(contentValues);
                                         Log.e("insert state language ", state + "");
                                         language_list.add(response.body().getAllLanguage().get(i).getLang_name());
                                     }

                                 }
                             } catch (NullPointerException ex) {
                                 ex.printStackTrace();
                             }

                             actv.setAdapter(adapter);
                             language.setAdapter(adapter2);
                             mskills.close();
                             mlanguage.close();

                             if (dialog.isShowing())
                                 dialog.dismiss();
                         }

                         @Override
                         public void onFailure(Call<AllSkillsAndLanguageResponse> call, Throwable t) {
                             System.out.println("ERROR 2" + t.toString());
                             if (dialog.isShowing())
                                 dialog.dismiss();
                         }

                     }

        );

    }


    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    private void galleryIntent() {

/*
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent,1);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }*/


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }


    public static String getDataColumn(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                final Uri imageUri = data.getData();


                FilePath = getPath(this, imageUri);
                allimages.add(getPath(this, imageUri));

                System.out.println(FilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.e("AllImages", allimages.toString());


        if (img1.getTag().equals("empty")) {
            Log.e("Empty", "Success");
            img1.setTag("fill");
            img1.setImageBitmap(bm);
            return;
        }

        if (img2.getTag().equals("empty")) {
            img2.setTag("fill");
            Log.e("Empty", "Success");
            img2.setImageBitmap(bm);
            return;
        }
        if (img3.getTag().equals("empty")) {
            img3.setTag("fill");
            Log.e("Empty", "Success");
            img3.setImageBitmap(bm);
            return;
        }


    }


    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    private void uploadFile(Uri fileUri) {


        File voice_file;


        File img1_file = null, img2_file = null, img3_file = null;

        RequestBody voice_request_file,
                img1_request_file,
                img2_request_file,
                img3_request_file;

        MultipartBody.Part body_voice, body_img1, body_img2, body_img3;

        if (outputFile.equals("")) {
            voice_file = null;
        } else {
            voice_file = new File(outputFile);
        }


        if (allimages.size() == 0) {

            img1_file = null;//new File("");
            img2_file = null;//new File("");
            img3_file = null;//new File("");

        }

        if (allimages.size() == 1) {
            img1_file = new File(allimages.get(0));
            img2_file = null;//new File("");
            img3_file = null;//new File("");
        }

        if (allimages.size() == 2) {
            img1_file = new File(allimages.get(0));
            img2_file = new File(allimages.get(1));
            img3_file = null;//new File("");
        }

        if (allimages.size() == 3) {
            img1_file = new File(allimages.get(0));
            img2_file = new File(allimages.get(1));
            img3_file = new File(allimages.get(2));
        }


        if (voice_file != null) {
            voice_request_file = RequestBody.create(MediaType.parse("multipart/form-data"), voice_file);
            body_voice =
                    MultipartBody.Part.createFormData("hr_voice_record", voice_file.getName(), voice_request_file);
            Log.e("Voice", "Success");

        } else {
            voice_request_file = null;
            body_voice = null;
            Log.e("Voice", "Faild");

        }

        if (img1_file != null) {
            img1_request_file = RequestBody.create(MediaType.parse("multipart/form-data"), img1_file);
            body_img1 =
                    MultipartBody.Part.createFormData("img1", img1_file.getName(), img1_request_file);

        } else {
            img1_request_file = null;
            body_img1 = null;
        }
        if (img2_file != null) {
            img2_request_file = RequestBody.create(MediaType.parse("multipart/form-data"), img2_file);
            body_img2 =
                    MultipartBody.Part.createFormData("img2", img1_file.getName(), img2_request_file);
        } else {
            img2_request_file = null;
            body_img2 = null;
        }
        if (img3_file != null) {
            img3_request_file = RequestBody.create(MediaType.parse("multipart/form-data"), img3_file);
            body_img3 =
                    MultipartBody.Part.createFormData("img3", img3_file.getName(), img3_request_file);
        } else {
            img3_request_file = null;
            body_img3 = null;
        }

        RequestBody str_email = RequestBody.create(MediaType.parse("multipart/form-data"), sessionManager.getEmail());

        RequestBody str_udid = RequestBody.create(MediaType.parse("multipart/form-data"), sessionManager.getUDID());

        RequestBody str_skill_id = RequestBody.create(MediaType.parse("multipart/form-data"), "5");

        RequestBody str_language = RequestBody.create(MediaType.parse("multipart/form-data"), "5");

        RequestBody str_descr = RequestBody.create(MediaType.parse("multipart/form-data"), "5dkjfskldfjklsd");

        RequestBody str_est_date = RequestBody.create(MediaType.parse("multipart/form-data"), "2013-03-03");

        RequestBody str_est_time = RequestBody.create(MediaType.parse("multipart/form-data"), "10:34:34");


        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        Call<ResponseBody> call = apiService.AskForHelp(
                body_voice
                , str_email,
                str_udid,
                str_skill_id, str_language,
                str_descr
                , str_est_date, str_est_time,
                body_img1,
                body_img2, body_img3);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    Log.e("response", "successs " + response.body().string());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("here 2" + t.toString());

            }

        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (requestCode == PLACE_PICKER_REQUEST) {
                    Place place = PlacePicker.getPlace(data, this);
                    //String toastMsg = String.format("Place: %s", place.getName());
                    where.setText(place.getName());

                }
                //Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FilePath = destination.getAbsolutePath();
        allimages.add(destination.getAbsolutePath());
        System.out.println(FilePath);
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("AllImages", allimages.toString());

        if (img1.getTag().equals("empty")) {
            img1.setImageBitmap(thumbnail);
            img1.setTag("fill");
            Log.e("Empty", "success");
            return;
        }

        if (img2.getTag().equals("empty")) {
            img2.setImageBitmap(thumbnail);
            img2.setTag("fill");
            Log.e("Empty", "success");
            return;
        }
        if (img3.getTag().equals("empty")) {
            img3.setTag("fill");
            img3.setImageBitmap(thumbnail);
            Log.e("Empty", "success");
            return;
        }


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
