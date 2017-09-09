package com.example.contentobserver;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = Uri.parse("content://sms/");
        getContentResolver().registerContentObserver(uri,true,new MyContentObserver(new Handler()));
    }

    class MyContentObserver extends ContentObserver{

        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override//观察短信的数据库
        public void onChange(boolean selfChange) {
            System.out.println("短信数据库发生了改变");
            Uri uri = Uri.parse("content://sms/");
            Cursor cursor = getContentResolver().query(uri, new String[]{"body","address"}, null, null, null);
            cursor.moveToLast();
            String body = cursor.getString(0);
            String address = cursor.getString(1);
            System.out.println("---"+body+"---"+address);
        }
    }
}
