package com.zichen.world.mapper.mysql;

import com.zichen.world.base.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/21.
 */

public class MUserTable {

    /**
     * zichen 插入user数据
     * @date 2017-12-31
     * @param ulist 数据
     */
    public static long insert( List<User> ulist) {

        Connection connection = DBConnect.connectDB();
        Statement ps = null;
        long i = -1;
        try {
            String sql = "INSERT INTO user " +
                    "(id,name,loginName,loginPssd)" +
                    " values " ;
            for(User u :ulist){
                sql += "("+u.getId()+","+u.getName()+","+u.getLoginName()+","+u.getLoginPssd()+")";
                if(ulist.size()>1){
                    sql += ",";
                }
            }
            ps = connection.createStatement();
            ps.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    /**
     * zichen 查询数据
     * @date 2017-12-31
     * @return List<User>
     */
    public static List<User> selectUser(User users){
        Connection db = DBConnect.connectDB();
        List<User> userList = new ArrayList<User>();
        String filter = " where ";
        if(users.getId()!=0){
            filter += "id = '"+users.getId()+"'";
        }
        if(users.getName()!=null){
            filter += filter.length()>7?" and "+"name = '"+users.getName()+"'":"name = '"+users.getName()+"'";
        }
        if(users.getLoginName()!=null){
            filter += filter.length()>7?" and "+"loginName = '"+users.getLoginName()+"'":"loginName = '"+users.getLoginName()+"'";
        }
        if(users.getLoginPssd()!=null){
            filter += filter.length()>7?" and "+"loginPssd = '"+users.getLoginPssd()+"'":"loginPssd = '"+users.getLoginPssd()+"'";
        }
        String sql = "select * from user "+filter;
        User user = new User();
        Statement ps = null;
        ResultSet rs = null;
        try {
            ps = (Statement)db .createStatement();
            rs  = ps.executeQuery(sql);
            if(rs!=null) {
                while (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setLoginName(rs.getString("loginName"));
                    user.setLoginPssd(rs.getString("loginPssd"));
                    userList.add(user);
                }
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
        return userList;
    }



}
