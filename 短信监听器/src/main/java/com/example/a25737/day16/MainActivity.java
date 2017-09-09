package com.example.a25737.day16;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        Cursor cursor = db.query("info", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String money = cursor.getString(3);
            System.out.println("----"+id+"----"+name+"----"+phone+"----"+money);
            Toast.makeText(this, "info表创建完毕", Toast.LENGTH_SHORT).show();
        }


    }
}
