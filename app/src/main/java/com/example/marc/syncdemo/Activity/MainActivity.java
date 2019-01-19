package com.example.marc.syncdemo.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.marc.syncdemo.Database.MyDatabaseHelper;
import com.example.marc.syncdemo.R;

import java.sql.Timestamp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.button_create)
    Button button_create;

    @BindView(R.id.button_insert)
    Button button_insert;

    @BindView(R.id.button_select)
    Button button_select;

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化绑定ButterKinife
        ButterKnife.bind(this);
        //版本号确定数据库迭代
        dbHelper = new MyDatabaseHelper(this,"List2.db",null,3);

        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name","Jack");
                values.put("phone","11111111111");
                values.put("status",0);
                db.insert("list2",null,values);
                values.clear();
                Toast.makeText(getApplicationContext(),"导入数据成功",Toast.LENGTH_SHORT).show();

            }
        });

        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
    }
}
