package com.tocheck.parent.common.util;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;


/**
 * 微信登录相关工具类
 *
 * @author Andy
 *         2016年8月3日 下午5:57:36
 */
@Component
public class WechatUtil {

    //获取access_token
    public static final String URI_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";

    //获取微信用户信息
    public static final String URI_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}";

    //授权地址
    public static final String OAUTH2_URL = "https://open.weixin.qq.com/connect/qrconnect?appid={0}&redirect_uri={1}&response_type=code&scope=snsapi_login&state={2}#wechat_redirect";


    public String getOauth2Url(String appid,String redirect_uri,long agentId){
        return  MessageFormat.format(OAUTH2_URL, appid, redirect_uri,agentId);
    }
    /**
     * 获取code
     *
     * @param appId
     * @param code
     * @return
     */
    public String getAccessToken(String appId, String secret, String code) {
        String uri = MessageFormat.format(URI_ACCESS_TOKEN, appId, secret, code);
        return HttpClientUtil.doHttpGet(uri);
    }

    /**
     * 获取微信用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public String getUserInfo(String accessToken, String openId) {
        String uri = MessageFormat.format(URI_USER_INFO, accessToken, openId);
        return HttpClientUtil.doHttpGet(uri);
    }
}
