package com.example.marc.syncdemo.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE = "create table list ("
            + "[id] integer primary key autoincrement,"
            + "[name] varchar(20) not null,"
            + "[phone] varchar(11) not null,"
            + "[status] interger(1) not null DEFAULT(0),"
            + "[anchor] timestamp NOT NULL DEFAULT(datetime('now','localtime')));";

    private Context mContext;

    public MyDatabaseHelper(Context context ,String name,
                            SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库---没有数据库的情况
        db.execSQL(CREATE_TABLE);
        Toast.makeText(mContext,"Create Database Success!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库迭代操作（版本号高于当前现有版本）----更新数据
        //模拟操作，实际应该将原有数据覆盖到新数据库中
        db.execSQL("drop table if exists list");
        db.execSQL(CREATE_TABLE);
        Toast.makeText(mContext,"更新数据库",Toast.LENGTH_SHORT).show();

    }

    public void recoveryData(SQLiteDatabase db)
    {
        //还原数据库
        db.execSQL("drop table if exists list");
        db.execSQL(CREATE_TABLE);
        Toast.makeText(mContext,"还原数据库成功",Toast.LENGTH_SHORT).show();

    }
}
