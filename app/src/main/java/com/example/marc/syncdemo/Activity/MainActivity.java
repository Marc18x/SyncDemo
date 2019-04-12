package com.example.marc.syncdemo.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.marc.syncdemo.Database.MyDatabaseHelper;
import com.example.marc.syncdemo.R;

import org.litepal.tablemanager.Connector;

import java.sql.Timestamp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.button_create)
    Button button_create;

    @BindView(R.id.button_insert)
    Button button_insert;

    @BindView(R.id.button_update)
    Button button_update;

    @BindView(R.id.button_show)
    Button button_show;

    @BindView(R.id.button_delete)
    Button button_delete;

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化绑定ButterKinife
        ButterKnife.bind(this);


        //创建数据库
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自动创建数据库
                Connector.getDatabase();
                Toast.makeText(getApplicationContext(),"LitePal数据库创建成功",Toast.LENGTH_SHORT).show();
            }
        });

        //模拟写入数据
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //跳转到插入数据页面
                Intent intent = new Intent(MainActivity.this,AddInfoActivity.class);
                startActivity(intent);



            }
        });

        //显示所有数据
        button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到页面显示所有数据
                Intent intent = new Intent(MainActivity.this,ShowItemActivity.class);
                startActivity(intent);
            }
        });

        //更新数据库
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //更新数据库操作----当前version版本号高于本地已存版本号时自动调用更新数据库
                //执行更新操作的同事，覆盖原有的数据， 不用担心数据丢失的问题
                Connector.getDatabase();
            }
        });

        //还原数据库
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //删除数据库
            }
        });
    }
}
