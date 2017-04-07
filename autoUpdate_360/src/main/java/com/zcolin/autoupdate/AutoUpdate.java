/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-4-7 下午3:13
 * ********************************************************
 */

package com.zcolin.autoupdate;

import android.content.Context;

import com.qihoo.updatesdk.lib.UpdateHelper;


/**
 * 自动更新对外开放类
 */
public class AutoUpdate {
    private static boolean isInit = false;

    /**
     * 自动更新
     *
     * @param callback 检查完是否有更新后回调()
     */
    public static void autoUpdate(final Context context, final OnCheckComplete callback) {
        if (!isInit) {
            UpdateHelper.getInstance()
                        .init(context, context.getResources()
                                              .getColor(context.getResources()
                                                               .getIdentifier("colorPrimary", "color", context.getPackageName())));
            isInit = true;
        }

        UpdateHelper.getInstance()
                    .autoUpdate(context.getPackageName());
        if (callback != null) {
            callback.onCheckComplete(null);
        }
    }

    /**
     * 手动更新
     *
     * @param callback 检查完是否有更新后回调()
     */
    public static void manualUpdate(final Context context, final OnCheckComplete callback) {
        if (!isInit) {
            UpdateHelper.getInstance()
                        .init(context, context.getResources()
                                              .getColor(context.getResources()
                                                               .getIdentifier("colorPrimary", "color", context.getPackageName())));
            isInit = true;
        }

        UpdateHelper.getInstance()
                    .manualUpdate(context.getPackageName());
        if (callback != null) {
            callback.onCheckComplete(null);
        }
    }
}
