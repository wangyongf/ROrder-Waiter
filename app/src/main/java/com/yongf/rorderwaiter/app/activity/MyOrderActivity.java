package com.yongf.rorderwaiter.app.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongf.rorderwaiter.R;
import com.yongf.rorderwaiter.app.application.AppEnv;
import com.yongf.rorderwaiter.app.application.Config;
import com.yongf.rorderwaiter.model.order.OrderDetailsResultBean;
import com.yongf.rorderwaiter.net.DataObservable;
import com.yongf.rorderwaiter.util.IntentHelper;
import com.yongf.rorderwaiter.widget.TitleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的订单页面
 *
 * @author Scott Wang
 * @version 1.0, 17-5-7
 * @see
 * @since ROder V1.0
 */
public class MyOrderActivity extends BaseActivity {

    public static final String ORDER_ID = "order_id";
    private static final String TAG = MyOrderActivity.class.getSimpleName();

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.rv_details)
    RecyclerView mRvDetails;

    private MyOrderAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        mRvDetails.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyOrderAdapter(this, null);
        mRvDetails.setAdapter(mAdapter);
        mRvDetails.addItemDecoration(new DividerDecoration(this));
    }

    @Override
    protected void initData() {
        int waiterId = Config.WAITER_ID;
        if (waiterId == -1) {
            AppEnv.getUserToast().simpleToast("没有WaiterId, 使用默认的1");
            waiterId = 1;
        }

        //根据订单号获取订单详情
        loadOrderDetail(waiterId);
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
     * 根据订单号获取订单详情
     *
     * @param waiterId
     */
    private void loadOrderDetail(int waiterId) {
        getSubscription().add(
                DataObservable.orderDetails(DataObservable.TYPE_NETWORK, waiterId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<OrderDetailsResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                //获取订单详情数据失败
                                AppEnv.getUserToast().simpleToast("获取订单详情数据失败,请稍后再试");
                            }

                            @Override
                            public void onNext(OrderDetailsResultBean orderDetailsResultBean) {
                                mAdapter.update(orderDetailsResultBean);
                            }
                        })
        );
    }

    public static class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

        private Context mContext;
        private OrderDetailsResultBean mOrderDetailsResultBean;

        public MyOrderAdapter(Context context, @Nullable OrderDetailsResultBean orderDetailsResultBean) {
            mContext = context;
            mOrderDetailsResultBean = orderDetailsResultBean;
        }

        public void update(OrderDetailsResultBean bean) {
            mOrderDetailsResultBean = bean;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_my_order, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            OrderDetailsResultBean.DetailsBean detailsBean = mOrderDetailsResultBean.getDetails().get(position);
            holder.bindData(detailsBean);
        }

        @Override
        public int getItemCount() {
            if (mOrderDetailsResultBean == null ||
                    mOrderDetailsResultBean.getDetails() == null) {
                return 0;
            }
            return mOrderDetailsResultBean.getDetails().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.iv_cover)
            ImageView mIvCover;
            @BindView(R.id.tv_name)
            TextView mTvName;
            @BindView(R.id.tv_count)
            TextView mTvCount;
            @BindView(R.id.tv_real_price)
            TextView mTvRealPrice;
            @BindView(R.id.btn_detail)
            Button mBtnDetail;

            private OrderDetailsResultBean.DetailsBean mDetailsBean;

            public ViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
            }

            /**
             * 绑定数据
             *
             * @param detailsBean
             */
            public void bindData(OrderDetailsResultBean.DetailsBean detailsBean) {
                mDetailsBean = detailsBean;

                // TODO: 17-5-7 考虑cover的事情...
                //常量在左边,变量在右边,防止空指针
                if (!"\"http://www.baidu.com\"".equals(detailsBean.getCover())) {
                    //加载图片...mIvCover
                }
                mTvName.setText(detailsBean.getName());
                mTvCount.setText("份数: " + detailsBean.getQuantity() + "");
                mTvRealPrice.setText("单价: " + detailsBean.getReal_price() + "");
            }

            @OnClick(R.id.btn_detail)
            void go2OrderDetail() {
                int orderDetailId = mDetailsBean.getDetails_id();
                Bundle bundle = new Bundle();
                bundle.putInt(OrderDetailActivity.ORDER_DETAIL_ID, orderDetailId);
                IntentHelper.simpleJump(mContext, OrderDetailActivity.class, bundle);
            }
        }
    }

    /////// ------------------- DividerDecoration ------------------- ///////
    public static class DividerDecoration extends RecyclerView.ItemDecoration {

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
        private static final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };
        private Drawable mDivider;

        public DividerDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            if (getOrientation(parent) == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (getOrientation(parent) == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }

        private int getOrientation(RecyclerView parent) {
            LinearLayoutManager layoutManager;
            try {
                layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            } catch (ClassCastException e) {
                throw new IllegalStateException("DividerDecoration can only be used with a " +
                        "LinearLayoutManager.", e);
            }
            return layoutManager.getOrientation();
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int recyclerViewTop = parent.getPaddingTop();
            final int recyclerViewBottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = Math.max(recyclerViewTop, child.getBottom() + params.bottomMargin);
                final int bottom = Math.min(recyclerViewBottom, top + mDivider.getIntrinsicHeight());
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int recyclerViewLeft = parent.getPaddingLeft();
            final int recyclerViewRight = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = Math.max(recyclerViewLeft, child.getRight() + params.rightMargin);
                final int right = Math.min(recyclerViewRight, left + mDivider.getIntrinsicHeight());
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
