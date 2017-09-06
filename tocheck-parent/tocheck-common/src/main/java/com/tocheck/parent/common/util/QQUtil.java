package com.tocheck.parent.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

/**
 * Created by kchen on 2016-04-01.
 */
public class QQUtil {

    private static final Logger logger = LoggerFactory.getLogger(QQUtil.class);

    public static String getAccessToken(String qqAppId, String qqAppKey, String code, String redirectUrl, String state)
            throws UnsupportedEncodingException {
        String accessUrl = qqAccessUrl(qqAppId, qqAppKey, code, redirectUrl, state);
        String result = httpQQRequest(accessUrl, "GET", null);
        if (StringUtils.isNoneBlank(result)) {
            String[] aes = StringUtils.split(result, "&");
            for (String s : aes) {
                if (StringUtils.containsAny(s, "access_token")) {
                    String[] tos = StringUtils.split(s, "=");
                    logger.info("当前access_token={}", tos[1]);
                    return tos[1];
                }
            }
        }
        return null;
    }

    public static JSONObject getOpenId(String token) {
        String openIdUrl = "https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN";
        String url = openIdUrl.replace("YOUR_ACCESS_TOKEN", token);
        String result = httpQQRequest(url, "GET", null);
        String json = StringUtils.substring(result, StringUtils.indexOf(result, "(") + 1,
                StringUtils.indexOf(result, ")"));
        JSONObject jsonObject = JSONObject.parseObject(json);
        logger.info("当前token={}", jsonObject.toString());
        return jsonObject;
    }

    public static JSONObject getUserInfo(String token, String appId, String openId) {
        String userInfoUrl = "https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID";
        String url = userInfoUrl.replace("YOUR_ACCESS_TOKEN", token).replace("YOUR_APP_ID", appId)
                .replace("YOUR_OPENID", openId);
        JSONObject jsonObject = httpRequest(url, "GET", null);
        logger.info("当前用户信息userInfo={}", jsonObject.toString());
        return jsonObject;
    }

    /**
     * 发起https请求并获取
     *
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    public static String httpQQRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            logger.error("error httpQQRequest", ce);
        } catch (Exception e) {
            logger.error("error httpQQRequest", e);
        }
        return null;
    }

    public static String qqOauthUrl(String appId, String url, String scope, long state)
            throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        // https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=[YOUR_APPID]&redirect_uri=[YOUR_REDIRECT_URI]&scope=[THE_SCOPE]
        buffer.append("https://graph.qq.com/oauth2.0/authorize?client_id=");
        buffer.append(appId).append("&");
        buffer.append("redirect_uri=");
        buffer.append(URLEncoder.encode(url, "UTF-8")).append("&");
        buffer.append("response_type=code&");
        buffer.append("scope=").append(scope).append("&");
        if (state != 0) {
            buffer.append("state=").append(state);
        }
        return buffer.toString();
    }

    // https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=[YOUR_APP_ID]&client_secret=[YOUR_APP_Key]&code=[The_AUTHORIZATION_CODE]&state=[The_CLIENT_STATE]&redirect_uri=[YOUR_REDIRECT_URI]
    public static String qqAccessUrl(String appId, String appKey, String code, String url, String state)
            throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=");
        buffer.append(appId).append("&");
        buffer.append("client_secret=").append(appKey).append("&");
        buffer.append("code=").append(code).append("&");
        buffer.append("redirect_uri=");
        buffer.append(URLEncoder.encode(url, "UTF-8")).append("&");
        if (StringUtils.isNoneBlank(state)) {
            buffer.append("state=").append(state);
        }
        return buffer.toString();
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        }
        return jsonObject;
    }
}