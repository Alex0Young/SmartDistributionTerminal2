package com.hyphenate.chatuidemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDataBaseHelper extends SQLiteOpenHelper {


    public static final String CREATE_NOTICE = "create table NOTICE ("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "notice text)";

    public static final String CREATE_TIME = "create table TIME ("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "time text)";

    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTICE);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
        db.execSQL(CREATE_TIME);
        Toast.makeText(mContext, "Time success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("drop table if exists NOTICE");
        db.execSQL("drop table if exists TIME");
        onCreate(db);
    }
}
