package com.procasy.dubarah_nocker.Activity.Teaser.Join;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Helper.ContactsDb;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.R;


public class BroadCastJoinActivity extends AppCompatActivity {

    LinearLayout next_btn;
    LinearLayout back_btn;
    LinearLayout Whole_btn;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11;
    ContactsDb mContactsDb;
    EditText editText;
    ImageView pp;
    Spinner emailSpinner;
    ImageView six,three,one;
    Boolean b1,b2,b3;
    LinearLayout emailSpinnerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_join);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        Whole_btn = (LinearLayout) findViewById(R.id.wholething);
        emailSpinner = (Spinner) findViewById(R.id.spinner);
        pp = (ImageView) findViewById(R.id.profilePic);
        six = (ImageView) findViewById(R.id.six);
        three = (ImageView) findViewById(R.id.three);
        one = (ImageView) findViewById(R.id.one);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        t7 = (TextView) findViewById(R.id.t7);
        t8 = (TextView) findViewById(R.id.t8);
        t9 = (TextView) findViewById(R.id.t9);
        t10 = (TextView) findViewById(R.id.t10);
        t11 = (TextView) findViewById(R.id.t11);
        editText = (EditText) findViewById(R.id.email);
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/font3.ttf");

        t1.setTypeface(typface);
        t2.setTypeface(bold);
        t3.setTypeface(bold);
        t4.setTypeface(typface);
        t5.setTypeface(typface);
        t6.setTypeface(typface);
        t7.setTypeface(bold);
        t8.setTypeface(typface);
        editText.setTypeface(typface);
        final SessionManager sessionManager = new SessionManager(this);
        t2.setText(new String("Your"));
        t7.setText(sessionManager.getFName()+" "+sessionManager.getLName());
        t8.setText(sessionManager.getKey_ur_skill());
        t5.setText(sessionManager.getKey_City());
editText.setText(sessionManager.getEmail());
        next_btn.setOnClickListener(new View.OnClickListener() {
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
                        startActivity(new Intent(getApplicationContext(), FinishJoin.class));
                        finish();
                        //any other associated action
                    }
                }, 400);
                // .8secs delay time

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
        b1 = false;
        b2 = false;
        b3 = false;

        six.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b1=true;
                        b2=false;
                        b3=false;
                        sessionManager.setKEY_my_subscription("6");
                        six.setImageResource(R.drawable.sixmonthschoosen);
                        three.setImageResource(R.drawable.threemonths);
                        one.setImageResource(R.drawable.onemonth);
                    }
                }
        );

        three.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b1=false;
                        b2=true;
                        b3=false;
                        sessionManager.setKEY_my_subscription("3");
                        six.setImageResource(R.drawable.sixmonths);
                        three.setImageResource(R.drawable.threemonthschosen);
                        one.setImageResource(R.drawable.onemonth);
                    }
                }
        );

        one.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b1=false;
                        b2=false;
                        b3=true;
                        sessionManager.setKEY_my_subscription("1");
                        six.setImageResource(R.drawable.sixmonths);
                        three.setImageResource(R.drawable.threemonths);
                        one.setImageResource(R.drawable.onemonthchoosen);
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

