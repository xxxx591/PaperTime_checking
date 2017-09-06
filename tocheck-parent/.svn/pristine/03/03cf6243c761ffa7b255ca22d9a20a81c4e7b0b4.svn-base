package com.tocheck.parent.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;


/**
 * 微博登录相关工具类
 *
 * @author Andy
 *         2016年8月4日 下午4:45:57
 */
public class WeiboUtil {
    private static final Logger logger = LoggerFactory.getLogger(WeiboUtil.class);
    //获取access_token
    public static final String URI_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token?client_id={0}&client_secret={1}&grant_type=authorization_code&redirect_uri={2}&code={3}";

    //获取微博用户信息
    public static final String URI_USER_INFO = "https://api.weibo.com/2/users/show.json?access_token={0}&uid={1}";

    //微博退出登录
    public static final String URI_USER_LOGOUT = "https://api.weibo.com/oauth2/revokeoauth2?access_token={0}";


    /**
     * 获取code
     *
     * @param appId
     * @param redirectUri
     * @param code
     * @return
     */
    public static String getAccessToken(String appId, String appSecret, String redirectUri, String code) {
        String uri = MessageFormat.format(URI_ACCESS_TOKEN, appId, appSecret, redirectUri, code);
        return HttpClientUtil.doHttpPost(uri, null);
    }

    /**
     * 获取微博用户信息
     *
     * @param accessToken
     * @param uid
     * @return
     */
    public static String getUserInfo(String accessToken, String uid) {
        String uri = MessageFormat.format(URI_USER_INFO, accessToken, uid);
        return HttpClientUtil.doHttpGet(uri);
    }

    /**
     * 微博退出登录
     *
     * @param accessToken
     * @return
     */
    public static String logOut(String accessToken) {
        String uri = MessageFormat.format(URI_USER_LOGOUT, accessToken);
        return HttpClientUtil.doHttpGet(uri);
    }

    /**
     * 微博登录
     *
     * @param appId
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String weiBoOauthUrl(String appId, String url)
            throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        //https://api.weibo.com/oauth2/authorize?client_id=YOUR_CLIENT_ID&response_type=code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI
        buffer.append("https://api.weibo.com/oauth2/authorize?client_id=");
        buffer.append(appId).append("&");
        buffer.append("redirect_uri=");
        buffer.append(URLEncoder.encode(url, "UTF-8")).append("&");
        buffer.append("response_type=code");
        return buffer.toString();
    }

    /**
     * 微博登录 客户端登录新增
     *
     * @param appId
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String weiBoOauthUrl(String appId, String url, String state)
            throws UnsupportedEncodingException {
        StringBuilder buffer = new StringBuilder(250);
        //https://api.weibo.com/oauth2/authorize?client_id=YOUR_CLIENT_ID&response_type=code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI
        buffer.append("https://api.weibo.com/oauth2/authorize?client_id=");
        buffer.append(appId).append("&");
        buffer.append("redirect_uri=");
        buffer.append(URLEncoder.encode(url, "UTF-8")).append("&");
        buffer.append("state=").append(state).append("&");
        buffer.append("forcelogin=").append(true);
        return buffer.toString();
    }
}
