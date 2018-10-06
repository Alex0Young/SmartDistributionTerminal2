package com.hyphenate.chatuidemo.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.db.MyDataBaseHelper;
import com.hyphenate.easeui.widget.EaseAlertDialog;

public class NewNoticeActivity extends AppCompatActivity {

    private EditText noticeNameEditText;
    private ProgressDialog progressDialog;
    private EditText noticeEditText;

    private MyDataBaseHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_notice);
        noticeNameEditText = (EditText) findViewById(R.id.edit_notice_name);
        noticeEditText = (EditText) findViewById(R.id.edit_notice_introduction);

        dbHelper = new MyDataBaseHelper(this, "Notice.db", null, 2);

    }

    public void save(View v) {
        String notice = noticeEditText.getText().toString();
        String name = noticeNameEditText.getText().toString();
        if (TextUtils.isEmpty(notice)) {
            new EaseAlertDialog(this, R.string.notice_empty).show();
        } else {
            // select from contact list
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("notice",notice);
            db.insert("NOTICE",null,values);
            Log.d("NewNoticeActivity", "save: "+name+"  "+notice);
            startActivityForResult(new Intent(this, MainActivity.class).putExtra("groupName", name), 0);
        }
    }

    public void back(View view) {
        finish();
    }
}
