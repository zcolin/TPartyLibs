/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-4-7 下午3:12
 * ********************************************************
 */

package com.zcolin.autoupdate;

import android.content.Context;
import android.widget.Toast;

import com.baidu.autoupdatesdk.AppUpdateInfoForInstall;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;


/**
 * 自动更新对外开放类
 */
public class AutoUpdate {
    
    /**
     * 自动更新
     *
     * @param callback 检查完是否有更新后回调()
     */
    public static void autoUpdate(final Context context, final OnCheckComplete callback) {
        BDAutoUpdateSDK.uiUpdateAction(context, new UICheckUpdateCallback() {
            @Override
            public void onCheckComplete() {
                if (callback != null) {
                    callback.onCheckComplete(null);
                }
            }
        });
    }


    /**
     * 手动更新
     *
     * @param callback 检查完是否有更新后回调
     */
    public static void manualUpdate(final Context context, final OnCheckComplete callback) {
        BDAutoUpdateSDK.cpUpdateCheck(context, new com.baidu.autoupdatesdk.CPCheckUpdateCallback() {
                    @Override
                    public void onCheckUpdateCallback(com.baidu.autoupdatesdk.AppUpdateInfo appUpdateInfo, AppUpdateInfoForInstall appUpdateInfoForInstall) {
                        if (appUpdateInfo == null) {
                            if (callback != null) {
                                callback.onCheckComplete(null);
                            }

                            Toast toast = Toast.makeText(context, "已是最新版本了", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            AppUpdateInfo updateInfo = new AppUpdateInfo();
                            updateInfo.appName = appUpdateInfo.getAppSname();
                            updateInfo.size = appUpdateInfo.getAppSize();
                            updateInfo.versionCode = appUpdateInfo.getAppVersionCode();
                            updateInfo.versionName = appUpdateInfo.getAppVersionName();
                            updateInfo.changeLog = appUpdateInfo.getAppChangeLog();
                            updateInfo.installPath = appUpdateInfo.getAppPath();
                            if (callback != null) {
                                callback.onCheckComplete(updateInfo);
                            }

                            BDAutoUpdateSDK.uiUpdateAction(context, new com.baidu.autoupdatesdk.UICheckUpdateCallback() {
                                @Override
                                public void onCheckComplete() {

                                }
                            });
                        }
                    }
                }
        );
    }
}
