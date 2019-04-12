package com.example.marc.syncdemo.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marc.syncdemo.Database.MyDatabaseHelper;
import com.example.marc.syncdemo.R;

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

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        //初始化绑定ButterKinife
        ButterKnife.bind(this);

        dbHelper = new MyDatabaseHelper(this,"List.db",null,1);

        add_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //向数据库中添加新的数据
        //测试数据
        add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //获取用户填写的数据
                values.put("name",et_name.getText().toString());
                values.put("phone",et_phone.getText().toString());
                //新增数据 状态默认为0
                values.put("status",0);
                //执行插入操作
                db.insert("list",null,values);
                //清除数据
                values.clear();
                Toast.makeText(getApplicationContext(),"添加数据成功",Toast.LENGTH_SHORT).show();

                et_name.setText("");
                et_name.clearFocus();
                et_phone.setText("");
                et_phone.clearFocus();

            }
        });


    }
}
