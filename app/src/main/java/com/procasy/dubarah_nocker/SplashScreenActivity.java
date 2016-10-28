package com.procasy.dubarah_nocker;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import android.util.Log;
import android.util.LongSparseArray;

import com.crashlytics.android.Crashlytics;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Activity.Teaser.WhatIsNockerAbout;
import com.procasy.dubarah_nocker.Helper.ContactsDb;
import com.procasy.dubarah_nocker.Helper.Language;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.Model.Responses.AllSkillsAndLanguageResponse;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    ContactsDb mContactsDb;
    Skills skills;
    Language languages;


    public List<AddressBookContact> getContacts()
    {
        List<AddressBookContact> list = new LinkedList<AddressBookContact>();
        LongSparseArray<AddressBookContact> array = new LongSparseArray<AddressBookContact>();
        long start = System.currentTimeMillis();

        String[] projection = {
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Contactables.DATA,
                ContactsContract.CommonDataKinds.Contactables.TYPE,
        };
        String selection = ContactsContract.Data.MIMETYPE + " in (?, ?)";
        String[] selectionArgs = {
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        };
        String sortOrder = ContactsContract.Contacts.SORT_KEY_ALTERNATIVE;

        Uri uri = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI;

        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        final int mimeTypeIdx = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE);
        final int idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID);
        final int nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        final int dataIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA);
        final int typeIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.TYPE);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idIdx);
            AddressBookContact addressBookContact = array.get(id);
            if (addressBookContact == null) {
                addressBookContact = new AddressBookContact(id, cursor.getString(nameIdx), getResources());
                array.put(id, addressBookContact);
                list.add(addressBookContact);
            }
            int type = cursor.getInt(typeIdx);
            String data = cursor.getString(dataIdx);
            String mimeType = cursor.getString(mimeTypeIdx);
            if (mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                // mimeType == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                addressBookContact.addEmail(type, data);
            } else {
                // mimeType == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                addressBookContact.addPhone(type, data);
            }
        }
        long ms = System.currentTimeMillis() - start;
        cursor.close();

        int i = 1;
        for (AddressBookContact addressBookContact : list) {
            Log.d("FAST", "AddressBookContact #" + i++ + ": " + addressBookContact.toString(true));
        }
        return list;
    }


    private class DownloadWebPageTask extends AsyncTask<Void, Void, List<AddressBookContact>> {
        @Override
        protected  List<AddressBookContact> doInBackground(Void... s) {
            return getContacts();

        }

        @Override
        protected void onPostExecute(List<AddressBookContact> result) {
            for (AddressBookContact obj:result) {
                System.out.println(obj.toString());
            }

            mContactsDb.open();
            Log.e("remove state ", mContactsDb.removeAllEntry() + "");
            try {
                for (int i = 0; i < result.size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(mContactsDb.COL_contact_id, result.get(i).getId());
                    contentValues.put(mContactsDb.COL_contact_name, result.get(i).getName());
                    try {
                        LongSparseArray<String> emails = result.get(i).getEmails();
                        String emailString ="";
                        for (int j=1;j<emails.size()+1;j++)
                        {
                            emailString += emails.get(j)+";";
                        }
                        contentValues.put(mContactsDb.COL_contact_email,emailString);
                    }catch (NullPointerException e)
                    {
                        contentValues.put(mContactsDb.COL_contact_email, "");

                    }
                    contentValues.put(mContactsDb.COL_contact_address,"");
                    try {
                        contentValues.put(mContactsDb.COL_contact_photo, "");
                    }catch (NullPointerException e)
                    {
                        contentValues.put(mContactsDb.COL_contact_photo, "");
                    }
                    try {
                        contentValues.put(mContactsDb.COL_contact_phone, result.get(i).getPhones().valueAt(0));
                    }catch (NullPointerException e)
                    {
                        contentValues.put(mContactsDb.COL_contact_phone, "");
                    }
                    long state = mContactsDb.insertEntry(contentValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mContactsDb.close();
            }

            APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
            //// TODO: 7/30/2016  dont forget to modify session
            Call<AllSkillsAndLanguageResponse> call = apiService.GetAllSkills("alafandiomar@gmail.com","352910072441300");
            call.enqueue(new Callback<AllSkillsAndLanguageResponse>() {
                @Override
                public void onResponse(Call<AllSkillsAndLanguageResponse> call, Response<AllSkillsAndLanguageResponse> response) {

                    skills.open();
                    Log.e("remove state ", skills.removeAllEntry() + "");

                    try {
                        for (int i = 0; i < response.body().getAllSkills().size(); i++) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(Skills.COL_skillname, response.body().getAllSkills().get(i).getSkill_name());
                            contentValues.put(Skills.COL_is_hidden, response.body().getAllSkills().get(i).getIs_hidden());
                            contentValues.put(Skills.COL_SKILL_ID, response.body().getAllSkills().get(i).getSkill_id());
                            long state = skills.insertEntry(contentValues);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        skills.close();

                        languages.open();
                        Log.e("remove state ", languages.removeAllEntry() + "");
                        try {
                            for (int i = 0; i < response.body().getAllLanguage().size(); i++) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(Language.COL_language_name, response.body().getAllLanguage().get(i).getLang_name());
                                contentValues.put(Language.COL_language_Id, response.body().getAllLanguage().get(i).getlang_id());
                                long state = languages.insertEntry(contentValues);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                            languages.close();



                        }

                    }



                }

                @Override
                public void onFailure(Call<AllSkillsAndLanguageResponse> call, Throwable t) {
                    System.out.println("ERROR 2" + t.toString());
                }

            });

        }
    }
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    private class thread extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            marshmallowContactsPremissionCheck();
            return 1;
        }
        @Override
        protected void onPostExecute(Integer s) {
            startActivity(new Intent(getApplicationContext(),WhatIsNockerAbout.class));
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        TwitterAuthConfig authConfig =  new TwitterAuthConfig("pvkAmYZntmsS4V4jqyTsqtahR", "0r5IUmH38PlbcRsIRim6AcpjJwqaXubdJr9CuqFvPLstlxuGyf");
        Fabric.with(this, new TwitterCore(authConfig));
        setContentView(R.layout.activity_splash_screen);
        mContactsDb = new ContactsDb(this);
        skills = new Skills(this);
        languages = new Language(this);
        printKeyHash(SplashScreenActivity.this);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            //finish();
        }
        else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    thread t1 = new thread();
                    t1.execute();
                }
            }, 1500);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    class AddressBookContact {
        private long id;
        private Resources res;
        private String name;
        private LongSparseArray<String> emails;
        private LongSparseArray<String> phones;

        AddressBookContact(long id, String name, Resources res) {
            this.id = id;
            this.name = name;
            this.res = res;
        }

        public long getId() {
            return id;
        }

        public Resources getRes() {
            return res;
        }

        public String getName() {
            return name;
        }

        public LongSparseArray<String> getEmails() {
            return emails;
        }

        public LongSparseArray<String> getPhones() {
            return phones;
        }

        @Override
        public String toString() {
            return toString(false);
        }

        public String toString(boolean rich) {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            if (rich) {
                builder.append("id: ").append(Long.toString(id))
                        .append(", name: ").append("\u001b[1m").append(name).append("\u001b[0m");
            } else {
                builder.append(name);
            }

            if (phones != null) {
                builder.append("\n\tphones: ");
                for (int i = 0; i < phones.size(); i++) {
                    int type = (int) phones.keyAt(i);
                    builder.append(ContactsContract.CommonDataKinds.Phone.getTypeLabel(res, type, ""))
                            .append(": ")
                            .append(phones.valueAt(i));
                    if (i + 1 < phones.size()) {
                        builder.append(", ");
                    }
                }
            }

            if (emails != null) {
                builder.append("\n\temails: ");
                for (int i = 0; i < emails.size(); i++) {
                    int type = (int) emails.keyAt(i);
                    builder.append(ContactsContract.CommonDataKinds.Email.getTypeLabel(res, type, ""))
                            .append(": ")
                            .append(emails.valueAt(i));
                    if (i + 1 < emails.size()) {
                        builder.append(", ");
                    }
                }
            }
            return builder.toString();
        }

        public void addEmail(int type, String address) {
            if (emails == null) {
                emails = new LongSparseArray<String>();
            }
            emails.put(type, address);
        }

        public void addPhone(int type, String number) {
            if (phones == null) {
                phones = new LongSparseArray<String>();
            }
            phones.put(type, number);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            DownloadWebPageTask task = new DownloadWebPageTask();
            task.execute();
        }
    }
    private void marshmallowContactsPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getApplicationContext().checkSelfPermission(
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                && getApplicationContext().checkSelfPermission(
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS},
                    5);
        } else {
            DownloadWebPageTask task = new DownloadWebPageTask();
            task.execute();
        }
    }

}
