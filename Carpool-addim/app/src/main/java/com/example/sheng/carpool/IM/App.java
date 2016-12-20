package com.example.sheng.carpool.IM;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Santa on 16/12/19.
 */

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        RongIM.init(this);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String id) {
                return new UserInfo(id, id, Uri.parse("http://a2.qpic.cn/psb?/1c8fb06f-76b9-4f11-a9b9-a31ce2b2be7a/t*gLmxQ*TTNXByS6eaw3yVc3tCGutYVoREuf2pJ72mA!/b/dHgBAAAAAAAA&bo=jwCAAAAAAAADBy0!&rf=viewer_4"));
            }
        }, true);
    }


//     <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
//     <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
//     在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
//
//     @param token    从服务端获取的用户身份令牌（Token）。
//     @param callback 连接回调。
//     @return RongIM  客户端核心类的实例。

    public void connect(String token) {

        if (true) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


}
