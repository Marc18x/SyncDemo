package com.example.marc.syncdemo.Tools;

import java.util.Calendar;

public class TimeID {
    //通过当前的时间生成一个唯一的Id编号
    public String generateId()
    {
        String TimeId = "";
        Calendar cal = Calendar.getInstance();
        int y=cal.get(Calendar.YEAR);
        TimeId += y;
        int m=cal.get(Calendar.MONTH);
        TimeId += transfer(m+1);
        int d=cal.get(Calendar.DATE);
        TimeId += transfer(d);
        int h=cal.get(Calendar.HOUR_OF_DAY);
        TimeId += transfer(h);
        int mi=cal.get(Calendar.MINUTE);
        TimeId += transfer(mi);
        int s=cal.get(Calendar.SECOND);
        TimeId += transfer(s);

        System.out.println("现在时刻是"+y+"年"+m+"月"+d+"日"+h+"时"+mi+"分"+s+"秒");
        return TimeId;

    }

    public String transfer(int data)
    {
        if(data/10==0)
        {
            return "0"+data;
        }
        else
            return data+"";

    }
}
