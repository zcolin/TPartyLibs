/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 上午10:26
 * ********************************************************
 */

package com.zcolin.tpartydemo;

import com.zcolin.frame.app.BaseApp;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.util.ShareSocial;
import cn.sharesdk.wechat.favorite.WechatFavorite;

/**
 *
 */

public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareSocial.setHiddenPlatForms(SinaWeibo.NAME, TencentWeibo.NAME, WechatFavorite.NAME);
    }
}
