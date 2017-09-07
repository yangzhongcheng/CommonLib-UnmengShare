package mvpdemo.com.unmeng_share_librarys;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**获取用户信息
 * @ClassName: mvpdemo.com.unmeng_share_librarys
 * @author: Administrator 杨重诚
 * @date: 2016/11/15:15:23
 */

public class UmengUserInfo {
    private UMShareAPI mShareAPI = null;

    /**
     * 授权
     * @param context
     * @param platform
     * @param umAuthListener
     */
    public  void getPlatformInfo(Context context,SHARE_MEDIA platform,UMAuthListener umAuthListener){
        mShareAPI = UMShareAPI.get(context);
        mShareAPI.getPlatformInfo((Activity) context, platform, umAuthListener);
    }
}
