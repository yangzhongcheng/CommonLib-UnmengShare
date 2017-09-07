package mvpdemo.com.unmeng_share_librarys;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @ClassName: mvpdemo.com.unmeng_share_librarys
 * @author: Administrator 杨重诚
 * @date: 2016/11/7:16:08
 */

public interface ShareLintener {
    public void onResult(SHARE_MEDIA platform,ShareCode shareCode);
}
