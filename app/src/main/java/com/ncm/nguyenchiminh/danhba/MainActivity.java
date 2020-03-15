package com.ncm.nguyenchiminh.danhba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ncm.nguyenchiminh.danhba.adapter.ContactAdapter;
import com.ncm.nguyenchiminh.danhba.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> arrayContact;
    private EditText edtName;
    private EditText edtNum;
    private RadioButton rbNam;
    private RadioButton rbNu;
    private Button btnAdd;
    private ListView lvDanhBa;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWiget();
        arrayContact = new ArrayList<>();
        adapter = new ContactAdapter(this, R.layout.item_contact_listview, arrayContact);
        lvDanhBa.setAdapter(adapter);
        checkPermission();
        lvDanhBa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogConfirm(position);
            }
        });
    }

    public void setWiget() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtNum = (EditText) findViewById(R.id.edt_num);
        rbNam = (RadioButton) findViewById(R.id.rb_nam);
        rbNu = (RadioButton) findViewById(R.id.rb_nu);
        btnAdd = (Button) findViewById(R.id.btn_add);
        lvDanhBa = (ListView) findViewById(R.id.lv_danhba);
    }

    public void addContact(View view) {
        if (view.getId() == R.id.btn_add) {
            String name = edtName.getText().toString().trim();
            String number = edtNum.getText().toString().trim();
            boolean isMale = true;
            if (rbNam.isChecked()) {
                isMale = true;
            } else {
                isMale = false;
            }
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tên và số điện thoại", Toast.LENGTH_SHORT).show();
            } else {
                Contact contact = new Contact(isMale, name, number);
                arrayContact.add(contact);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void showDialogConfirm(final int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        Button btnCall = (Button) dialog.findViewById(R.id.btn_call);
        Button btnText = (Button) dialog.findViewById(R.id.btn_text);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(position);
            }
        });
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentText(position);
            }
        });
        dialog.show();
    }

    public void intentCall(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + arrayContact.get(position).getmNumber().toString().trim()));
        startActivity(intent);
    }

    public void intentText(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + arrayContact.get(position).getmNumber().toString().trim()));
        startActivity(intent);
    }

    public void checkPermission() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS
        };
        List<String> listPermissionNeeded = new ArrayList<>();
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(permission);
            }
        }
        if(!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),1);
        }
    }
}
