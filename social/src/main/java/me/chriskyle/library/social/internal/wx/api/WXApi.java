package me.chriskyle.library.social.internal.wx.api;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.chriskyle.library.social.internal.net.RequestManager;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/11/21.
 */
public final class WXApi {

    private static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String WX_GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    public interface Callback {

        void onComplete(Map<String, String> data);

        void onError(String msg);
    }

    /**
     * 获取access_token
     *
     * @param wxAppId     wx appid
     * @param wxAppSecret wx appsecret
     * @param code        调用微信登录获取的code
     * @param callback
     */
    public static void getAccessToken(String wxAppId, String wxAppSecret,
                                      String code,
                                      final Callback callback) {
        String access_token_url = WX_ACCESS_TOKEN_URL + "?appid=" + wxAppId + "&secret="
                + wxAppSecret + "&code=" + code + "&grant_type=authorization_code";

        //获取access token
        RequestManager.doGet(access_token_url, new RequestManager.HttpResponseCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                if (response == null || response.length() == 0) {
                    callback.onError("null respone");
                    return;
                }

                if (response.optString("access_token") == null || response.optString("access_token").length() == 0) {
                    callback.onError("errcode=" + response.optString("errcode") + " errmsg=" + response.optString("errmsg"));
                    return;
                }

                Map<String, String> data = new HashMap<String, String>();
                String[] keys = {"access_token", "expires_in", "refresh_token", "openid", "scope"};
                for (int i = 0; i < keys.length; i++) {
                    data.put(keys[i], response.optString(keys[i]));
                }

                callback.onComplete(data);
            }

            @Override
            public void onFailure() {
                callback.onError("error net");
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param openid       openid
     * @param access_token access_token
     * @param callback
     */
    public static void getUserInfo(String openid, String access_token,
                                   final Callback callback) {

        String get_user_info_url = WX_GET_USER_INFO_URL + "?access_token=" + access_token
                + "&openid=" + openid;

        //获取userinfo
        RequestManager.doGet(get_user_info_url, new RequestManager.HttpResponseCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                if (response == null || response.length() == 0) {
                    callback.onError("null respone");
                    return;
                }

                if (response.optString("openid") == null || response.optString("openid").length() == 0) {
                    callback.onError("errcode=" + response.optString("errcode") + " errmsg=" + response.optString("errmsg"));
                    return;
                }


                Map<String, String> data = new HashMap<String, String>();
                String[] keys = {"openid", "nickname", "gender", "province", "city", "country", "headimgurl", "unionid"};
                for (int i = 0; i < keys.length; i++) {
                    data.put(keys[i], response.optString(keys[i]));
                }

                callback.onComplete(data);
            }

            @Override
            public void onFailure() {
                callback.onError("error net");
            }
        });
    }
}
