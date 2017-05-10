package com.yongf.rorderwaiter.app.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yongf.rorderwaiter.R;
import com.yongf.rorderwaiter.app.application.AppEnv;
import com.yongf.rorderwaiter.model.order.OrderDetailResultBean;
import com.yongf.rorderwaiter.model.order.UpdateDishScheduleBodyBean;
import com.yongf.rorderwaiter.model.order.UpdateDishScheduleResultBean;
import com.yongf.rorderwaiter.net.DataObservable;
import com.yongf.rorderwaiter.widget.TitleLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 订单详情页面
 *
 * @author Scott Wang
 * @version 1.0, 17-5-8
 * @see
 * @since ROder V1.0
 */
public class OrderDetailActivity extends BaseActivity {

    public static final String ORDER_DETAIL_ID = "order_detail_id";
    private static final String TAG = OrderDetailActivity.class.getSimpleName();

    private static final int MSG_REFRESH = 0;
    private static final int REFRESH_INTERVAL = 30 * 1000;          //每30s刷新一下数据~

    private String[] mSchedules = new String[]{"尚未开始制作", "正在制作", "上菜中", "上菜完毕", "其它"};

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_cover)
    ImageView mIvCover;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rb_score)
    RatingBar mRbScore;
    @BindView(R.id.tv_real_price)
    TextView mTvRealPrice;
    @BindView(R.id.tv_minus)
    TextView mTvMinus;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.tv_count2)
    TextView mTvCount2;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_created_at)
    TextView mTvCreatedAt;
    @BindView(R.id.tv_updated_at)
    TextView mTvUpdatedAt;

    private Random mRandom;
    private OrderDetailResultBean mOrderDetailResultBean;
    private Timer mTimer = new Timer();
    private int mOrderDetailId;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REFRESH:
                    AppEnv.getUserToast().simpleToast("开始刷新数据");
                    initData();             //定时刷新数据
            }
        }
    };
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(MSG_REFRESH);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mTimer.cancel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    // TODO: 17-5-9 定时器请求~

    @Override
    protected void before() {
        mRandom = new Random();
        mTimer.schedule(mTimerTask, REFRESH_INTERVAL, REFRESH_INTERVAL);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        mOrderDetailId = -1;
        if (bundle != null) {
            mOrderDetailId = bundle.getInt(ORDER_DETAIL_ID, -1);
        }
        if (mOrderDetailId == -1) {
            AppEnv.getUserToast().simpleToast("没有OrderDetailId, 使用默认的1");
            mOrderDetailId = 1;
        }

        loadOrderDetail(mOrderDetailId);
    }

    @Override
    protected void initEvent() {
        mTlTitle.setOnLeftIconClickListener(() -> finish());
    }

    @Override
    protected boolean needRefreshDataWhenResume() {
        return true;
    }

    /**
     * 加载这一条订单详情的数据
     *
     * @param orderDetailId
     */
    private void loadOrderDetail(int orderDetailId) {
        getSubscription().add(
                DataObservable.orderDetail(DataObservable.TYPE_NETWORK, orderDetailId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<OrderDetailResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                AppEnv.getUserToast().simpleToast("加载订单详情数据失败,请稍后再试");
                            }

                            @Override
                            public void onNext(OrderDetailResultBean bean) {
                                mOrderDetailResultBean = bean;
                                update(bean);
                            }
                        })
        );
    }

    /**
     * 网络请求数据,更新UI
     *
     * @param bean
     */
    private void update(@NonNull OrderDetailResultBean bean) {
        updateStatus(bean.getStatus());
        updateCover(bean.getCover());

        mTvName.setText(bean.getName());
        mRbScore.setRating(mRandom.nextInt(5));
        mTvRealPrice.setText("¥" + bean.getReal_price() + "");
        mTvCount.setText(bean.getQuantity() + "");
        mTvCount2.setText(bean.getQuantity() + "");
        mTvOrderNumber.setText(bean.getOrder_id());
        mTvCreatedAt.setText(bean.getCreated_at());
        mTvUpdatedAt.setText(bean.getUpdated_at());
    }

    /**
     * 更新菜品的封面图片
     *
     * @param coverUrl
     */
    private void updateCover(String coverUrl) {

    }

    /**
     * 更新状态
     * <p>
     * 进度:
     * 0-尚未开始制作
     * 1-正在制作
     * 2-制作完成上菜中
     * 3-上菜完毕
     *
     * @param status
     */
    private void updateStatus(int status) {
        String text = "";
        switch (status) {
            case 0:
                text = "正在排队,尚未开始制作";
                break;
            case 1:
                text = "正在制作";
                break;
            case 2:
                text = "制作完成上菜中";
                break;
            case 3:
                text = "上菜完毕";
                break;
            default:
                text = "未知状态,请刷新本页面";
                break;
        }
        mTvStatus.setText(text);
    }

    /**
     * 减少份数
     */
    @OnClick(R.id.tv_minus)
    void onMinus() {
        int finalCount = Integer.parseInt(mTvCount.getText().toString().trim());
        if (finalCount > 0) {
            mTvCount.setText((finalCount - 1) + "");
        }
    }

    /**
     * 增加份数
     */
    @OnClick(R.id.tv_add)
    void onAdd() {
        int finalCount = Integer.parseInt(mTvCount.getText().toString().trim());
        mTvCount.setText((finalCount + 1) + "");
    }

    @OnClick(R.id.tv_submit)
    void onSubmit() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("请选择更新进度")
                .setItems(mSchedules, (dialog1, which) -> executeSubmit(which))
                .create();
        dialog.show();
    }

    /**
     * 真正提交修改后的订单
     *
     * @param schedule 需要更新到的进度
     */
    private void executeSubmit(int schedule) {
        String jsonBody = buildUpdateOrderDetailJsonBody(schedule);
        getSubscription().add(
                DataObservable.updateDishSchedule(DataObservable.TYPE_NETWORK, jsonBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<UpdateDishScheduleResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                AppEnv.getUserToast().simpleToast("网络错误,请检查网络后重试");
                                initData();                 //刷新页面数据
                            }

                            @Override
                            public void onNext(UpdateDishScheduleResultBean updateDishScheduleResultBean) {
                                if (checkUpdateResult(updateDishScheduleResultBean)) {
                                    AppEnv.getUserToast().simpleToast("更新成功");
                                } else {
                                    AppEnv.getUserToast().simpleToast("更新失败, 请稍后重试");
                                }
                                initData();
                            }
                        })
        );
    }

    /**
     * 从返回结果中确定修改是否成功
     * result: 0-更新成功, 1-更新失败
     *
     * @param bean
     *
     * @return
     */
    private boolean checkUpdateResult(@NonNull UpdateDishScheduleResultBean bean) {
        return bean.getResult() == 0;
    }

    /**
     * 构造顾客端更新订单请求的请求体body
     *
     * @param schedule 需要更新到的进度, 0-尚未开始, 1-正在制作, 2-上菜中, 3-上菜完毕, 4-其它
     *
     * @return
     */
    private String buildUpdateOrderDetailJsonBody(int schedule) {
        UpdateDishScheduleBodyBean bean = new UpdateDishScheduleBodyBean();
        bean.setOrder_details_id(mOrderDetailId);
        bean.setSchedule(schedule);

        return new Gson().toJson(bean);
    }
}
