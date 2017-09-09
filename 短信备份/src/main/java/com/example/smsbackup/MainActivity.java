package com.example.smsbackup;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click(View view){
        int id = view.getId();
        switch (id){
            case R.id.backup:
                try {
                    XmlSerializer serializer = Xml.newSerializer();
                    File file = new File(getFilesDir().getPath(), "smsbackup.xml");
                    FileOutputStream fos = new FileOutputStream(file);
                    serializer.setOutput(fos,"utf-8");
                    //文档的声明，true是生成独立的xml文件
                    serializer.startDocument("utf-8",true);
                    serializer.startTag(null,"smss");

                    //访问短信数据库中的uri
                    Uri uri = Uri.parse("content://sms/");
                    //利用内容解析者来查询系统数据库
                    Cursor cursor = getContentResolver().query(uri,new String[]{"address","date","body"},null,null,null);

                    while (cursor.moveToNext()) {
                        String address = cursor.getString(0);
                        String date = cursor.getString(1);
                        String body = cursor.getString(2);

                        serializer.startTag(null, "sms");

                        serializer.startTag(null, "address");
                        serializer.text(address);
                        serializer.endTag(null, "address");

                        serializer.startTag(null, "date");
                        serializer.text(date);
                        serializer.endTag(null, "date");

                        serializer.startTag(null, "body");
                        serializer.text(body);
                        serializer.endTag(null, "body");

                        serializer.endTag(null, "sms");

                    }
                    serializer.endTag(null, "smss");
                    serializer.endDocument();
                    Toast.makeText(this, "备份成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "备份失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.restore:
                //Xml解析
                try {
                    String address = null;
                    String date = null;
                    String body = null;
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    File file = new File(getFilesDir().getPath(), "smsbackup.xml");
                    parser.setInput(new FileInputStream(file),"utf-8");
                    int eventType = parser.getEventType();
                    while(eventType!=XmlPullParser.END_DOCUMENT){
                        switch (eventType){
                            case XmlPullParser.START_TAG:
                                if("address".equals(parser.getName())){
                                    address =  parser.nextText();
                                }else if("date".equals(parser.getName())){
                                    date =  parser.nextText();
                                }else if("body".equals(parser.getName())){
                                    body =  parser.nextText();
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if("sms".equals(parser.getName())){
                                    //相当于插入操作
                                    Uri uri = Uri.parse("content://sms/");
                                    ContentValues values = new ContentValues();
                                    values.put("address",address);
                                    values.put("date",date);
                                    values.put("body",body);
                                    getContentResolver().insert(uri,values);
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    Toast.makeText(this, "还原成功", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "还原失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
