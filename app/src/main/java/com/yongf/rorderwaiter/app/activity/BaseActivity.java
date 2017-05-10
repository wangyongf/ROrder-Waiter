/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BaseActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-1       新增：Create	
 */

package com.yongf.rorderwaiter.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yongf.rorderwaiter.app.application.AppEnv;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * BaseActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V0.1
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final int LAST_EXIT_DURATION = 2000;

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected static int LENGTH_LONG = Toast.LENGTH_LONG;
    protected static int LENGTH_SHORT = Toast.LENGTH_SHORT;

    //复合订阅
    protected CompositeSubscription mSubscription = new CompositeSubscription();

    private long mLastExitTime = 0;                     //上一次在主页点击返回按钮的时刻

    protected CompositeSubscription getSubscription() {
        return mSubscription;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        ButterKnife.bind(this);

        before();
        initPresenter();
        initView();
        initData();
        initEvent();
        after();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (getSubscription() != null && getSubscription().isUnsubscribed()) {
            getSubscription().unsubscribe();
        }
    }

    /**
     * 获取布局文件的id
     *
     * @return 布局文件的id
     */
    protected abstract int getLayoutId();

    /**
     * 用于initView, initData, initEvent之前的初始化工作
     */
    protected void before() {

    }

    /**
     * 初始化视图
     */
    protected void initPresenter() {

    }

    /**
     * 初始化视图
     */
    protected void initView() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化事件
     */
    protected void initEvent() {

    }

    /**
     * 用于initView, initData, initEvent之后的初始化工作
     */
    protected void after() {

    }

    /**
     * 页面重新获取焦点时是否要重新加载数据
     * 默认是false
     *
     * @return 是否要重新加载数据
     */
    protected boolean needRefreshDataWhenResume() {
        return false;
    }

    /**
     * 在主页点击退出按钮提示
     */
    @Override
    public void onBackPressed() {
        if (this instanceof MainActivity
                && System.currentTimeMillis() - mLastExitTime > LAST_EXIT_DURATION) {
            mLastExitTime = System.currentTimeMillis();
            AppEnv.getUserToast().simpleToast("再次点击退出应用", LENGTH_LONG);
            return;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (needRefreshDataWhenResume()) {
            initData();
        }
    }
}
