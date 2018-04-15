package com.zichen.world.mapper.mysql;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zichen.world.base.BookkeepingBook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */

public class BookkeepingBookTable {

    /**
     *  zichen 创建BookkpingBook表
     * @date 2018-1-1
     */
    public static void createBookkpingBookTable() {
        //创建表
        Connection connection = DBConnect.connectDB();
        Statement ps = null;
        try {
            String sql = "CREATE TABLE IF NOT EXISTS bookkeepingBook" +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    " accountId VARCHAR ," +
                    " createDate VARCHAR ," +
                    " modifyDate VARCHAR ," +
                    " accountTitle VARCHAR ," +
                    " accountNote VARCHAR ," +
                    " money VARCHAR ," +
                    " isIncome VARCHAR );";
            ps = connection.createStatement();
            ps.execute(sql);
        }catch (java.sql.SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * zichen 插入user数据
     * @date 2017-12-31
     * @param bblist 数据
     */
    public static long insert(List<BookkeepingBook> bblist) {
        long i = -1;
        Connection connection = DBConnect.connectDB();
        Statement ps = null;
        try {
            String sql = "INSERT INTO bookkeepingBook " +
                    "(createDate,modifyDate,accountId,accountTitle,accountNote,money,isIncome)" +
                    "values";
            for(int j=0;j<bblist.size();j++){
                BookkeepingBook bb = bblist.get(j);
                int isIncome = bb.getIsIncome()==false?0:1;
                sql +=  "('"+bb.getCreateDate()+"','"+bb.getModifyDate()+"','"+bb.getAccountId()+"','" +
                        bb.getAccountTitle()+"','"+bb.getAccountNote()+"','"+
                        bb.getMoney()+"','"+isIncome+"')" ;
                if(bblist.size()>1 && j != bblist.size()-1){
                    sql += ",";
                }
            }
            ps = connection.createStatement();
            ps.execute(sql);
            i = 1;
        }catch (java.sql.SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } catch (java.sql.SQLException e) {
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
    public static List<BookkeepingBook> selectBookkeeping(String filter){
        List<BookkeepingBook> bookkeepingBooks = new ArrayList<BookkeepingBook>();
        Connection connection = DBConnect.connectDB();
        Statement ps = null;
        ResultSet c = null;
        try {
            String sql = "SELECT * FROM bookkeepingBook "+filter ;
            ps = connection.createStatement();
            c  = ps.executeQuery(sql);
            while (c.next()){
                Boolean b = false;
                if(c.getInt(8)==1){
                    b = true;
                }
                bookkeepingBooks.add(new BookkeepingBook(c.getInt(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),b));
            }
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(c!=null){
                    c.close();
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return bookkeepingBooks;
    }

}

