package com.example.erase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String db, String gadget, int point_earned) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.USER, name);
        contentValue.put(DatabaseHelper.Dropbox, db);
        contentValue.put(DatabaseHelper.Gadget, gadget);
        contentValue.put(DatabaseHelper.Points, point_earned);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch(String user) {
        String q = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.USER + " ='" + user+ "' " ;
        Cursor cursor = database.rawQuery(q, null);
        if (cursor != null) {
            cursor.moveToLast();
        }
        return cursor;
    }

    /*public int update(long id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USER, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(String name) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.USER + "=" + name, null);
    }*/

}
