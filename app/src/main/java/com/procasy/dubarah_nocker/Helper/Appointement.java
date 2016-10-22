package com.procasy.dubarah_nocker.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Omar on 10/21/2016.
 */


public class Appointement {


    public static final String LOGTAG = "Nocker";
    public static final String DATABASE_NAME = "nocker_appointement.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "appointement";
    public static final String COL_appointement_id = "appointement_id";
    public static final String COL_appointement_date = "appointement_date";
    public static final String COL_appointement_call_id = "appointement_call_id";
    public static final String COL_appointement_time = "appointement_time";
    public static final String COL_appointement_ammount = "appointement_ammount";
    public static final String COL_appointement_nocker_name = "appointement_nocker_name";
    public static final String COL_appointement_quota_id = "appointement_quota_id";
    public static final String COL_appointement_skill_name = "appointement_skill_name";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" + COL_appointement_id + " INTEGER , "
                    + COL_appointement_date + " INTEGER  , "
                    + COL_appointement_call_id + " TEXT  , "
                    + COL_appointement_time + " BOOLEAN  , "
                    + COL_appointement_ammount + " TEXT  , "
                    + COL_appointement_nocker_name + " TEXT  , "
                    + COL_appointement_quota_id + " TEXT  , "
                    + COL_appointement_skill_name + " TEXT   "
                    +" );";


    private static final String[] all = {COL_appointement_id,COL_appointement_date,COL_appointement_call_id,COL_appointement_time,COL_appointement_ammount,COL_appointement_nocker_name,COL_appointement_quota_id,COL_appointement_skill_name};

    private SQLiteDatabase db;

    private final Context context;

    private myDbHelper dbHelper;

    public Appointement(Context _context) {
        context = _context;
        dbHelper = new myDbHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public Appointement open() throws SQLException {

        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertEntry(ContentValues contentValues) {
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public boolean removeEntry(Integer _rowIndex) {
        return db.delete(TABLE_NAME, COL_appointement_id +
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


    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class myDbHelper extends SQLiteOpenHelper {

        // CONSTR
        public myDbHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
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