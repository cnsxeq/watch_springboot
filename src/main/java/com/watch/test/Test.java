package com.watch.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try(
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/watch_springboot?useUnicode=true&characterEncoding=utf8",
                        "root","lxjkq13141517");
                Statement s = c.createStatement();
        ){
            for (int i = 1;i<10;i++){
                String sqlFormat = "insert into brand values(null,'测试分类%d')";
                String sql = String.format(sqlFormat,i);
                s.execute(sql);
            }
            System.out.println(" 20 条数据ok");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}