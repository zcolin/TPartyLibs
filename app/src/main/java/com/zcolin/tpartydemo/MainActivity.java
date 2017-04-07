/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-4-7 上午11:56
 * ********************************************************
 */

package com.zcolin.tpartydemo;

import android.Manifest;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.zcolin.frame.app.BaseFrameActivity;
import com.zcolin.frame.permission.PermissionHelper;
import com.zcolin.frame.permission.PermissionsResultAction;
import com.zcolin.frame.utils.ToastUtil;
import com.zcolin.gui.ZAlert;
import com.zcolin.gui.ZConfirm;
import com.zcolin.gui.ZDialog;
import com.zcolin.libamaplocation.LocationUtil;

import java.util.ArrayList;

import cn.sharesdk.util.ShareSocial;

public class MainActivity extends BaseFrameActivity implements View.OnClickListener {

    private LinearLayout llContent;
    private ArrayList<Button> listButton = new ArrayList<>();
    private MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        init();
    }


    private void init() {
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        listButton.add(addButton("定位"));
        listButton.add(addButton("分享"));

        for (Button btn : listButton) {
            btn.setOnClickListener(this);
        }
    }

    private Button addButton(String text) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = new Button(mActivity);
        button.setText(text);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        llContent.addView(button, params);
        return button;
    }

    private void location() {
        PermissionHelper.requestPermission(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
            @Override
            public void onGranted() {

                final LocationUtil location = new LocationUtil(mActivity);
                location.startLocation(new LocationUtil.OnGetLocation() {

                    @Override
                    public void getLocation(AMapLocation location) {

				        /*设置位置描述*/
                        String desc = null;
                        Bundle locBundle = location.getExtras();
                        if (locBundle != null) {
                            desc = locBundle.getString("desc");
                        }

                        new ZAlert(mActivity).setMessage(locBundle == null ? location.getCity() + location.getDistrict() : desc)
                                             .show();
                    }

                    @Override
                    public void locationFail() {
                        ZConfirm dlg = new ZConfirm(mActivity);
                        dlg.setTitle("定位失败, 是否尝试再次定位？")
                           .addSubmitListener(new ZDialog.ZDialogSubmitInterface() {

                               @Override
                               public boolean submit() {
                                   location();
                                   return true;
                               }
                           });
                        dlg.addCancelListener(new ZDialog.ZDialogCancelInterface() {
                            @Override
                            public boolean cancel() {
                                return true;
                            }
                        });
                        dlg.show();
                    }
                });
            }

            @Override
            public void onDenied(String permission) {
                ToastUtil.toastShort("请授予本程序[定位]和[写SD卡权限]");
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == listButton.get(0)) {
            location();
        } else if (v == listButton.get(1)) {
            new ShareSocial(mActivity).setTitle("分享")
                                      .setContent("分享内容")
                                      .setTargetUrl("http://www.baidu.com")
                                      .setImgUrl("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg")
                                      .share();
        }
    }
}