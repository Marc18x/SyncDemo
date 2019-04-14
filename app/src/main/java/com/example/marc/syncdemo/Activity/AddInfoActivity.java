package com.example.marc.syncdemo.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marc.syncdemo.Model.Info;
import com.example.marc.syncdemo.R;
import com.example.marc.syncdemo.Tools.TimeID;

import java.sql.Timestamp;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddInfoActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.add_submit)
    Button add_submit;

    @BindView(R.id.add_cancel)
    Button add_cannel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        //初始化绑定ButterKinife
        ButterKnife.bind(this);

        final TimeID timeID = new TimeID();


        add_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消添加，返回上一级界面
                finish();
            }
        });

        //向数据库中添加新的数据
        //测试数据
        add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Info info = new Info();
                //获取用户填写的数据
                info.setName(et_name.getText().toString());
                info.setPhone(et_phone.getText().toString());
                //新增数据 状态默认为0
                info.setState(0);
                //时间tid标识
                info.setTid(timeID.generateId());
                //时间戳
                info.setAnchor(new Timestamp(System.currentTimeMillis()).toString());
                Log.d("timestamp",new Timestamp(System.currentTimeMillis()).toString());
                //执行插入操作
                info.save();

                Toast.makeText(getApplicationContext(),"添加数据成功",Toast.LENGTH_SHORT).show();

                et_name.setText("");
                et_name.clearFocus();
                et_phone.setText("");
                et_phone.clearFocus();

            }
        });


    }
}
