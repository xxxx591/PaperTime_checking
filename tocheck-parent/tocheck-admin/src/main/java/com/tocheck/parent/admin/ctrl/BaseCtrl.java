package com.tocheck.parent.admin.ctrl;

import com.alibaba.fastjson.JSON;
import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.common.util.CookieUrlUtils;
import com.tocheck.parent.common.util.SSDBUtils;
import com.tocheck.parent.core.entity.AgentUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author pangliang
 * @create 2017-06-15 17:42
 **/
public class BaseCtrl {

    @Autowired
    protected SSDBUtils ssdbUtils;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected Model model;


    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response, Model model) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.model = model;
    }


    /**
     * 保存用户信息到ssdb
     *
     * @param agentUser
     */
    protected void saveSessionUserInfo(HttpServletRequest request, AgentUser agentUser, String loginType) {
        String sessionId = requestCookieId(request);
        if (StringUtils.isAnyBlank(sessionId)) {
            return;
        }
        ssdbUtils.save(sessionId + loginType, JSON.toJSONString(agentUser), Constants.USER_SESSION_TIME);
    }

    /**
     * 从ssdb获取用户信息
     *
     * @return
     */
    protected AgentUser getSessionUserInfo(HttpServletRequest request, String loginType) {
        String sessionId = requestCookieId(request);
        if (StringUtils.isAnyBlank(sessionId)) {
            return null;
        }
        String value = ssdbUtils.getValue(sessionId + loginType);
        if (StringUtils.isNoneBlank(value)) {
            return JSON.parseObject(value, AgentUser.class);
        }
        return null;
    }

    /**
     * 删除ssdb中的用户信息
     */
    protected void delSessionUserInfo(HttpServletRequest request, String loginType) {
        String sessionId = requestCookieId(request);
        if (StringUtils.isAnyBlank(sessionId)) {
            return;
        }
        ssdbUtils.del(sessionId + loginType);
    }

    /**
     * 获取sessionId
     *
     * @return
     */
    protected String requestCookieId(HttpServletRequest request) {
        String sessionId = CookieUrlUtils.readCookie(request, Constants.TOCHECK_S_ID);
        if (StringUtils.isAnyBlank(sessionId)) {
            sessionId = request.getSession().getId();
        }
        return sessionId;
    }
}
