package com.procasy.dubarah_nocker.Activity.SignUpActivities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.Model.UserRegistrationModel;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.AndroidMultiPartEntity;
import com.shawnlin.preferencesmanager.PreferencesManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PhotoSignUp extends AppCompatActivity {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    CircleImageView circleImageView;
    ImageView browse, take;
    LinearLayout btn_next;
    String FilePath = new String("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_sign_up);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        browse = (ImageView) findViewById(R.id.browse_picture);
        take = (ImageView) findViewById(R.id.take_picture);
        btn_next = (LinearLayout) findViewById(R.id.next_btn);
        new PreferencesManager(this)
                .setName("user")
                .init();
        final APIinterface apiService =
                ApiClass.getClient().create(APIinterface.class);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FilePath.equals("")) {


                }
                else
                {
                    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                    File file = new File(FilePath);
                    RequestBody requestBody = RequestBody.create(MEDIA_TYPE_PNG, file);
                    SignUpRequest();
                }
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(PhotoSignUp.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoSignUp.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(PhotoSignUp.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    0);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        ActivityCompat.requestPermissions(PhotoSignUp.this,
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
                    if (ContextCompat.checkSelfPermission(PhotoSignUp.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoSignUp.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(PhotoSignUp.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        ActivityCompat.requestPermissions(PhotoSignUp.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                } else {
                    cameraIntent();
                }
            }
        });
    }




    public void SignUpRequest() {
        final ProgressDialog pDialog = new ProgressDialog(PhotoSignUp.this);
        pDialog.setMessage("Signing up ... ");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "signup";
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClass.BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            int status = object.optInt("status");
                            if (status == 1) {
                               if(!FilePath.equals(""))
                                    new UploadFileToServer().execute();
                            } else {
                                System.out.println(object.getJSONArray("message"));
                                AlertDialog.Builder alertdialog = new AlertDialog.Builder(PhotoSignUp.this);
                                alertdialog.setMessage(object.getJSONArray("message").getString(0));
                                alertdialog.setTitle("Fail");
                                alertdialog.setPositiveButton("Ok", null);
                                alertdialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        pDialog.hide();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String firstName=PreferencesManager.getString("firstName");
                String lastName=PreferencesManager.getString("lastName");
                String country=PreferencesManager.getString("country");
                String city=PreferencesManager.getString("city");
                String region=PreferencesManager.getString("region");
                String birthDate=PreferencesManager.getString("birthDate");
                String address1=PreferencesManager.getString("address1");
                String postalCode=PreferencesManager.getString("postalCode");
                String address2=PreferencesManager.getString("address2");
                String email=PreferencesManager.getString("email");
                String password=PreferencesManager.getString("password");
                String phoneNumber=PreferencesManager.getString("phoneNumber");
                UserRegistrationModel userRegistrationModel =  new UserRegistrationModel(firstName,lastName,email,phoneNumber,birthDate,password,"other");

                JsonObject payerReg = new JsonObject();
                payerReg.addProperty("ua_address_name",address1);
                payerReg.addProperty("ua_lat","aas22");
                payerReg.addProperty("ua_lon","aas22");
                payerReg.addProperty("ua_country",country);
                payerReg.addProperty("ua_state",region);
                payerReg.addProperty("ua_city",city);
                payerReg.addProperty("ua_region",region);
                payerReg.addProperty("ua_zip_code",postalCode);
                payerReg.addProperty("ua_postal_code",postalCode);
                payerReg.addProperty("ua_street_name","aas22");
                payerReg.addProperty("ua_street_number","");
                payerReg.addProperty("ua_premise","");


                params.put("user_fname", firstName);
                params.put("user_lname", lastName);
                params.put("user_email", email);
                params.put("user_password", password);
                params.put("user_phone", phoneNumber);
                params.put("user_birthday", birthDate);
                params.put("user_gender", "other");
                params.put("address1",payerReg.toString());

                System.out.println(params.toString());
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cameraIntent();
        } else if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            galleryIntent();
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

        circleImageView.setImageBitmap(thumbnail);
    }
    private class UploadFileToServer extends AsyncTask<Integer, Integer, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressDialog = new ProgressDialog(PhotoSignUp.this);
            progressDialog.show();
            super.onPreExecute();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible

        }

        @Override
        protected String doInBackground(Integer... params) {
            return uploadFile(FilePath);
        }

        @SuppressWarnings("deprecation")
        private String uploadFile(String file_url) {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ApiClass.BASE_URL+"update_user_img");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {
                            @Override
                            public void transferred(long num) {

                            }
                        });

                File sourceFile = new File(file_url);
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                System.out.println(new FileBody(sourceFile).toString());
                // Extra parameters if you want to pass to server
                entity.addPart("user_email",new StringBody(PreferencesManager.getString("email")));
                entity.addPart("user_password",new StringBody(PreferencesManager.getString("password")));

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("ResponseUpload", "Response from server: " + result);
            progressDialog.dismiss();
            // showing the server response in an alert dialog

            super.onPostExecute(result);
        }

    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                final Uri imageUri = data.getData();
                FilePath = getPath(getApplicationContext(), imageUri);
                System.out.println(FilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        circleImageView.setImageBitmap(bm);
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