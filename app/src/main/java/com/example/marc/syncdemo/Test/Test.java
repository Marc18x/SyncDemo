package com.example.marc.syncdemo.Test;

import com.example.marc.syncdemo.Tools.TimeID;

import java.sql.Timestamp;

public class Test {
    public static void main(String[] args)
    {
//        TimeID timeID = new TimeID();
//        System.out.print("TIMEID:"+timeID.generateId());
        Timestamp ts  = new Timestamp(System.currentTimeMillis());
        System.out.print(ts.toString());
    }
}
