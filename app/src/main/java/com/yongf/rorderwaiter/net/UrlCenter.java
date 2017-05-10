package com.yongf.rorderwaiter.net;

/**
 * 存放各种url常量
 *
 * @author Scott Wang
 * @version 1.0, 17-5-2
 * @see
 * @since ROder V1.0
 */
public class UrlCenter {

    //主站链接
    public static final String ALIYUN_SITE = "http://121.42.59.52/ROrder/public";
    public static final String LOCAL_SITE = "http://192.168.2.107/ps/ROrder/public";
    public static final String LOCALHOST_SITE = "http://localhost/ps/ROrder/public";
    public static final String EMULATOR_SITE = "http://10.0.2.2/ps/ROrder/public";

    /////// ------------------- restaurant ------------------- ///////

    //服务员端获取订单信息
    ///api/v1/waiter/{id}/order
    public static final String ORDER_DETAILS_PREFIX = "/api/v1/waiter/";
    public static final String ORDER_DETAILS_SUFFIX = "/order";
    //根据OrderDetailId获取相应订单详情
    ///api/v1/order_detail/get/
    public static final String ORDER_DETAIL = "/api/v1/order_detail/get/";

    /////// ------------------- order ------------------- ///////

    //更新上菜进度
    ///api/v1/dish_schedule/update
    public static final String UPDATE_DISH_SCHEDULE = "/api/v1/dish_schedule/update";
}
