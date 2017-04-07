# TPartyLibs
## 封装常用的第三方库
此项目包含：

1. libAmapLocation - 高德定位 
```
dependencies {
    compile 'com.github.zcolin.TPartyLibs:libAmapLocation:latest.release'
}
```
2. libShareSdk - ShareSdk 
```
dependencies {
    compile 'com.github.zcolin.TPartyLibs:libShareSdk:latest.release'
}
```
3. autoUpdate_360和autoUpdate_Baidu - 上百度市场和360市场时需要集成他们的自动更新包,这两个包使用相同的包名封装好,可以实现打包时动态引用
```
dependencies {
    //360渠道自动更新包引用
    360Compile "com.github.zcolin.TPartyLibs:autoUpdate_360:latest.release"
    //百度渠道自动更新包引用
    BaiduCompile "com.github.zcolin.TPartyLibs:autoUpdate_Baidu:latest.release"
}

productFlavors {
    Baidu {
         manifestPlaceholders = [
                        BDAPPID: "*****",
                        BDAPPKEY : "*******************************"
                ]
    }
```

