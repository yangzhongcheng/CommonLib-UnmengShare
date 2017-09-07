package mvpdemo.com.unmeng_share_librarys;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @ClassName: mvpdemo.com.unmeng_share_librarys
 * @author: Administrator 杨重诚
 * @date: 2016/11/15:15:23
 */

public class UmengAuth {
    private UMShareAPI mShareAPI = null;

    /**
     * 授权
     * @param context
     * @param platform
     * @param umAuthListener
     */
    public  void doOauthVerify(Context context,SHARE_MEDIA platform,UMAuthListener umAuthListener){
        mShareAPI = UMShareAPI.get(context);
        mShareAPI.doOauthVerify((Activity) context, platform, umAuthListener);
    }

    /**
     * 解除授权
     * @param context
     * @param platform
     * @param umAuthListener
     */
    public  void deleteOauth(Context context,SHARE_MEDIA platform,UMAuthListener umAuthListener){
        mShareAPI = UMShareAPI.get(context);
        mShareAPI.deleteOauth((Activity) context, platform, umAuthListener);
    }
}
