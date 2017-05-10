package com.yongf.rorderwaiter.app.activity;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yongf.rorderwaiter.R;
import com.yongf.rorderwaiter.util.IntentHelper;
import com.yongf.rorderwaiter.widget.TitleLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.rl_order)
    RelativeLayout mRlOrder;
    @BindView(R.id.rl_myorder)
    RelativeLayout mRlMyorder;
    @BindView(R.id.rl_account)
    RelativeLayout mRlAccount;
    @BindView(R.id.rl_about)
    RelativeLayout mRlAbout;
    @BindView(R.id.tv_order)
    TextView mTvOrder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 浏览菜品
     */
    @OnClick(R.id.rl_order)
    void onOrder() {
//        IntentHelper.simpleJump(this, ShoppingCartActivity.class);
    }

    /**
     * 开始点餐
     */
    @OnClick(R.id.tv_order)
    void onOrder1() {
//        IntentHelper.simpleJump(this, ShoppingCartActivity.class);
    }

    /**
     * 我的订单
     */
    @OnClick(R.id.rl_myorder)
    void onMyOrder() {
        IntentHelper.simpleJump(this, MyOrderActivity.class);
    }

    /**
     * 个人中心
     */
    @OnClick(R.id.rl_account)
    void onAccount() {
        IntentHelper.simpleJump(this, AccountActivity.class);
    }

    /**
     * 关于我们
     */
    @OnClick(R.id.rl_about)
    void onAbout() {
        IntentHelper.simpleJump(this, AboutActivity.class);
    }
}
