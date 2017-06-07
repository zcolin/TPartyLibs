package com.tyr.libbaidumaplocation;

import android.content.Context;
import android.os.Handler;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BaiduMapLocationUtil implements Runnable, BDLocationListener {

    public static final int FAILURE_DELAYTIME = 12000; // 定位超时时间
    public static BDLocation LOCATION;                 // 获取到的定位位置

    private Context              context;
    private LocationClient       mLocationClient;
    private LocationClientOption mLocationOption;

    private Handler handler = new Handler();
    private BDLocation    mLocation;
    private OnGetLocation onGetLocation;


    public BaiduMapLocationUtil(Context context) {
        this.context = context;
    }

    /**
     * 开始定位
     *
     * @param onGetLocation 定位完成回调接口
     */
    public void startLocation(OnGetLocation onGetLocation) {
        this.onGetLocation = onGetLocation;
        startLocation();
    }

    public void startLocation() {
        if (mLocationOption == null) {
            mLocationOption = new LocationClientOption();
            mLocationOption.setOpenGps(true);             // 是否打开GPS
            mLocationOption.setCoorType("bd09ll");        // 设置返回值的坐标类型
            mLocationOption.setScanSpan(5000);            // 设置定时定位的时间间隔（单位毫秒）
            mLocationOption.setIsNeedAddress(true);       // 可选，设置是否需要地址信息（默认不需要）
            mLocationOption.setIgnoreKillProcess(false);  // 可选，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程（默认不杀死true）
        }
        mLocationClient = new LocationClient(context);
        mLocationClient.setLocOption(mLocationOption);
        mLocationClient.registerLocationListener(this);
        mLocationClient.start();
        handler.postDelayed(this, FAILURE_DELAYTIME);     // 设置超过12秒还没有定位到就停止定位

        /*
         * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation(
         * )后，每隔设定的时间，定位SDK就会进行一次定位。如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，
         * 返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
         * 定时定位时，调用一次requestLocation，会定时监听到定位结果。
         */
        mLocationClient.requestLocation();
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null) {
            return;
        }

        if (location != null) {
            this.mLocation = location;// 判断超时机制
            if (onGetLocation != null) {
                if (location.getLatitude() == 0 && location.getLongitude() == 0) {
                    onGetLocation.locationFail();
                    LOCATION = null;
                } else {
                    onGetLocation.getLocation(location);
                    LOCATION = location;
                }
            }
            stop();
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    /**
     * 12秒内还没有定位成功即停止定位
     */
    @Override
    public void run() {
        if (mLocation == null) {
            stopLocation(); // 销毁掉定位
            if (onGetLocation != null) {
                onGetLocation.locationFail();
            }
        }
    }

    /**
     * 结束定位，连本次的回调任务也取消
     */
    public void stop() {
        handler.removeCallbacks(this);
        stopLocation();
    }

    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        mLocationClient = null;
    }

    /**
     * 获取位置后回调
     */
    public interface OnGetLocation {
        void getLocation(BDLocation location);

        void locationFail();
    }

}
