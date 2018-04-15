package com.zichen.world.activity.bookkeepingBook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zichen.world.R;
import com.zichen.world.base.BookkeepingBook;
import com.zichen.world.base.User;
import com.zichen.world.mapper.mysql.BookkeepingBookTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookkeepingBookActivity extends Activity {

    //当前用户信息
    private User user;
    //数据库文件
    //private SQLiteDatabase mydb;
    //底部按钮
    private TextView mTextMessage;
    private BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    //数据显示
    private MyAdapter myAdapter;
    public ListView bookkeepingBook;
    private List<BookkeepingBook> bblist;
    private Boolean isMulChoice;
    private AdapterView.OnItemClickListener onItemClickListener;
    //添加页面
    private LinearLayout new_Bookkeeping;
    private EditText bookkeepingBook_title;
    private Button isIncome;
    private Button bookkeepingBook_createDate;
    private EditText money;
    private EditText accountNote;
    private Button bookkeepingBook_submit;
    private Button bookkeepingBook_cancel;
    private View.OnClickListener onClickListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    final static int DATE_DIALOG = 1;
    private int year;
    private int month;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookkeeping_book);

        init();
        new_Bookkeeping.setVisibility(View.VISIBLE);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bookkeepingBook.setOnItemClickListener(onItemClickListener);
        myAdapter = new MyAdapter(this,bblist,isMulChoice,bookkeepingBook);
        bookkeepingBook.setAdapter(myAdapter);

        account();
    }

    private List<BookkeepingBook> getList() {
        //return BookkeepingBookTable.select(mydb,null,null,null,null,null,null);
        /**
         * 待办事项：
         * 新建线程查询数据并返回数据
         */
        List<BookkeepingBook> bbooks = new ArrayList<BookkeepingBook>();
        try {
            bbooks = new AsyncTask<BookkeepingBook, Integer, List<BookkeepingBook>>() {
                /**
                 * 数据处理方法,不能进行UI操作
                 * @param bbs
                 * @return
                 */
                @Override
                protected List<BookkeepingBook> doInBackground(BookkeepingBook... bbs) {
                    List<BookkeepingBook> bookkeepingBooks = BookkeepingBookTable.selectBookkeeping("");
                    return bookkeepingBooks;
                }

                /**
                 * 先走doInBackground，再走此方法
                 * 发送数据方法
                 * @param bbs doInBackground的返回值
                 */
                @Override
                protected void onPostExecute(List<BookkeepingBook> bbs) {
                    if(bbs.size()>0){

                    }
                    super.onPostExecute(bbs);
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
                protected void onCancelled(List<BookkeepingBook> bbs) {
                    super.onCancelled(bbs);
                }

                /**
                 * 取消AsyncTask
                 */
                @Override
                protected void onCancelled() {
                    super.onCancelled();
                }
            }.execute(new BookkeepingBook()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bbooks;
    }

    @SuppressLint("WrongConstant")
    public void init(){
        user = (User) getIntent().getSerializableExtra("user");
        //mydb = openOrCreateDatabase(SDCard.getSqlitePath(BookkeepingBookActivity.this),SQLiteDatabase.CREATE_IF_NECESSARY,null);
        mTextMessage = findViewById(R.id.message);
        navigation = findViewById(R.id.navigation);
        mOnNavigationItemSelectedListener = getOnNavigationItemSelectedListener();
        onItemClickListener =  getOnItemClickListener();
        bookkeepingBook = findViewById(R.id.bookkeepingBook_list);
        new_Bookkeeping = findViewById(R.id.new_bookkeeping);
        bblist = getList();
        isMulChoice = false;
    }


    private AdapterView.OnItemClickListener getOnItemClickListener() {
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isMulChoice = false;
                //设置选中状态
                view.setSelected(true);
                //提示框
                showInfo();
            }
        };
        return  onItemClickListener;
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

    public BottomNavigationView.OnNavigationItemSelectedListener getOnNavigationItemSelectedListener(){
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mTextMessage.setText(R.string.To_be_developed1);
                        bookkeepingBook.setVisibility(View.GONE);
                        mTextMessage.setVisibility(View.GONE);
                        new_Bookkeeping.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.navigation_dashboard:
                        //mTextMessage.setText(R.string.welcome);
                        mTextMessage.setVisibility(View.GONE);
                        bookkeepingBook.setVisibility(View.VISIBLE);
                        new_Bookkeeping.setVisibility(View.GONE);

                        bblist = getList();
                        MyAdapter myAdapter = new MyAdapter(BookkeepingBookActivity.this,bblist,isMulChoice,bookkeepingBook);
                        bookkeepingBook.setAdapter(myAdapter);
                        return true;
                    case R.id.navigation_notifications:
                        mTextMessage.setText(R.string.To_be_developed2);
                        mTextMessage.setVisibility(View.VISIBLE);
                        bookkeepingBook.setVisibility(View.GONE);
                        new_Bookkeeping.setVisibility(View.GONE);
                        return true;
                }
                return false;
            }
        };
        return mOnNavigationItemSelectedListener;
    }

    /**
     * 添加记账方法
     */
    public void account(){
        bookkeepingBook_title = findViewById(R.id.bookkeepingBook_title);
        isIncome = findViewById(R.id.isIncome);
        bookkeepingBook_createDate = findViewById(R.id.bookkeepingBook_createDate);
        money = findViewById(R.id.money);
        accountNote = findViewById(R.id.accountNote);
        bookkeepingBook_submit = findViewById(R.id.bookkeepingBook_submit);
        bookkeepingBook_cancel = findViewById(R.id.bookkeepingBook_cancel);

        onClickListener = getOnClickListener();
        onDateSetListener = getOnDateSetListener();

        isIncome.setText("否");
        isIncome.setOnClickListener(onClickListener);
        bookkeepingBook_createDate.setOnClickListener(onClickListener);
        bookkeepingBook_submit.setOnClickListener(onClickListener);
        bookkeepingBook_cancel.setOnClickListener(onClickListener);

        //日期的处理
        year = Integer.parseInt(new SimpleDateFormat("YYYY").format(new Date()));
        month = new Date().getMonth();
        day = new Date().getDate();

        //价格的处理-----只能输入两位小数
        money.addTextChangedListener(getTextWatcher());
    }

    private DatePickerDialog.OnDateSetListener getOnDateSetListener() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int years, int months, int dayOfMonth) {
                months += 1;
                bookkeepingBook_createDate.setText(years+"年"+months+"月"+dayOfMonth+"日");
                year = years;
                month = months;
                day = dayOfMonth;
            }
        };
        return onDateSetListener;
    }

    public View.OnClickListener getOnClickListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.isIncome:
                        if("是".equals(isIncome.getText())){
                            isIncome.setText("否");
                        }else{
                            isIncome.setText("是");
                        }
                        break;
                    case R.id.bookkeepingBook_createDate:
                        showDialog(DATE_DIALOG);
                        break;
                    case R.id.bookkeepingBook_submit:
                        String createDate = bookkeepingBook_createDate.getText().toString();
                        if("请选择日期".equals(bookkeepingBook_createDate.getText())){
                            createDate = null;
                        }
                        Boolean isIncomes = false;
                        if("是".equals(isIncome.getText())){
                            isIncomes = true;
                        }
                        BookkeepingBook bookkeepingBook = new BookkeepingBook(user.getId()+"",createDate,createDate,bookkeepingBook_title.getText().toString(),accountNote.getText().toString(),money.getText().toString(),isIncomes);
                        final List<BookkeepingBook> bbList = new ArrayList<BookkeepingBook>();
                        bbList.add(bookkeepingBook);
                        //long i = BookkeepingBookTable.insert(mydb,bbList);
                        try {
                            new AsyncTask<BookkeepingBook, Integer, Long>() {
                                /**
                                 * 数据处理方法,不能进行UI操作
                                 * @param bb
                                 * @return
                                 */
                                @Override
                                protected Long doInBackground(BookkeepingBook... bb) {
                                    List<BookkeepingBook> bbList = new ArrayList<BookkeepingBook>();
                                    bbList.add(bb[0]);
                                    long i = BookkeepingBookTable.insert(bbList);
                                    return i;
                                }

                                /**
                                 * 先走doInBackground，再走此方法
                                 * 发送数据方法
                                 * @param i doInBackground的返回值
                                 */
                                @Override
                                protected void onPostExecute(Long i) {
                                    if(i>-1){
                                        Toast.makeText(BookkeepingBookActivity.this,"成功",Toast.LENGTH_LONG).show();
                                        bookkeepingBook_title.setText(null);
                                        bookkeepingBook_createDate.setText("请选择日期");
                                        money.setText(null);
                                        accountNote.setText(null);
                                    }else{
                                        Toast.makeText(BookkeepingBookActivity.this,"失败",Toast.LENGTH_LONG).show();
                                    }
                                    super.onPostExecute(i);
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
                                protected void onCancelled(Long count) {
                                    super.onCancelled(count);
                                }

                                /**
                                 * 取消AsyncTask
                                 */
                                @Override
                                protected void onCancelled() {
                                    super.onCancelled();
                                }
                            }.execute(bbList.get(0)).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        break;
                    case R.id.bookkeepingBook_cancel:
                        bookkeepingBook_title.setText(null);
                        bookkeepingBook_createDate.setText("请选择日期");
                        money.setText(null);
                        accountNote.setText(null);
                        break;
                    default:
                        break;
                }
            }
        };

        return onClickListener;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, getOnDateSetListener(), year,month,day);
        }
        return null;
    }

    public TextWatcher getTextWatcher(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        money.setText(s);
                        money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    money.setText(s);
                    money.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        money.setText(s.subSequence(0, 1));
                        money.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;
    }
}


class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<BookkeepingBook> bblist;
    private Context context;
    private HashMap<Integer, View> mView ;
    public HashMap<Integer, Integer> visiblecheck ;//用来记录是否显示checkBox
    public HashMap<Integer, Boolean> ischeck;
    private boolean isMulChoice = false;
    private ListView bookkeepingBook;

    public MyAdapter(Context context, List<BookkeepingBook> bblist, Boolean isMulChoice, ListView bookkeepingBook) {
        this.bookkeepingBook = bookkeepingBook;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.bblist = bblist;
        this.isMulChoice = isMulChoice;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = new HashMap<Integer, View>();
        visiblecheck = new HashMap<Integer, Integer>();
        ischeck      = new HashMap<Integer, Boolean>();
        if(isMulChoice){
            for(int i=0;i<bblist.size();i++){
                ischeck.put(i, true);
                visiblecheck.put(i, CheckBox.VISIBLE);
            }
        }else{
            for(int i=0;i<bblist.size();i++){
                ischeck.put(i, false);
                visiblecheck.put(i, CheckBox.INVISIBLE);
            }
        }
    }

    @Override
    public int getCount() {
        return bblist.size();
    }

    @Override
    public Object getItem(int position) {
        return bblist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //在第一页的数据convertView都为空，之后的就不会为空了。
        Log.v("BookkeepingBook",bblist.get(position).toString());
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.bookkeepingbook_item,null);

            holder.bookkeepingBookID = convertView.findViewById(R.id.bookkeepingBookID);
            holder.isIncome = convertView.findViewById(R.id.isIncome);
            holder.accountID = convertView.findViewById(R.id.accountID);
            holder.accountTitle = convertView.findViewById(R.id.accountTitle);
            holder.bookkeepingBookCreateDate = convertView.findViewById(R.id.bookkeepingBookCreateDate);
            holder.bookkeepingBookModifyDate = convertView.findViewById(R.id.bookkeepingBookModifyDate);
            holder.bookkeepingBook_duoxuan = convertView.findViewById(R.id.bookkeepingBook_duoxuan);
            holder.bookkeepingBook_duoxuan.setVisibility(View.GONE);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.accountID.setText(bblist.get(position).getAccountId());
        holder.accountTitle.setText(bblist.get(position).getAccountTitle());
        holder.bookkeepingBookCreateDate.setText(bblist.get(position).getCreateDate());
        holder.bookkeepingBookModifyDate.setText(bblist.get(position).getModifyDate());

        holder.bookkeepingBook_duoxuan.setChecked(ischeck.get(position));
        holder.bookkeepingBook_duoxuan.setVisibility(visiblecheck.get(position));
        holder.bookkeepingBook_duoxuan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                for(int i=0;i<bblist.size();i++){
                    visiblecheck.put(i, CheckBox.VISIBLE);
                }
                MyAdapter adapter = new MyAdapter(context,bblist,isMulChoice,bookkeepingBook);
                bookkeepingBook.setAdapter(adapter);
                return true;
            }
        });
        mView.put(position,convertView);
        return convertView;
    }
}
