package mvpdemo.com.unmeng_share_librarys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN_CIRCLE;

/**友盟分享
 * @ClassName: mvpdemo.com.unmeng_share_librarys
 * @author: Administrator 杨重诚
 * @date: 2016/11/7:15:56
 */

public class UmengShare {
    Context mContext;
    ShareLintener shareLintener;
    UMImage image ;
    String msgTitle="分享了...哈哈";
    String msgText="";
    String msgLink="";
    private ProgressDialog dialog;
    /**
     * 默认分享面板
     * @param context
     * @param msgTitles
     * @param msgTexts
     * @param msgLinks
     * @param imgUris
     * @param shareLintener
     */
    public void openShare( Context context, String msgTitles,  String msgTexts, String msgLinks,
                           String imgUris,ShareLintener shareLintener){
        mContext=context;
        this.shareLintener=shareLintener;
        image = new UMImage(mContext,imgUris);
        this.msgTitle=msgTitles;
        this.msgText=msgTexts;
        this.msgLink=msgLinks;
        new ShareAction((Activity) mContext).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)
                .addButton("umeng_mobile_btn","umeng_mobile_btn","icon_logo_mobile","icon_logo_mobile")
                .addButton("umeng_copylink_btn","umeng_copylink_btn","icon_logo_copy_link","icon_logo_copy_link")
                .setShareboardclickCallback(mShareBoardlistener).open();
    }
    /**
     * 默认分享面板
     * @param context
     * @param msgTitles
     * @param msgTexts
     * @param msgLinks
     * @param images
     * @param shareLintener
     */
    public void openShare( Context context, String msgTitles,  String msgTexts, String msgLinks,
                           int images,ShareLintener shareLintener){
        mContext=context;
        this.shareLintener=shareLintener;
        image = new UMImage(context,images);
        this.msgTitle=msgTitles;
        this.msgText=msgTexts;
        this.msgLink=msgLinks;
        new ShareAction((Activity) mContext).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.WEIXIN_FAVORITE)
                .setShareboardclickCallback(mShareBoardlistener).open();
    }
    /**
     * 默认分享面板
     * @param context
     * @param msgTitles
     * @param msgTexts
     * @param msgLinks
     * @param image
     * @param shareLintener
     */
    public void openShare( Context context, String msgTitles,  String msgTexts, String msgLinks,
                           UMImage image,ShareLintener shareLintener){
        mContext=context;
        this.shareLintener=shareLintener;
        this.image = image;
        this.msgTitle=msgTitles;
        this.msgText=msgTexts;
        this.msgLink=msgLinks;
        new ShareAction((Activity) mContext).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.WEIXIN_FAVORITE)
                .setShareboardclickCallback(mShareBoardlistener).open();
    }
    /**
     * 根据渠道设置分享面板
     * @param context
     * @param msgTitles
     * @param msgTexts
     * @param msgLinks
     * @param imgUris
     * @param sharePlatement
     * @param shareLintener
     */
    public void openShare( Context context, String msgTitles,  String msgTexts, String msgLinks,
                           String imgUris,String sharePlatement,ShareLintener shareLintener){
        mContext=context;
        this.shareLintener=shareLintener;
        image = new UMImage(mContext,imgUris);
        this.msgTitle=msgTitles;
        this.msgText=msgTexts;
        this.msgLink=msgLinks;
        ShareAction shareAction=getShareAction(getPlatement(sharePlatement),new ShareAction((Activity) mContext));
        shareAction.setShareboardclickCallback(mShareBoardlistener).open();
    }

    /**
     * 根据渠道设置分享面板
     * @param context
     * @param imgUris
     * @param sharePlatement
     * @param shareLintener
     */
    public void openShare( Context context,String imgUris,String sharePlatement,ShareLintener shareLintener){
        mContext=context;
        this.shareLintener=shareLintener;
        image = new UMImage(mContext,imgUris);
        //对于微信QQ的那个平台，分享的图片需要设置缩略图，缩略图的设置规则为：
        UMImage thumb =  new UMImage(mContext, imgUris);
        image.setThumb(thumb);
        //用户设置的图片大小最好不要超过250k，缩略图不要超过18k，如果超过太多（最好不要分享1M以上的图片，压缩效率会很低），图片会被压缩。 用户可以设置压缩的方式：
        //image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享

        ShareAction shareAction=getShareAction(getPlatement(sharePlatement),new ShareAction((Activity) mContext));
        shareAction.setShareboardclickCallback(mShareImageBoardlistener).open();
    }

    /**
     * 分享链接的点击平台事件
     */
    public ShareBoardlistener mShareBoardlistener=new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            dialog=new ProgressDialog(mContext);
            if(snsPlatform.mShowWord.equals("umeng_mobile_btn")){
                sendSMS(msgText+msgLink);// 发送短信
            }else if(snsPlatform.mShowWord.equals("umeng_copylink_btn")){
                copyLink(msgLink);
            }else{
                //检测是否有安装应用
                if(!checkApplication(mContext,share_media)){
                    return;
                }
                UMWeb  web = new UMWeb(msgLink);
                web.setTitle(msgTitle);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(msgText);//描述
                new ShareAction((Activity) mContext)
                        .withMedia(web)
                        .setPlatform(share_media)
                        .setCallback(umShareListener)
                        .share();
            }
        }
    };
    /**
     * 分享图片的点击平台事件
     */
    public ShareBoardlistener mShareImageBoardlistener=new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            dialog=new ProgressDialog(mContext);
            if(snsPlatform.mShowWord.equals("umeng_mobile_btn")){
                sendSMS(msgText+msgLink);// 发送短信
            }else if(snsPlatform.mShowWord.equals("umeng_copylink_btn")){
                copyLink(msgLink);
            }else{
                //检测是否有安装应用
                if(!checkApplication(mContext,share_media)){
                 return;
                }
                new ShareAction((Activity) mContext)
                        .withMedia(image)
                        .setPlatform(share_media)
                        .setCallback(umShareListener)
                        .share();
            }
        }
    };


    /**
     * 获取分享渠道String【】
     * @param sharePlatement
     * @return
     */
    private String[] getPlatement(String sharePlatement){
        if(sharePlatement != null && !sharePlatement.equals("")){
            if (sharePlatement.contains("|")){
                sharePlatement = sharePlatement.replace("|", ",");
                String[] stri = sharePlatement.split(",");
                return stri;
            }
    }
        return null;
}

    /**
     * 获取友盟分享渠道面板
     * @param sharePlatement
     * @return
     */
    private ShareAction getShareAction(String[] sharePlatement,ShareAction shareAction){
        if(sharePlatement!=null&&sharePlatement.length>0){
            //存放默认平台
            List<SHARE_MEDIA> shareMediaList=new ArrayList<>();
            for(int i=0;i<sharePlatement.length;i++){
                switch (sharePlatement[i]) {
                    case "WX":// 微信
                        shareMediaList.add(SHARE_MEDIA.WEIXIN);
                        break;
                    case "WXCircle":// 朋友圈
                        shareMediaList.add(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case "QQFriend":// QQ好友
                        shareMediaList.add(SHARE_MEDIA.QQ);
                        break;
                    case "QQZone":// QQ空间
                        shareMediaList.add(SHARE_MEDIA.QZONE);
                        break;
                    case "Sina":// 新浪微博
                        shareMediaList.add(SHARE_MEDIA.SINA);
                        break;
                    default:
                        break;
                }
            }
            //封装默认分享平台
            SHARE_MEDIA[] displaylist=new SHARE_MEDIA[shareMediaList.size()];
            for(int i=0;i<shareMediaList.size();i++){
                displaylist[i]=shareMediaList.get(i);
            }
            shareAction.setDisplayList(displaylist);//添加分享面板渠道

            //判断是否有自定义分享平台
            for(int i=0;i<sharePlatement.length;i++){
                switch (sharePlatement[i]) {
                    case "Tele":// 手机联系
                        shareAction.addButton("umeng_mobile_btn","umeng_mobile_btn","icon_logo_mobile","icon_logo_mobile");//自定义分享面板
                        break;
                    case "CopyLink":// 复制链接
                        shareAction .addButton("umeng_copylink_btn","umeng_copylink_btn","icon_logo_copy_link","icon_logo_copy_link");//自定义分享面板
                        break;
                    default:
                        break;
                }
            }
            return shareAction;
        }
        return null;
    }

    /**
     * 分享微信朋友圈
     * @param context
     * @param msgTitles
     * @param msgTexts
     * @param msgLinks
     * @param imgUris
     * @param shareLintener
     */
    public void shareWEIXIN_CIRCLE( Context context, String msgTitles,  String msgTexts, String msgLinks,
                           String imgUris,ShareLintener shareLintener){
        mContext=context;
        this.shareLintener=shareLintener;
        this.msgTitle=msgTitles;
        this.msgText=msgTexts;
        this.msgLink=msgLinks;
        image = new UMImage(mContext,imgUris);
        dialog=new ProgressDialog(mContext);

        UMWeb  web = new UMWeb(msgLink);
        web.setTitle(msgTitle);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(msgText);//描述
        new ShareAction((Activity) mContext)
                .withMedia(web)
                .setPlatform(WEIXIN_CIRCLE)
                .setCallback(umShareListener)
                .share();
    }

    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
            SocializeUtils.safeShowDialog(dialog);
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if(platform.name().equals("WEIXIN_FAVORITE")){
                //收藏成功啦
                if(shareLintener!=null)
                shareLintener.onResult(platform,ShareCode.SHARE_FAVORITE);
            }else{
                //分享成功啦
                if(shareLintener!=null) {
                    shareLintener.onResult(platform, ShareCode.SHARE_SUCCESS);
                }else {
                }
            }
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            //分享失败啦
            if(shareLintener!=null)
            shareLintener.onResult(platform,ShareCode.SHARE_FAIL);
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            //分享取消了
            if(shareLintener!=null)
            shareLintener.onResult(platform,ShareCode.SHARE_CANCLE);
            SocializeUtils.safeCloseDialog(dialog);
        }
    };

    /**
     * 发送短信
     *
     * @param smsBody
     *            短信内容
     */
    private void sendSMS(String smsBody) {

        Uri smsToUri = Uri.parse("smsto:");

        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

        intent.putExtra("sms_body", smsBody);

        mContext.startActivity(intent);

    }

    /**
     * 复制链接
     */
    @SuppressWarnings("deprecation")
    private void copyLink(String msgContent) {
        ClipboardManager cmb = (ClipboardManager) mContext
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(msgContent.trim()); // 将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
        Toast.makeText(mContext, "链接复制成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测该包名所对应的应用是否存在
     *
     * @param packageName
     * @return
     */
    public  boolean checkPackage(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES );
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkApplication(Context context,SHARE_MEDIA share_media) {
        if (share_media == SHARE_MEDIA.WEIXIN || share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            //手机没有安装微信
            if (!checkPackage(context, "com.tencent.mm")) {
                Toast.makeText(context, "请安装微信客户端", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (share_media == SHARE_MEDIA.QQ || share_media == SHARE_MEDIA.QZONE) {
            //手机没有安装QQ
            if (!checkPackage(context, "com.tencent.mobileqq")) {
                Toast.makeText(context, "请安装QQ客户端", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (share_media == SHARE_MEDIA.SINA) {
            //手机没有安装微博
            if (!checkPackage(context, "com.sina.weibo")) {
                Toast.makeText(context, "请安装微博客户端", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}
