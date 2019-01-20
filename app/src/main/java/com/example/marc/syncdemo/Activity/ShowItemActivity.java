package com.example.marc.syncdemo.Activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.marc.syncdemo.Adapter.InfoAdapter;
import com.example.marc.syncdemo.Database.MyDatabaseHelper;
import com.example.marc.syncdemo.Model.Info;
import com.example.marc.syncdemo.R;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowItemActivity extends AppCompatActivity{

    private List<Info> infoList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;

    @BindView(R.id.lv_info)
    ListView lv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        //初始化绑定ButterKinife
        ButterKnife.bind(this);

        initInfo(); //获取信息数据

        InfoAdapter adapter = new InfoAdapter(ShowItemActivity.this,
                R.layout.adapter_item,infoList);
        lv_info.setAdapter(adapter);

    }

    //获取信息数据
    private void initInfo(){
        dbHelper = new MyDatabaseHelper(this,"List.db",null,1);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("list",null,null,null,
                null,null,null);

        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                Integer state = cursor.getInt(cursor.getColumnIndex("status"));
                Timestamp anchor = change(cursor.getString(cursor.getColumnIndex("anchor")));
                Info info = new Info(id,name,phone,state,anchor);
                infoList.add(info);
            }while (cursor.moveToNext());

        }
        cursor.close();

    }

    //String转化成TimeStamp
    Timestamp change(String time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return Timestamp.valueOf(time);//转换时间字符串为Timestamp
    }
}
