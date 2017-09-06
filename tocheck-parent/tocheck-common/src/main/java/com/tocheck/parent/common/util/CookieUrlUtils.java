package com.tocheck.parent.common.util;

import com.tocheck.parent.common.constans.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kchen on 2015/8/18.
 */
public class CookieUrlUtils {

    private CookieUrlUtils(){};

    /**
     * 将当前访问url添加到cookie中
     * @param httpResponse
     * @param name
     * @param value
     */
    public static void writeCookie(HttpServletResponse httpResponse, String name,String value){
        addCookie(httpResponse, name, value, Constants.COOKIE_MAX_AGE);
    }

    /**
     * cookie中写入k-v 
     * @param httpResponse
     * @param name
     * @param value
     * @param expiredTime
     */
    public static void writeCookie(HttpServletResponse httpResponse, String name,String value,Integer expiredTime){
        addCookie(httpResponse, name, value, expiredTime);
    }
    
    /**
     * 设置cookie
     * @param response
     * @param name  cookie名字
     * @param value  @param maxAge cookie生cookie值
     *命周期  以秒为单位
     */
    private static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 获取输入字段的对应的值
     * @param request
     * @param key 键
     * @return
     */
    public static String readCookie(HttpServletRequest request,String key){
        Cookie cookie = getCookieByName(request, key);
        if(null == cookie){
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    private static Cookie getCookieByName(HttpServletRequest request,String name) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (name.equalsIgnoreCase(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}