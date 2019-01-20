package com.example.marc.syncdemo.Model;

import java.sql.Timestamp;

public class Info {
    private int id;
    private String name;
    private String phone;
    private int state;
    private Timestamp anchor;


    public Info(){

    }

    public Info(int id,String name,String phone,int state,Timestamp anchor)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.state = state;
        this.anchor = anchor;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Timestamp getAnchor() {
        return anchor;
    }

    public void setAnchor(Timestamp anchor) {
        this.anchor = anchor;
    }
}
