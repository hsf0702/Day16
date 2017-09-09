package com.example.a25737.day16;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 25737 on 2017/9/6.
 */

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "itheima.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info( _id integer primary key autoincrement,name varchar(20),phone varchar(20),money varchar(20))");
        db.execSQL("insert into info('name','phone','money') values ('张三','123','1000')");
        db.execSQL("insert into info('name','phone','money') values ('李四','1234','2000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
