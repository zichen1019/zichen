package com.zichen.world.mapper.mysql;

import android.util.Log;

import com.zichen.world.base.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/11/1.
 * 数据库连接
 */

public class DBConnect {

    /**
     * 连接驱动
     * 连接数据库
     */
    public static Connection connectDB(){
        /*
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/
        Connection db = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://39.107.98.55:3306/zichen?user=root&password=root&useUnicode=true&characterEncoding=UTF-8";
            db = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            Log.e("Mysql>>>","Mysql驱动未找到");
            e.printStackTrace();
        } catch (SQLException e){
            Log.e("URL>>>>","URL连接失败");
            e.printStackTrace();
        }
        return db;
    }

    /**
     * 查询sql
     */
    public static User selectUser(){
        Connection db = connectDB();
        String sql = "select * from user where id = 1";
        User user = new User();
        Statement ps = null;
        ResultSet rs = null;
        try {
            ps = (Statement)db .createStatement();
            rs  = ps.executeQuery(sql);
            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLoginName(rs.getString("loginName"));
                user.setLoginPssd(rs.getString("loginPssd"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(db!=null) {
                    db.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

}
