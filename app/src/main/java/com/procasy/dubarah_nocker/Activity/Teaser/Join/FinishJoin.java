package com.procasy.dubarah_nocker.Activity.Teaser.Join;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Activity.Teaser.JoinOrRecommend;
import com.procasy.dubarah_nocker.Helper.ContactsDb;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.Responses.CheckResponse;
import com.procasy.dubarah_nocker.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishJoin extends AppCompatActivity {


     ACProgressFlower dialog;
    SessionManager sessionManager;
    ContactsDb mContactsDb;
    TextView t1,t2,person_name,person_skill,online_text,period,free,terms_of_use,privacy_policy,t10,t11;
    ImageView pp;
    LinearLayout next_btn;
    LinearLayout Whole_btn;
    LinearLayout back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_join);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        Whole_btn = (LinearLayout) findViewById(R.id.wholething);
        person_name = (TextView) findViewById(R.id.person_name);
        person_skill = (TextView) findViewById(R.id.person_skill);
        online_text = (TextView) findViewById(R.id.online_text);
        period = (TextView) findViewById(R.id.period);
        free = (TextView) findViewById(R.id.free);
        terms_of_use = (TextView) findViewById(R.id.terms_of_use);
        privacy_policy = (TextView) findViewById(R.id.privacy_policy);
        t10 = (TextView) findViewById(R.id.t10);
        t11 = (TextView) findViewById(R.id.t11);
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        t1.setTypeface(typface);
        t2.setTypeface(typface);
        person_name.setTypeface(typface);
        person_skill.setTypeface(typface);
        online_text.setTypeface(typface);
        period.setTypeface(typface);
        free.setTypeface(typface);
        terms_of_use.setTypeface(typface);
        privacy_policy.setTypeface(typface);
        t10.setTypeface(typface);
        t11.setTypeface(typface);
        pp = (ImageView) findViewById(R.id.profilePic);
        sessionManager = new SessionManager(this);
        person_name.setText(sessionManager.getFName()+" "+sessionManager.getLName());
        person_skill.setText(sessionManager.getKey_ur_skill());


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicking","click111");

                Whole_btn.performClick();
                Whole_btn.setPressed(true);
                Whole_btn.invalidate();
                // delay completion till animation completes
                Whole_btn.postDelayed(new Runnable() {  //delay button
                    public void run() {
                        Whole_btn.setPressed(false);
                        Whole_btn.invalidate();

                        final ACProgressFlower dialog = new ACProgressFlower.Builder(FinishJoin.this)
                                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                .themeColor(Color.WHITE)
                                .text("Submitting Your Data ...")
                                .fadeColor(Color.DKGRAY).build();
                        dialog.show();
                        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
                        //// TODO: 7/30/2016  dont forget to modify session
                        Call<CheckResponse> call = apiService.speedy_sign(sessionManager.getKey_his_name(), sessionManager.getKey_his_skill(),sessionManager.getKey_his_phone(),sessionManager.getKey_his_email(),sessionManager.getKey_his_language(),sessionManager.getKey_his_rating(),sessionManager.getKey_his_rating_text(),sessionManager.getKEY_his_subscription(),sessionManager.getFName(),sessionManager.getLName(),sessionManager.getKey_Country(),sessionManager.getKey_City(),sessionManager.getKEY_Province(),sessionManager.getKEY_Birthyear(),sessionManager.getEmail(),sessionManager.getKEY_Phonenumber(),sessionManager.getKey_ur_skill(),sessionManager.getKEY_my_subscription(),sessionManager.getKEY_language(),sessionManager.getKEY_gender() );
                        call.enqueue(new Callback<CheckResponse>() {
                            @Override
                            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),JoinOrRecommend.class));
                                Log.e("Response","success");
                                finish();
                            }

                            @Override
                            public void onFailure(Call<CheckResponse> call, Throwable t) {
                                Log.e("Response","faild");
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),JoinOrRecommend.class));
                                finish();
                            }


                        });


                    }
                }, 400);


            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Whole_btn.performClick();
                                            Whole_btn.setPressed(true);
                                            Whole_btn.invalidate();
                                            // delay completion till animation completes
                                            Whole_btn.postDelayed(new Runnable() {  //delay button
                                                public void run() {
                                                    Whole_btn.setPressed(false);
                                                    Whole_btn.invalidate();
                                                    finish();
                                                    //any other associated action
                                                }
                                            }, 400);
                                        }
                                    }
        );
    }

    private Bitmap getPhoto(int contactId) {
        Bitmap photo = null;
        final String[] projection = new String[]{ContactsContract.Contacts.PHOTO_ID};

        final Cursor contact = managedQuery(ContactsContract.Contacts.CONTENT_URI, projection, ContactsContract.Contacts._ID + "=?", new String[]{String.valueOf(contactId)}, null);

        if (contact.moveToFirst()) {
            final String photoId = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
            if (photoId != null) {
                photo = getBitmap(photoId);
            } else {
                photo = null;
            }
        }
        contact.close();

        return photo;
    }

    private Bitmap getBitmap(String photoId) {
        final Cursor photo = managedQuery(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, ContactsContract.RawContacts.Data._ID + "=?", new String[]{photoId}, null);

        final Bitmap photoBitmap;
        if (photo.moveToFirst()) {
            byte[] photoBlob = photo.getBlob(photo.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
            photoBitmap = BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.length);
        } else {
            photoBitmap = null;
        }
        photo.close();
        return photoBitmap;
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

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
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
