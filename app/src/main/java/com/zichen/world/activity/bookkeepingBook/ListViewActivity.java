package com.zichen.world.activity.bookkeepingBook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zichen.world.R;
import com.zichen.world.base.BookkeepingBook;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends Activity {

    //所有记账
    private List<BookkeepingBook> bbList;
    //视图
    private ListView listView;
    //sqlite
    //private SQLite sqLite;

    //监听事件
    private AdapterView.OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        init();
        MyAdapter myAdapter = new MyAdapter(this,bbList,false,listView);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setAdapter(myAdapter);
    }

    @SuppressLint("WrongConstant")
    public void init(){
        listView = findViewById(R.id.bookkeepingBook);
        bbList = selectBookkeepingBook();
        //SQLiteDatabase USER_DB = openOrCreateDatabase(SDCard.getSqlitePath(ListViewActivity.this),SQLiteDatabase.CREATE_IF_NECESSARY,null);
        //sqLite = new SQLite(USER_DB);
        onItemClickListener = getOnItemClickListener();
    }

    private AdapterView.OnItemClickListener getOnItemClickListener() {
        AdapterView.OnItemClickListener onItemClickListeners = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Log.e("tag","点击item");
                showInfo();
            }
        };
        return onItemClickListeners;
    }

    private List<BookkeepingBook> selectBookkeepingBook() {
        List<BookkeepingBook> bblist = new ArrayList<BookkeepingBook>();
        for(int i=1;i<20;i++){
            BookkeepingBook bb = new BookkeepingBook(i,i+"", "2017-12-"+i, "2018-1-"+i, i+"", i+"", i+"", false);
            bblist.add(bb);
        }
        //sqLite.selectBookkeepingBook(null,null,null,null,null,null);
        return bblist;
    }

    /**
     * 对话框提示
     */
    public void showInfo(){
        new AlertDialog.Builder(this)
                .setTitle("提示")
        .setMessage("确定查看该条数据吗？")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

}
