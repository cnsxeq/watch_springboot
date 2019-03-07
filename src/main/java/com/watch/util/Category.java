package com.watch.util;

public class Category {
    private  int bid;
    private String sex;
    private String core;
    private String shell;

    public void setBid(int bid){
        this.bid=bid;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    public int getBid(){
        return bid;
    }
    public String getSex(){
        return sex;
    }
    public String getCore(){
        return core;
    }
    public String getShell(){return shell;}
}
