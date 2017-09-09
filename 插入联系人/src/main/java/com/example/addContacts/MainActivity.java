package com.example.addContacts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_phone;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name =  (EditText) findViewById(R.id.et_name);
        et_phone =  (EditText) findViewById(R.id.et_phone);
        et_email =  (EditText) findViewById(R.id.et_email);
    }
    public void click(View view){
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String email = et_email.getText().toString().trim();

        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        ContentValues values = new ContentValues();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int count = cursor.getCount();
        int contact_id = count+1;
        values.put("contact_id",contact_id);
        getContentResolver().insert(uri,values);

        ContentValues nameValues = new ContentValues();
        nameValues.put("data1",name);
        nameValues.put("raw_contact_id",contact_id);
        nameValues.put("mimetype","vnd.android.cursor.item/name");
        getContentResolver().insert(dataUri,nameValues);

        ContentValues phoneValues = new ContentValues();
        phoneValues.put("data1",phone);
        phoneValues.put("raw_contact_id",contact_id);
        phoneValues.put("mimetype","vnd.android.cursor.item/phone_v2");
        getContentResolver().insert(dataUri,phoneValues);

        ContentValues emailValues = new ContentValues();
        emailValues.put("data1",email);
        emailValues.put("raw_contact_id",contact_id);
        emailValues.put("mimetype","vnd.android.cursor.item/email_v2");
        getContentResolver().insert(dataUri,emailValues);
    }
}
