package com.android.upcomingguide.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shweta on 2/11/2017.
 */

public class DatabaseConnectivity {
    private final Context context;

    protected static DatabaseHelper DBHelper;
    protected SQLiteDatabase db;

    private static final String TAG = "DataBase Connectivity";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UpcomingGuide";

    public DatabaseConnectivity(Context context) {
        this.context = context;
        if (DBHelper == null) {
            System.out.println("Database Context Called");
            DBHelper = new DatabaseHelper(context);
        }
    }

    private static final String CART_TABLE_CREATE_QUERY = "CREATE TABLE IF NOT EXISTS "
            + DBCartAdapter.TABLE_NAME_MENU_ITEM
            + "("
            + DBCartAdapter.CART_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBCartAdapter.START_DATE
            + " VARCHAR , "
            + DBCartAdapter.END_DATE
            + " VARCHAR , "
            + DBCartAdapter.OBJ_TYPE
            + " VARCHAR, "
            + DBCartAdapter.NAME
            + " VARCHAR, "
            + DBCartAdapter.LOGIN_REQUIRED
            + " VARCHAR, "
            + DBCartAdapter.URL
            + " VARCHAR, "
            + DBCartAdapter.QUANTITY
            + " INTEGER, "
            + DBCartAdapter.ICON
            + " VARCHAR " +
            ")";



    protected class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(CART_TABLE_CREATE_QUERY);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            if (newVersion > oldVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + DBCartAdapter.TABLE_NAME_MENU_ITEM);
                onCreate(db);
            }
        }
    }
}
