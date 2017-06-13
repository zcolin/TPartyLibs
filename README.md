# TPartyLibs
## 封装常用的第三方库
此项目包含：

1. libAmapLocation - 高德定位 
```
dependencies {
    compile 'com.github.zcolin.TPartyLibs:libAmapLocation:latest.release'
}

AndroidManifest.xml
<meta-data
    android:name="com.amap.api.v2.apikey"
    android:value="b1452dfd87b5777103a64ae2c6537645"/>
```
2. libShareSdk - ShareSdk 
```
dependencies {
    compile 'com.github.zcolin.TPartyLibs:libShareSdk:latest.release'
}

在AndroidManifest.xml中增加
<meta-data android:name="Mob-AppKey" android:value="你的key"/>
<meta-data android:name="Mob-AppSecret" android:value="你得Secret"/>

ShareSDK.xml
在assets中新建ShareSDK.xml文件
文件内容参照libShareSdk->assets中的ShareSDK_demo.xml

//如果需要全局隐藏某些平台，在Application中调用ShareSocial.setHiddenPlatForms(String...);
//如果需要全局增加自定义logo，在Application中调用ShareSocial.addCustomerLogo(Bitmap, String, View.OnClickListener);

ShareSocial.instance()
               .setTitle("分享")
               .setContent("分享内容")
               .setTargetUrl("http://www.baidu.com")
               .setImgUrl("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg")
               .setShowPlatform(Wechat.NAME, WechatMoments.NAME)//不调用此函数则默认全部
               .share(mActivity);
```
3. autoUpdate_360和autoUpdate_Baidu - 上百度市场和360市场时需要集成他们的自动更新包,这两个包使用相同的包名封装好,可以实现打包时动态引用
```
dependencies {
    //360渠道自动更新包引用
    360Compile "com.github.zcolin.TPartyLibs:autoUpdate_360:latest.release"
    //百度渠道自动更新包引用
    BaiduCompile "com.github.zcolin.TPartyLibs:autoUpdate_Baidu:latest.release"
}


AndroidManifest.xml
<meta-data
    android:name="BDAPPID"
    android:value="百度AppId" />
<meta-data
    android:name="BDAPPKEY"
    android:value="百度AppKey" />
```

