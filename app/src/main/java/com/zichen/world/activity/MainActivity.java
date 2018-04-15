package com.zichen.world.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zichen.world.R;
import com.zichen.world.activity.bookkeepingBook.BookkeepingBookActivity;
import com.zichen.world.base.User;

public class MainActivity extends AppCompatActivity {

    //当前用户信息
    private User user;
    private TextView mTextMessage;
    //按钮
    private Button bookkeepingBook;
    //按钮监听事件
    private View.OnClickListener onClickListener;

    //底部菜单按钮
    private BottomNavigationView navigation;
    //底部菜单按钮监听事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        bookkeepingBook.setOnClickListener(onClickListener);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void init(){
        user = (User) getIntent().getSerializableExtra("user");
        bookkeepingBook = findViewById(R.id.bookkeepingBook);
        mTextMessage = findViewById(R.id.message);
        navigation = findViewById(R.id.navigation);
        onClickListener = getOnClickListener();
        mOnNavigationItemSelectedListener = getOnNavigationItemSelectedListener();
    }

    public View.OnClickListener getOnClickListener(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bookkeepingBook:
                        Intent intent = new Intent(MainActivity.this, BookkeepingBookActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        break;
                }
            }
        };
        return onClickListener;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener getOnNavigationItemSelectedListener(){
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mTextMessage.setText(R.string.welcome);
                        return true;
                    case R.id.navigation_dashboard:
                        mTextMessage.setText(R.string.To_be_developed);
                        return true;
                    case R.id.navigation_notifications:
                        mTextMessage.setText(R.string.To_be_developed);
                        return true;
                }
                return false;
            }
        };
        return mOnNavigationItemSelectedListener;
    }
}
