/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: AppEnv						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-15       新增：Create	
 */

package com.yongf.rorderwaiter.app.application;

import com.yongf.rorderwaiter.net.UrlCenter;
import com.yongf.rorderwaiter.widget.toast.UserToast;

/**
 * App环境
 *
 * @author Scott Wang
 * @version 1.0, 17-1-15
 * @see
 * @since ROder V0.1
 */
public final class AppEnv {

    private static String sMainSite = UrlCenter.ALIYUN_SITE;
    private static UserToast sUserToast;

    public static String getMainSite() {
        return sMainSite;
    }

    public static void setMainSite(String mainSite) {
        sMainSite = mainSite;
    }

    public static UserToast getUserToast() {
        return sUserToast;
    }

    public static void setUserToast(UserToast userToast) {
        sUserToast = userToast;
    }
}
