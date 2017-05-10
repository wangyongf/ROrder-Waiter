package com.yongf.rorderwaiter.model.order;

import com.yongf.rorderwaiter.model.BaseBean;

import java.util.List;

/**
 * 根据服务员编号获取订单详情
 *
 * @author Scott Wang
 * @version 1.0, 17-5-7
 * @see
 * @since ROder V1.0
 */
public class OrderDetailsResultBean extends BaseBean {

    /**
     * orders_raw_id : 123
     * orders_id : 123ADB
     * notes : 微辣, 不要香菜
     * status : 0
     * tables_id : 1
     * user_info_uid : 1234567890
     * details : [{"details_id":1,"goods_raw_id":1234567890,"goods_id":"1","name":"卤蛋","original_price":17.2,"real_price":15.1,"pictures":"此处是一段json字符串","status":0,"quantity":2},{"details_id":1,"goods_raw_id":12345678901,"goods_id":"12345678901","name":"牛肉拉面","original_price":18.5,"real_price":15,"pictures":"此处是一段json字符串","status":0,"quantity":1}]
     */

    private int orders_raw_id;
    private String orders_id;
    private String notes;
    private int status;
    private int tables_id;
    private int user_info_uid;
    private List<DetailsBean> details;

    public int getOrders_raw_id() {
        return orders_raw_id;
    }

    public void setOrders_raw_id(int orders_raw_id) {
        this.orders_raw_id = orders_raw_id;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTables_id() {
        return tables_id;
    }

    public void setTables_id(int tables_id) {
        this.tables_id = tables_id;
    }

    public int getUser_info_uid() {
        return user_info_uid;
    }

    public void setUser_info_uid(int user_info_uid) {
        this.user_info_uid = user_info_uid;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * details_id : 1
         * goods_raw_id : 1234567890
         * goods_id : 1
         * name : 卤蛋
         * original_price : 17.2
         * real_price : 15.1
         * pictures : 此处是一段json字符串
         * status : 0
         * quantity : 2
         */

        private int details_id;
        private int goods_raw_id;
        private String goods_id;
        private String name;
        private double original_price;
        private double real_price;
        private String cover;
        private String pictures;
        private int status;
        private int quantity;

        public int getDetails_id() {
            return details_id;
        }

        public void setDetails_id(int details_id) {
            this.details_id = details_id;
        }

        public int getGoods_raw_id() {
            return goods_raw_id;
        }

        public void setGoods_raw_id(int goods_raw_id) {
            this.goods_raw_id = goods_raw_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(double original_price) {
            this.original_price = original_price;
        }

        public double getReal_price() {
            return real_price;
        }

        public void setReal_price(double real_price) {
            this.real_price = real_price;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
