package mvpdemo.com.unmeng_share_librarys;

/**
 * 监听请求服务的接口
 * @author Administrator
 */
public interface ShareCodeListener {
	/**
	 * 分享收藏
	 */
	public static final int SHARE_FAVORITE = 0x971;
	/**
	 * 分享成功
	 */
	public static final int SHARE_SUCCESS = 0x970;
	/**
	 * 分享失败
	 */
	public static final int SHARE_FAIL = 0x969;
	/**
	 * 分享取消
	 */
	public static final int SHARE_CANCLE = 0x968;
	/**
	 * 分享拒绝
	 */
	public static final int SHARE_REFUSE = 0x967;
}
