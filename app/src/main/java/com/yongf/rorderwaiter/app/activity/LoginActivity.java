/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: LoginActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-1       新增：Create	
 */

package com.yongf.rorderwaiter.app.activity;

import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yongf.rorderwaiter.R;
import com.yongf.rorderwaiter.presenter.login.LoginContract;
import com.yongf.rorderwaiter.presenter.login.LoginPresenter;
import com.yongf.rorderwaiter.widget.TitleLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


/**
 * LoginActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V0.1
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;

    @BindView(R.id.et_username)
    EditText mEtUsername;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @BindView(R.id.tv_register)
    TextView mTvRegister;

    @BindView(R.id.tv_find_pwd)
    TextView mTvFindPwd;

    private LoginContract.Presenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();

        LoginPresenter presenter = new LoginPresenter(this);
        setPresenter(presenter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        mTlTitle.setOnLeftIconClickListener(() -> finish());
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void showInputError() {
        // TODO: 17-1-15 封装UserToastWrapper
        Toast.makeText(this, "用户名或密码不能为空哦^_^", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        // TODO: 17-1-15 封装UserToastWrapper
        Toast.makeText(this, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError() {
        // TODO: 17-1-15 封装UserToastWrapper
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(String uid) {

    }

    @OnClick(R.id.btn_login)
    public void onLogin() {
        mPresenter.login(mEtUsername.getText().toString().trim(), mEtPassword.getText().toString().trim());
    }

    @OnClick(R.id.tv_register)
    public void onRegister() {

    }

    @OnClick(R.id.tv_find_pwd)
    public void onFindPwd() {

    }
}
