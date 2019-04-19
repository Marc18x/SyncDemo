package com.example.marc.syncdemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaseSwipListAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.example.marc.syncdemo.Model.Info;
import com.example.marc.syncdemo.R;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

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
    private InfoAdapter adapter;


    @BindView(R.id.lv_info)
    SwipeMenuListView lv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        //初始化绑定ButterKinife
        ButterKnife.bind(this);

        initInfo(); //获取信息数据

        adapter = new InfoAdapter();

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

                        //从List中移除对应项目
                        infoList.remove(position);
                        //强制刷新
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

        //过滤已经删除的信息
         infoList = LitePal.where("state>?","-1")
                 .find(Info.class);

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        switch (resultCode){
            case 0x901:
                //从更新数据页面返回而来
                //获取list
                initInfo();
                //强制页面刷新
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"修改数据成功",Toast.LENGTH_SHORT).show();
                break;
            default:

        }


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
        Toast.makeText(getApplicationContext(),"更新数据",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ShowItemActivity.this,UpdateInfoActivity.class);
        //传递更新的值
        intent.putExtra("updateInfo",info);
        //页面跳转
        startActivityForResult(intent,0x901);


    }

    /**
     * 删除
     * @param info
     */
    private void delete(Info info)
    {
        //删除

        Info deleteInfo = new Info();
        deleteInfo.setState(-1);
        //时间戳
        deleteInfo.setAnchor(new Timestamp(System.currentTimeMillis()).toString());
        //提交执行
        deleteInfo.updateAll("tid=?",info.getTid());

        Toast.makeText(getApplicationContext(),"删除数据成功",Toast.LENGTH_SHORT).show();

    }

    class InfoAdapter extends BaseSwipListAdapter {



        @Override
        public int getCount() {
            return infoList.size();
        }

        @Override
        public Object getItem(int position) {
            return infoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position , View convertView , ViewGroup parent)
        {


            if(convertView == null){
                convertView = View.inflate(getApplicationContext(),
                        R.layout.adapter_item,null);
                new ViewHolder(convertView);
            }

            ViewHolder viewHolder = (ViewHolder) convertView.getTag();

            Info info =(Info) getItem(position);

            viewHolder.tv_id.setText(info.getId()+"");
            viewHolder.tv_tid.setText(info.getTid()+"");
            viewHolder.tv_name.setText(info.getName());
            viewHolder.tv_phone.setText(info.getPhone());
            viewHolder.tv_state.setText("(状态："+info.getState()+")");
            viewHolder.tv_anchor.setText(info.getAnchor());
            return convertView;
        }


         class ViewHolder{

            @BindView(R.id.tv_id)
            TextView tv_id;

            @BindView(R.id.tv_tid)
            TextView tv_tid;

            @BindView(R.id.tv_name)
            TextView tv_name;

            @BindView(R.id.tv_phone)
            TextView tv_phone;

            @BindView(R.id.tv_state)
            TextView tv_state;

            @BindView(R.id.tv_anchor)
            TextView tv_anchor;

            //初始化绑定ButterKinife
            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
                view.setTag(this);
            }

        }

    }




}
