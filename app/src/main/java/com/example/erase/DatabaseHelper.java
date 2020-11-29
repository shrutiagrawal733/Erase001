package com.example.erase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "Products";
    public static final String TABLE_NAME = "Items";
    public static final String ID = "ID";
    public static final String USER = "User";
    public static final String C_D = "Column_date";
    public static final String Points = "Points";
    public static final String Dropbox = "DropBox";
    public static final String Gadget = "Gadget";


    public DatabaseHelper(Context context){
        super (context, DB_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME +"("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ USER + " TEXT NOT NULL, " +  Dropbox + " TEXT NOT NULL, "+ Gadget+ " TEXT NOT NULL, " + Points + " INTEGER NOT NULL, "+C_D+ " TEXT DEFAULT CURRENT_DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Items");
        onCreate(sqLiteDatabase);
    }
}
