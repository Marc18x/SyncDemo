package com.example.marc.syncdemo.Activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
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
    SwipeMenuListView lv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        //初始化绑定ButterKinife
        ButterKnife.bind(this);

        initInfo(); //获取信息数据

        final InfoAdapter adapter = new InfoAdapter(ShowItemActivity.this,
                R.layout.adapter_item,infoList);

        lv_info.setAdapter(adapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator()
        {
            @Override
            public void create(SwipeMenu menu)
            {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("详情");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);


                // create "open" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background0
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set item title
                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };

        // set creator
        lv_info.setMenuCreator(creator);

        // step 2. listener item click event
        lv_info.setOnMenuItemClickListener(new OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index)
            {
                Info info = infoList.get(position);

                switch (index)
                {
                    case 0:
                        // open
                        open(info);
                        break;
                    case 1:
                        // delete
                        delete(info);
                        infoList.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Info info = infoList.get(position);
                open(info);
            }
        });

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
                String tid = cursor.getString(cursor.getColumnIndex("tid"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                Integer state = cursor.getInt(cursor.getColumnIndex("status"));
                Timestamp anchor = change(cursor.getString(cursor.getColumnIndex("anchor")));
                Info info = new Info(id,tid,name,phone,state,anchor);
                infoList.add(info);
            }while (cursor.moveToNext());

        }
        cursor.close();
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    //String转化成TimeStamp
    Timestamp change(String time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return Timestamp.valueOf(time);//转换时间字符串为Timestamp
    }

    /**
     * 打开
     * @param info
     */
    private void open(Info info)
    {
        Toast.makeText(getApplicationContext(),"未完成的功能",Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除
     * @param info
     */
    private void delete(Info info)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("list","id = ? ",new String[ ]{info.getId()+""});
        Toast.makeText(getApplicationContext(),"删除数据成功",Toast.LENGTH_SHORT).show();

    }
}
