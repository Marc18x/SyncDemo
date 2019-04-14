package com.example.marc.syncdemo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.syncdemo.Model.Info;
import com.example.marc.syncdemo.R;
import com.example.marc.syncdemo.Tools.TimeID;

import java.sql.Timestamp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateInfoActivity extends AppCompatActivity {

    @BindView(R.id.txt_tid)
    TextView txt_tid;

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
        setContentView(R.layout.activity_update_info);

        //初始化绑定ButterKinife
        ButterKnife.bind(this);

        final TimeID timeID = new TimeID();
        //获取传递而来的更新info值
        final Info updateInfo = (Info) getIntent().getSerializableExtra("updateInfo");

        txt_tid.setText(updateInfo.getTid());
        et_name.setText(updateInfo.getName());
        et_phone.setText(updateInfo.getPhone());

        add_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消添加，返回上一级界面
                finish();
            }
        });

        //向数据库中添加新的数据
        add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info info = new Info();
                info.setName(et_name.getText().toString());
                info.setPhone(et_phone.getText().toString());
                //更新标记
                info.setState(1);
                //时间戳
                info.setAnchor(new Timestamp(System.currentTimeMillis()).toString());


                //提交执行
                info.updateAll("tid=?",updateInfo.getTid());


                Intent intent = getIntent();
                setResult(0x901,intent);
                finish();



            }
        });

    }
}
