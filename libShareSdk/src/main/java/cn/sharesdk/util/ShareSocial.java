/*
 * **********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     16-10-8 下午3:55
 * *********************************************************
 */

package cn.sharesdk.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 社会化分享工具类
 */
public class ShareSocial {
    private static final String[] ALLPLATFORMS = {Wechat.NAME, WechatMoments.NAME, WechatFavorite.NAME, QQ.NAME, QZone.NAME, TencentWeibo.NAME, SinaWeibo.NAME};
    private static String[] HIDDENPLATFORMS;

    private String platform;
    private boolean isShowContentEdit = true;
    private String                  title;
    private String                  content;
    private String                  targetUrl;
    private String                  imgUrl;
    private String                  comment;
    private String                  imgPath;
    private SparseArray<String>     showPlatForms;
    private ArrayList<CustomerLogo> customerLogos;


    private ShareSocial() {
    }

    /**
     * 全局设置需要隐藏的平台，可以在Application中调用
     *
     * @param hiddenPlatformName 需要隐藏的平台名称 ex: {@link cn.sharesdk.sina.weibo.SinaWeibo#NAME}
     */
    public static void setHiddenPlatForms(String... hiddenPlatformName) {
        HIDDENPLATFORMS = hiddenPlatformName;
    }

    /**
     * 产生实例
     */
    public static ShareSocial instance() {
        return new ShareSocial();
    }

    /**
     * 添加自定义的View
     */
    public ShareSocial addCustomerLogo(Bitmap logo, String label, View.OnClickListener onClickListener) {
        if (customerLogos == null) {
            customerLogos = new ArrayList<>();
        }

        CustomerLogo customerLogo = new CustomerLogo();
        customerLogo.bitmap = logo;
        customerLogo.label = label;
        customerLogo.onClickListener = onClickListener;
        customerLogos.add(customerLogo);
        return this;
    }

    /**
     * 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     */
    public ShareSocial setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    /**
     * 是否显示编辑页
     */
    public ShareSocial setIsShowContentEdit(boolean isShowContentEdit) {
        this.isShowContentEdit = isShowContentEdit;
        return this;
    }

    /**
     * title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
     */
    public ShareSocial setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 分享内容
     */
    public ShareSocial setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 指向链接地址
     */
    public ShareSocial setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
        return this;
    }

    /**
     * 图片Url
     */
    public ShareSocial setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    /**
     * 本地图片路径
     */
    public ShareSocial setImgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }

    /**
     * 我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
     */
    public ShareSocial setComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * 设置定向显示的平台，如不设置，默认为除去全局隐藏的全部平台
     */
    public ShareSocial setShowPlatform(String... showPlatForms) {
        if (showPlatForms != null && showPlatForms.length > 0) {
            this.showPlatForms = new SparseArray<>();
            for (int i = 0; i < showPlatForms.length; i++) {
                this.showPlatForms.put(i, showPlatForms[i]);
            }
        }
        return this;
    }


    /**
     * 调用执行分享
     */
    public void share(Context context) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!isShowContentEdit);
        if (platform != null) {
            oks.setPlatform(platform);
        }

        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        if (title != null) {
            oks.setTitle(title);
        }

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用  
        if (targetUrl != null) {
            oks.setTitleUrl(targetUrl);
        }

        // text是分享文本，所有平台都需要这个字段  
        if (content != null) {
            oks.setText(content);
        }

        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        if (imgUrl != null) {
            oks.setImageUrl(imgUrl);
        }

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test-pic.jpg"); 
        if (imgPath != null) {
            oks.setImagePath(imgPath);
        }

        // url仅在微信（包括好友和朋友圈）中使用  
        if (targetUrl != null) {
            oks.setUrl(targetUrl);
        }

        //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        if (comment != null) {
            oks.setComment(comment);
        }

        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite(context.getResources()
        //                           .getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        if (targetUrl != null) {
            oks.setSiteUrl(targetUrl);
        }

        // oks.setVenueDescription("This is a beautiful place!");
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        //oks.setCallback(new OneKeyShareCallback());
        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

        // 在九宫格设置自定义的图标
        if (customerLogos != null && customerLogos.size() > 0) {
            for (CustomerLogo customerLogo : customerLogos) {
                Bitmap logo = customerLogo.bitmap;
                String label = customerLogo.label;
                View.OnClickListener listener = customerLogo.onClickListener;
                oks.setCustomerLogo(logo, label, listener);
            }
        }

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        //  隐藏九宫格中平台
        if (showPlatForms != null && showPlatForms.size() > 0) {
            for (String platForm : ALLPLATFORMS) {
                if (showPlatForms.indexOfValue(platForm) < 0) {
                    oks.addHiddenPlatform(platForm);
                }
            }
        } else if (HIDDENPLATFORMS != null && HIDDENPLATFORMS.length > 0) {
            for (String s : HIDDENPLATFORMS) {
                oks.addHiddenPlatform(s);
            }
        }

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS); //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }


    private static class CustomerLogo {
        private Bitmap               bitmap;
        private String               label;
        private View.OnClickListener onClickListener;
    }
}
