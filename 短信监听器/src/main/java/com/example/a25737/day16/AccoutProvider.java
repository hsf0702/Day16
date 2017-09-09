package com.example.a25737.day16;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class AccoutProvider extends ContentProvider {
    private MyHelper helper;

    public AccoutProvider() {
    }
    //匹配路径
    //匹配不上，默认此
    private static  final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //匹配成功
    private static final int QUERYSUCCESS = 10;
    private static final int INSERTSUCCESS = 20;
    private static final int DELETESUCCESS = 30;
    private static final int UPDATESUCESS = 40;



    //静态代码块---随着类的加载而加载，只加载一次
    static{
        //添加路径  参数1：清单文件配置的参数一致  参数2：子路径 参数3：匹配码
        sURIMatcher.addURI("com.example.a25737","query",QUERYSUCCESS);
        sURIMatcher.addURI("com.example.a25737","insert",INSERTSUCCESS);
        sURIMatcher.addURI("com.example.a25737","delete",DELETESUCCESS);
        sURIMatcher.addURI("com.example.a25737","update",UPDATESUCESS);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = sURIMatcher.match(uri);//匹配其他应用传来的匹配码,路径匹配成功
        if(code==DELETESUCCESS){
            SQLiteDatabase db = helper.getWritableDatabase();
            int info = db.delete("info", selection,selectionArgs);
            return info;
        }else{
            throw new IllegalArgumentException("路径匹配失败");
        }
    }

    @Override
    public String getType(Uri uri) {
        return "";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {//values 其实就是map
        int code = sURIMatcher.match(uri);//匹配其他应用传来的匹配码,路径匹配成功
        if(code==INSERTSUCCESS){
            SQLiteDatabase db = helper.getWritableDatabase();
            long info = db.insert("info", null, values);
            Uri uri1 = Uri.parse("com.example.a25737.insert/"+info);
            return uri1;
        }else{
            throw new IllegalArgumentException("路径匹配失败");
        }
    }

    @Override//先执行此方法
    public boolean onCreate() {
        helper =  new MyHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int code = sURIMatcher.match(uri);//匹配其他应用传来的匹配码,路径匹配成功
        if(code==QUERYSUCCESS){
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.query("info", projection, selection, selectionArgs, sortOrder, null, null);
//            while (cursor.moveToNext()){
//                String id = cursor.getString(0);
//            }不能自己查
            return cursor;
        }else{
            throw new IllegalArgumentException("路径匹配失败");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int code = sURIMatcher.match(uri);//匹配其他应用传来的匹配码,路径匹配成功
        if(code==UPDATESUCESS){
            SQLiteDatabase db = helper.getWritableDatabase();
            int info = db.update("info", values, selection,selectionArgs);
            return info;
        }else{
            throw new IllegalArgumentException("路径匹配失败");
        }
    }
}
