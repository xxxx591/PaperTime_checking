package com.tocheck.parent.admin.interceptor;

import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.core.entity.AgentUser;
import com.tocheck.parent.core.interfaces.AdminAuth;
import com.tocheck.parent.core.interfaces.AgentAuth;
import com.tocheck.parent.core.utils.TocheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * @author pangliang
 * @create 2017-06-15 10:25
 **/
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TocheckUtil tocheckUtil;

    private Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = tocheckUtil.putPaperFreeSessionId(request, response);

        HandlerMethod _handler = (HandlerMethod) handler;
        AdminAuth adminAuth = _handler.getMethodAnnotation(AdminAuth.class);
        AgentAuth agentAuth = _handler.getMethodAnnotation(AgentAuth.class);

        if (StringUtils.isNotEmpty(sessionId)) {
            if (adminAuth != null) {
                AgentUser user = tocheckUtil.getLoginUser(sessionId, Constants.ADMIN_USER);
                if (user == null) {
                    return userSessionIsEmpty(request, response);
                }
            }
            if (agentAuth != null) {
                AgentUser user = tocheckUtil.getLoginUser(sessionId, Constants.AGENT_USER);
                if (user == null) {
                    return userSessionIsEmpty(request, response);
                }
            }
        }
        return true;
    }

    private boolean userSessionIsEmpty(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getHeader("x-requested-with") != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
            response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
            response.setStatus(403);
            logger.warn("ajax请求时，用户session过期");
            return false;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(request.getContextPath());
            sb.append("/login.html?returnUrl=");
            String requestUrl = request.getRequestURL().toString();
            String param = "";
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                if (paramValues.length == 1) {
                    String paramValue = paramValues[0];
                    if (paramValue.length() != 0) {
                        param += ("".equals(param) ? "?" : "&") + paramName + "=" + paramValue;
                    }
                }
            }
            requestUrl = requestUrl + param;
            sb.append(URLEncoder.encode(requestUrl, "UTF-8"));
            response.setStatus(403);
            response.sendRedirect(sb.toString());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String sessionId = tocheckUtil.putPaperFreeSessionId(request, response);
        AgentUser adminUser = tocheckUtil.getLoginUser(sessionId, Constants.ADMIN_USER);
        if (adminUser != null) {
            request.setAttribute("adminUser", adminUser);
        }
        AgentUser agentUser = tocheckUtil.getLoginUser(sessionId, Constants.AGENT_USER);
        if (agentUser != null) {
            request.setAttribute("agentUser", agentUser);
        }
    }
}
