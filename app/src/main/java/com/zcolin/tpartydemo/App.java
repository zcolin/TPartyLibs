/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-6-13 上午11:16
 * ********************************************************
 */

package com.zcolin.tpartydemo;

import android.app.Application;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.util.ShareSocial;
import cn.sharesdk.wechat.favorite.WechatFavorite;

/**
 *
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareSocial.setHiddenPlatForms(SinaWeibo.NAME, TencentWeibo.NAME, WechatFavorite.NAME);
    }
}
