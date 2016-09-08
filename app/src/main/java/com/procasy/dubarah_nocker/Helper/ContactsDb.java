package com.procasy.dubarah_nocker.Helper;

/**
 * Created by DELL on 29/07/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ContactsDb {


    public static final String LOGTAG = "Nocker";
    public static final String DATABASE_NAME = "nocker_contact.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "contacts";
    public static final String COL_ID = "ID";
    public static final String COL_contact_id = "contact_id";
    public static final String COL_contact_name = "contact_name";
    public static final String COL_contact_email = "contact_email";
    public static final String COL_contact_address = "contact_address";
    public static final String COL_contact_phone = "contact_phone";
    public static final String COL_contact_photo = "contact_photo";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_contact_id + " INTEGER  , "
                    + COL_contact_name + " TEXT  , "
                    + COL_contact_email + " TEXT  , "
                    + COL_contact_address + " TEXT  , "
                    + COL_contact_phone + " TEXT  , "
                    + COL_contact_photo + " TEXT   "
                +" );";


    private static final String[] all = {COL_ID, COL_contact_id, COL_contact_name, COL_contact_email,COL_contact_address,COL_contact_phone,COL_contact_photo};

    // Variable to hold the database instance
    private SQLiteDatabase db;
    private final Context context;
    // Database open/upgrade helper
    private myDbHelper dbHelper;

    // CONSTRUCTOR
    public ContactsDb(Context _context) {
        context = _context;

        dbHelper = new myDbHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    // OPEN
    public ContactsDb open() throws SQLException {

        // Log.e("ahmad", "open");
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // CLOSE
    public void close() {
        db.close();
    }

    // insert
    public long insertEntry(ContentValues contentValues) {

        //  Log.e("ahmad", "insert");
        return db.insert(TABLE_NAME, null, contentValues);
    }

    // remove
    public boolean removeEntry(Integer _rowIndex) {
        return db.delete(TABLE_NAME, COL_ID +
                "=" + _rowIndex, null) > 0;
    }

    public boolean removeAllEntry() {
        return db.delete(TABLE_NAME, null
                , null) > 0;
    }


    public Cursor getAllEntries() {
        Cursor d = db.query(TABLE_NAME, all,
                null, null, null, null, null);

        return d;
    }

    public Object getEntry(long _rowIndex) {

        // TODO Return a cursor to a row from the database and
        // use the values to populate an instance of MyObject
        return null;
    }

    public int updateEntry(long _rowIndex, Object _myObject) {
        String where = COL_ID + "=" + _rowIndex;
        ContentValues contentValues = new ContentValues();
        // TODO fill in the ContentValue based on the new object
        return db.update(TABLE_NAME, contentValues, where, null);
    }

    public Cursor getSingleContact(String input)
    {
        Cursor c = db.rawQuery("SELECT * from contacts " +
                "where contact_name = '"+input+"'", null);
        return c;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class myDbHelper extends SQLiteOpenHelper {

        // CONSTR
        public myDbHelper(Context context, String name,
                          CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in
        // disk and the helper class needs
        // to create a new one.
        @Override
        public void onCreate(SQLiteDatabase _db) {
            Log.i(LOGTAG, "  Data created  ");
            _db.execSQL(TABLE_CREATE);
            // TODO Auto-generated method stub

        }

        // Called when there is a database version mismatch meaning that
        // the version of the database on disk needs to be upgraded to
        // the current version.
        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {

            // Upgrade the existing database to conform to the new version.
            // Multiple previous versions can be handled by comparing
            // _oldVersion and _newVersion values.
            // The simplest case is to drop the old table and create a
            // new one.
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            // Create a new one.
            onCreate(_db);
            // TODO Auto-generated method stub

        }

    }

}