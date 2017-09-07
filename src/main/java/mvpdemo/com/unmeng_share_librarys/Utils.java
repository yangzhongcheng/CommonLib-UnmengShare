package mvpdemo.com.unmeng_share_librarys;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * @ClassName: mvpdemo.com.unmeng_share_librarys
 * @author: Administrator 杨重诚
 * @date: 2016/11/15:16:10
 */

public class Utils {
    /**
     * 请求权限
     * @param context
     */
    public static void requestSelfPermission(Context context){
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions((Activity) context,mPermissionList,123);
        }
    }
}
