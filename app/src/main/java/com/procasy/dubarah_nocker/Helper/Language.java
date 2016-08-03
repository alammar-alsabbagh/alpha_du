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


public class Language {


    public static final String LOGTAG = "Nocker";
    public static final String DATABASE_NAME = "nocker_language.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "language";
    public static final String COL_ID = "ID";
    public static final String COL_language_name = "language_name";
    public static final String COL_language_code = "language_code";
    public static final String COL_language_Id = "language_id";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_language_name + " TEXT  , "
                    + COL_language_code + " TEXT  , "
                    + COL_language_Id + " varchar(10) );";


    private static final String[] all = {COL_ID, COL_language_name, COL_language_code, COL_language_Id};

    // Variable to hold the database instance
    private SQLiteDatabase db;
    private final Context context;
    // Database open/upgrade helper
    private myDbHelper dbHelper;

    // CONSTRUCTOR
    public Language(Context _context) {
        context = _context;

        dbHelper = new myDbHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    // OPEN
    public Language open() throws SQLException {

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
    public Cursor getSingleLanguage(String input)
    {
        Cursor c = db.rawQuery("SELECT language_id from language " +
                "where language_name = '"+input+"'", null);
        return c;
    }
    public Cursor filterLanguages(String input) {
        // Select All Query
        Cursor d = db.query(true, TABLE_NAME, all , COL_language_name + " LIKE ?",
                new String[] { input+"%" }, null, null, null,
                null);
        return d;
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