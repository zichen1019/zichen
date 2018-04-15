package com.zichen.world.activity.logio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.zichen.world.R;
import com.zichen.world.base.User;
import com.zichen.world.mapper.mysql.MUserTable;

import java.util.ArrayList;
import java.util.List;

public class LogonActivity extends Activity {

    //网名
    private EditText name;
    //用户名
    private EditText loginName;
    //密码
    private EditText loginPssd;
    //显示密码开关
    private Switch showPssd;
    //注册
    private Button logonUser;
    //数据库SQLite
    //private SQLite sqlite;
    //数据库文件
    //private SQLiteDatabase USER_DB;
    //监听事件
    private View.OnClickListener onClickListener;
    //选中事件
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    @SuppressLint("WrongConstant")
    public void init(){
        this.name = findViewById(R.id.name);
        this.loginName = findViewById(R.id.logonName);
        this.loginPssd = findViewById(R.id.logonPssd);
        this.showPssd = findViewById(R.id.showPssd);
        this.logonUser = findViewById(R.id.logonUser);
        /*this.USER_DB = openOrCreateDatabase(SDCard.getSqlitePath(LogonActivity.this),SQLiteDatabase.CREATE_IF_NECESSARY,null);
        this.sqlite = new SQLite(USER_DB);*/
        this.onClickListener = logons();
        this.onCheckedChangeListener = showPssdListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        init();
        showPssd.setOnCheckedChangeListener(onCheckedChangeListener);
        logonUser.setOnClickListener(onClickListener);


    }

    public View.OnClickListener logons(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册用户
                if(v.getId()==R.id.logonUser){
                    List<User> ulist = new ArrayList<User>();
                    User u = new User(name.getText().toString(),loginName.getText().toString(),loginPssd.getText().toString());
                    ulist.add(u);
                    //sqlite.insertUser(ulist);
                    //String selection = "name = '"+u.getName()+"' and loginName = '"+u.getLoginName()+"' and loginPssd = '"+u.getLoginPssd()+"'";
                    //List<User> userList = sqlite.selectUser(null,selection,null, null,null,null);

                    new AsyncTask<List<User>, Void, List<User>>() {
                        @Override
                        protected List<User> doInBackground(List<User>[] lists) {
                            MUserTable.insert(lists[0]);
                            return MUserTable.selectUser(lists[0].get(0));
                        }

                        @Override
                        protected void onPostExecute(List<User> users) {
                            if(users.size()>0) {
                                //跳转页面
                                Intent intent = new Intent(LogonActivity.this, LoginActivity.class);
                                Bundle bundle = new Bundle();
                                intent.putExtra("user", users.get(0));
                                //startActivityForResult(intent, 0);
                                setResult(0,intent);
                            }else{
                                Toast.makeText(LogonActivity.this,"注册失败",Toast.LENGTH_LONG);
                            }
                            super.onPostExecute(users);
                        }
                    }.execute(ulist);

                }

            }
        };
        return onClickListener;
    }

    public CompoundButton.OnCheckedChangeListener showPssdListener(){
        CompoundButton.OnCheckedChangeListener showPssdListener =  new CompoundButton.OnCheckedChangeListener() {
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
        };
        return showPssdListener;
    }
}
