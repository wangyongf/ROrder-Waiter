package com.yongf.rorderwaiter.model.order;

import com.yongf.rorderwaiter.model.BaseBean;

/**
 * 更新上菜进度请求体
 *
 * @author Scott Wang
 * @version 1.0, 17-5-10
 * @see
 * @since ROder V1.0
 */
public class UpdateDishScheduleBodyBean extends BaseBean {

    /**
     * order_details_id : 456
     * schedule : 0
     */

    private int order_details_id;
    private int schedule;

    public int getOrder_details_id() {
        return order_details_id;
    }

    public void setOrder_details_id(int order_details_id) {
        this.order_details_id = order_details_id;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }
}
