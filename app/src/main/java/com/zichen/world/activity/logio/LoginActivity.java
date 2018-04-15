package com.zichen.world.activity.logio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.zichen.world.R;
import com.zichen.world.activity.MainActivity;
import com.zichen.world.base.User;
import com.zichen.world.mapper.mysql.MUserTable;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    //用户名
    private EditText loginName;
    //密码
    private EditText loginPssd;
    //显示密码开关
    private Switch showPssd;
    //登录
    private Button login;
    //注册
    private Button logon;
    //数据库SQLite
    //private SQLite sqlite;
    //数据库文件
    //private SQLiteDatabase USER_DB;
    //监听事件
    private View.OnClickListener onClickListener;
    private final static int SUCCESS = 1;
    private final static int ERROR = 0;


    @SuppressLint("WrongConstant")
    public void init(){
        this.loginName = findViewById(R.id.loginName);
        this.loginPssd = findViewById(R.id.loginPssd);
        this.showPssd = findViewById(R.id.showPssd);
        this.login = findViewById(R.id.login);
        this.logon = findViewById(R.id.logon);
        //创建app专属文件夹，并存放数据
        /*this.USER_DB = openOrCreateDatabase(SDCard.getSqlitePath(LoginActivity.this),SQLiteDatabase.CREATE_IF_NECESSARY,null);
        this.sqlite = new SQLite(USER_DB);
        //user表
        UserTable.createUserTable(USER_DB);
        BookkeepingBookTable.createBookkpingBookTable(USER_DB);
        //数据
        List<User> uu = new ArrayList<User>();
        uu.add(new User("紫宸","zichen","123"));
        uu.add(new User("磊少","leishao","456"));
        //插入数据
        sqlite.insertUser(uu);
        */
        this.onClickListener = loginLogon();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        showPssd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    loginPssd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //loginPssd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    loginPssd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    //loginPssd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        login.setOnClickListener(onClickListener);

        logon.setOnClickListener(onClickListener);



    }

    public View.OnClickListener loginLogon(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //登录
                if(v.getId()==R.id.login) {
                    //判断用户名和密码
                    Log.i("tag","1："+loginName.getText());
                    //String selection = "loginName = '"+loginName.getText()+"'";
                    //List<User> ulist = sqlite.selectUser(null,selection,null,null,null,null);

                    new AsyncTask<User, Integer, List<User>>() {
                        /**
                         * 数据处理方法,不能进行UI操作
                         * @param users
                         * @return
                         */
                        @Override
                        protected List<User> doInBackground(User... users) {
                            List<User> u = MUserTable.selectUser(users[0]);
                            return u;
                        }

                        /**
                         * 先走doInBackground，再走此方法
                         * 发送数据方法
                         * @param users doInBackground的返回值
                         */
                        @Override
                        protected void onPostExecute(List<User> users) {
                            if(users.size()>0){
                                Intent intent  = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user",users.get(0));
                                startActivity(intent);
                            }
                            super.onPostExecute(users);
                            //给进度方法赋值
                            //publishProgress(100);
                        }

                        /**
                         * 发送数据之前方法
                         */
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        /**
                         * 进度加载方法
                         * @param values
                         */
                        @Override
                        protected void onProgressUpdate(Integer... values) {
                            super.onProgressUpdate(values);
                        }
                        /**
                         * 取消AsyncTask时，并对得到的数据进行处理
                         */
                        @Override
                        protected void onCancelled(List<User> users) {
                            super.onCancelled(users);
                        }

                        /**
                         * 取消AsyncTask
                         */
                        @Override
                        protected void onCancelled() {
                            super.onCancelled();
                        }
                    }.execute(new User(null,loginName.getText().toString(),null));

                    //注册
                }else if(v.getId()==R.id.logon){
                    //跳转页面
                    Intent intent  = new Intent(LoginActivity.this, LogonActivity.class);
                    startActivity(intent);
                }
            }
        };
        return onClickListener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(LoginActivity.this,requestCode+","+resultCode,Toast.LENGTH_LONG).show();

        switch(requestCode){
            case 0:
                //注册成功
                Toast.makeText(LoginActivity.this,"LogonActivity返回结果",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

}
