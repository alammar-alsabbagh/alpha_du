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


public class Notification {


    public static final String LOGTAG = "Nocker";
    public static final String DATABASE_NAME = "nocker_notification.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notification";
    public static final String COL_ID = "ID";
    public static final String COL_notification_type = "notification_type";
    public static final String COL_notfication_content = "notification_content";
    public static final String COL_notfication_status = "notification_status";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_notification_type + " INTEGER  , "
                    + COL_notfication_content + " TEXT  , "
                    + COL_notfication_status + " BOOLEAN   "
                  +" );";


    private static final String[] all = {COL_ID, COL_notification_type, COL_notfication_content, COL_notfication_status};

    private SQLiteDatabase db;

    private final Context context;

    private myDbHelper dbHelper;

    public Notification(Context _context) {
        context = _context;
        dbHelper = new myDbHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public Notification open() throws SQLException {

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

    public int updateEntry(long _rowIndex, Object _myObject) {
        String where = COL_ID + "=" + _rowIndex;
        ContentValues contentValues = new ContentValues();
        // TODO fill in the ContentValue based on the new object
        return db.update(TABLE_NAME, contentValues, where, null);
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