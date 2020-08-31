package com.example.entryrecording;

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
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.entryrecording.activity.EntryListActivity;
import com.example.entryrecording.adapter.BaseAdapter;
import com.example.entryrecording.adapter.MainAdapter;
import com.example.entryrecording.bean.Entrybase;
import com.example.entryrecording.mydao.entryDao;
import com.example.entryrecording.mydao.entrybaseDao;
import com.example.entryrecording.utils.DateUtil;
import com.example.entryrecording.utils.EditTextUtil;
import com.example.entryrecording.utils.ShakeAnimatorUtil;
import com.example.entryrecording.utils.ToastUtils;
import com.example.entryrecording.widget.MyToolBar;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    MyToolBar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    MainAdapter mainAdapter;
    entrybaseDao entrybaseDao;
    entryDao entryDao = new entryDao();
    List<Entrybase> entrybases2;
    Entrybase entrybase = new Entrybase();

    static int countbase;//词条库总数
    static int currentbase;//当前词条库数字
    @BindView(R.id.text_count)
    TextView textCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getAllBase();
        countbase = entrybases2.size();
        showdata();
        toolbar.inflateMenu(R.menu.menu_entrybase);
        // initentry();
        set(this.toolbar);
        recyclerviewAttribute();
        getcount();


    }

    //获取count
    private void getcount(){
        textCount.setText("词库总数："+entrybaseDao.findall().size());
    }

    //menu点击事件
    public void set(final View view) {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add:
                        //addUI();
                        addentrybase();
                        break;
                    case R.id.action_find:
                        searchUI();
                        break;
                    case R.id.action_showall:
                        //mainAdapter.clear();
                        showdata();
                        showAllUI();
                        break;
                }
                return false;
            }
        });
//        toolbar.setSearchViewOnclickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                query(v);
//            }
//        });

    }
    //点击软键盘外隐藏
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                EditTextUtil.hideKeyboard(ev, view, MainActivity.this);//调用方法判断是否需要隐藏键盘
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
            entrybases2 = entrybaseDao.fingbykey(key);
            showdata();
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            return true;
        }
        return super.dispatchKeyEvent(event);
    }



    //添加词条库
    public void addentrybase() {
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
                .addOnClickListener(R.id.edit_name, R.id.btn_sure, R.id.btn_cancel)   //添加进行点击控件的id
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        viewHolder.setText(R.id.textView2,"新增词条库");
                        viewHolder.setText(R.id.name,"词条库名字：");
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
                                    ToastUtils.show(view.getContext(), "词条库名子不能为空");
                                    return;
                                }
                                addToDataBase(editText.getText().toString());
                                getcount();
                                List<Entrybase> entrybases=entrybaseDao.fingbyLive("1");
                                entrybases2 = entrybases;
                                showdata();
                                ToastUtils.show(view.getContext(), "添加成功");
                                tDialog.dismiss();
                                addUI();
                                break;
                            case R.id.btn_cancel:
                                tDialog.dismiss();
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

    //添加到数据库
    public void addToDataBase(String name) {
        countbase = countbase + 1;
        currentbase = countbase + 10000;
        entrybase.setB_id("B"+currentbase + "");
        entrybase.setB_b_name(name);
        entrybase.setB_count(0 + "");
        entrybase.setB_createtime(DateUtil.getStringDate());
        entrybase.setB_spare1("1");
        entrybaseDao.insertbase(entrybase);
    }

//    //搜索词条库
//    public void query(View v) {
//
//        String key = toolbar.getmSearchViewValue();
//        entrybases2 = entrybaseDao.fingbykey(key);
//        showdata();
//
//    }

    //获取全部数据
    public void getAllBase() {
        entrybases2 = new ArrayList();
        entrybases2.clear();
        entrybaseDao = new entrybaseDao();
        entrybases2 = entrybaseDao.findall();
    }


    //recyclerview渲染数据
    public void showdata() {

        mainAdapter = new MainAdapter(this, entrybases2);
        recyclerview.setAdapter(mainAdapter);

        mainAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Entrybase entrybase3 = mainAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, EntryListActivity.class);

                intent.putExtra("key_baseid", entrybase3.getB_id());
                startActivity(intent);
                //ToastUtils.show(view.getContext(),"");

            }
        });


    }

    //recyclerview属性设置
    public void recyclerviewAttribute() {
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//添加分割线

        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        recyclerview.setItemAnimator(new DefaultItemAnimator());//动画
    }

    //调用搜索框的UI
    public void searchUI() {
        toolbar.showSearchView();
        toolbar.hideTitleView();
    }

    //添加词条库的UI
    public void addUI() {
        toolbar.hideSearchView();
        toolbar.showTitleView();
        toolbar.setTitle("全部词条库");
    }


    //显示全部词条库的UI
    public void showAllUI() {
        toolbar.hideSearchView();
        toolbar.showTitleView();
        toolbar.setTitle("全部词条库");
    }




}
