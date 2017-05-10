package com.yongf.rorderwaiter.model.order;

import com.yongf.rorderwaiter.model.BaseBean;

/**
 * 更新上菜进度返回结果
 *
 * @author Scott Wang
 * @version 1.0, 17-5-10
 * @see
 * @since ROder V1.0
 */
public class UpdateDishScheduleResultBean extends BaseBean {

    /**
     * result : 0
     */

    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
