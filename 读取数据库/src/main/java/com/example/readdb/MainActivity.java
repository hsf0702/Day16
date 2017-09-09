package com.example.readdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void click(View view){
//        MyHelper myHelper = new MyHelper(this);不能写open helper方法
//        SQLiteDatabase db = myHelper.getWritableDatabase();
//        SQLiteDatabase db = SQLiteDatabase.openDatabase("data/data/com.example.a25737.day16/database/itheima.db",null,SQLiteDatabase.OPEN_READWRITE);

        int vid = view.getId();
        switch (vid) {
            case R.id.add:
                Uri uri = Uri.parse("content://com.example.a25737/insert");
                ContentValues values = new ContentValues();
                values.put("name", "王五");
                values.put("phone", "888");
                values.put("money", "9000");
                Uri uri1 = getContentResolver().insert(uri, values);
                System.out.println("uri:" + uri1);
                break;
            case R.id.delete:
                Uri uri2 = Uri.parse("content://com.example.a25737/delete");
                int result = getContentResolver().delete(uri2, "name=?", new String[]{"王五"});
                Toast.makeText(this, "删除了"+result+"行", Toast.LENGTH_SHORT).show();
                break;
            case R.id.update:
                Uri uri3 = Uri.parse("content://com.example.a25737/update");
                ContentValues values1 = new ContentValues();
                values1.put("money","5000");
                int result1 = getContentResolver().update(uri3,values1, "name=?", new String[]{"王五"});
                Toast.makeText(this, "更新了"+result1+"行", Toast.LENGTH_SHORT).show();
                break;
            case R.id.query:
                Uri uri4 = Uri.parse("content://com.example.a25737/query");
                Cursor cursor = getContentResolver().query(uri4, null, null, null, null);
//        Cursor cursor = db.query("info", null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String phone = cursor.getString(2);
                    String money = cursor.getString(3);
                    System.out.println("----" + id + "----" + name + "----" + phone + "----" + money);
                    Toast.makeText(this, "info表创建完毕", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }
}
