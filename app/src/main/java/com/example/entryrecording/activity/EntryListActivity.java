package com.example.entryrecording.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.entryrecording.MainActivity;
import com.example.entryrecording.R;
import com.example.entryrecording.adapter.BaseAdapter;
import com.example.entryrecording.adapter.EntryListAdapter;
import com.example.entryrecording.bean.Entry;
import com.example.entryrecording.mydao.entryDao;
import com.example.entryrecording.mydao.entrybaseDao;
import com.example.entryrecording.utils.EditTextUtil;
import com.example.entryrecording.utils.ShakeAnimatorUtil;
import com.example.entryrecording.utils.ToastUtils;
import com.example.entryrecording.widget.MyToolBar;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EntryListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    MyToolBar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.text_count)
    TextView textCount;


    private String baseid;
    private List<Entry> entries = new ArrayList();
    private entryDao entryDao = new entryDao();
    private EntryListAdapter entryListAdapter;
    Entry entry = new Entry();

    static int countbase;//词条总数
    static int currentbase;//当前词条数字


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);
        ButterKnife.bind(this);
        init();
        ToastUtils.show(this, baseid);
    }

    public void init() {
        baseid = getIntent().getStringExtra("key_baseid");
        getAllEntry();
        showdata();
        setRecyclerview();
        setToolbar();
        getcount();
    }
    private void getcount(){
        textCount.setText("词库号："+baseid+"   总词条数："+entries.size());
    }

    //配置toolsbar
    public void setToolbar() {
        toolbar.inflateMenu(R.menu.menu_entrylist);

        //设置点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_add:
                        //addUI();
                        addEntry();
                        break;
                    case R.id.action_find:
                        findUI();
                        break;
                    case R.id.action_showall:
                        showAllUI();
                        getAllEntry();
                        showdata();
                        break;
                }
                return false;
            }
        });

        //搜索框使用
//        toolbar.setSearchViewOnclickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                query(v);
//            }
//        });

    }


    public void showdata() {


        entryListAdapter = new EntryListAdapter(this, entries);
        recyclerview.setAdapter(entryListAdapter);
        entryListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Entry entry = entryListAdapter.getItem(position);
                Intent intent = new Intent(EntryListActivity.this, EntryActivity.class);
                intent.putExtra("key_entries", (Serializable) entries);
                intent.putExtra("key_position", position);
                intent.putExtra("key_entry", entry);
                startActivity(intent);

            }
        });

    }

    public void getAllEntry() {
        entries = entryDao.fingby(baseid);
        List<Entry> e = new ArrayList<>();
        for(int i=0;i<entries.size();i++){
            if(entries.get(i).getE_spare1().equals("1")){
                e.add(entries.get(i));
            }
        }
        entries=e;
    }

    //点击软键盘外隐藏
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                EditTextUtil.hideKeyboard(ev, view, EntryListActivity.this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    //监听软键盘搜索键
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            String key = toolbar.getmSearchViewValue();
            entries = entryDao.fingbykey(key);
            showdata();
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(EntryListActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            return true;
        }
        return super.dispatchKeyEvent(event);
    }


//    //查找词条
//    public void query(View v) {
//        String key = toolbar.getmSearchViewValue();
//        entries = entryDao.fingbykey(key);
//        showdata();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
//    }

    //添加词条
    public void addEntry() {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_universal_add)
                .setWidth(600)  //设置弹窗宽度(px)
                .setHeight(800)  //设置弹窗高度(px)
                .setScreenWidthAspect(this, 0.8f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setScreenHeightAspect(this, 0.3f)  //设置弹窗高度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置\
                .setTag("DialogTest")   //设置Tag
                .setDimAmount(0.5f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                .addOnClickListener(R.id.btn_sure, R.id.btn_cancel)   //添加进行点击控件的id
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        viewHolder.setText(R.id.textView2,"新增词条");
                        viewHolder.setText(R.id.name,"词条名字：");
                    }
                })
                .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.btn_sure:
                                EditText editText = viewHolder.getView(R.id.edit_name);
                                if (TextUtils.isEmpty(editText.getText())) {
                                    ShakeAnimatorUtil.shakeX(editText);
                                    ToastUtils.show(view.getContext(), "词条中文名不能为空");
                                    return;
                                }
                                addToDataBase(editText.getText().toString());
                                getcount();
                                ToastUtils.show(view.getContext(), "添加成功");
                                tDialog.dismiss();
                                showdata();
                                break;
                            case R.id.btn_cancel:
                                break;
                        }
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return false;
                    }
                })
                .create()   //创建TDialog
                .show();    //展示


    }

    //添加词条到数据库
    public void addToDataBase(String name) {
        countbase = entries.size() + 1;
        currentbase = countbase + 10000;
        entry.setE_id(baseid+"_E"+currentbase + "");
        entry.setE_content(name);
        entry.setE_english("ABCDEFG");
        entry.setE_baseid(baseid);
        entry.setE_spare1("1");//表明存在未删除
        entryDao.insertEntry(entry);
        entries.add(entry);
    }


    //配置Recyclerview
    public void setRecyclerview() {
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//添加分割线

        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        recyclerview.setItemAnimator(new DefaultItemAnimator());//动画
    }

    //添加的UI
    public void addUI() {
        toolbar.hideSearchView();
        toolbar.showTitleView();
        toolbar.setTitle("全部词条");
        showdata();
    }

    //查找的UI
    public void findUI() {
        toolbar.showSearchView();
        toolbar.hideTitleView();
    }

    //显示全部词条的UI
    public void showAllUI() {
        toolbar.showTitleView();
        toolbar.hideSearchView();
        toolbar.setTitle("全部词条");
    }


}

