package com.tocheck.parent.admin.ctrl;

import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.common.util.ResponseJson;
import com.tocheck.parent.core.entity.AgentUser;
import com.tocheck.parent.core.entity.ToCheckMajor;
import com.tocheck.parent.core.service.AgentUserService;
import com.tocheck.parent.core.service.ToCheckMajorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author pangliang
 * @create 2017-06-15 10:27
 **/
@Controller
public class LoginCtrl extends BaseCtrl {

    @Autowired
    private ToCheckMajorService toCheckMajorService;
    @Autowired
    private AgentUserService agentUserService;

    private Logger logger = LoggerFactory.getLogger(LoginCtrl.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        List<ToCheckMajor> list = toCheckMajorService.getAll();
        model.addAttribute("list", list);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson register(AgentUser agentUser) {
        if (StringUtils.isBlank(agentUser.getUserName()) || StringUtils.length(agentUser.getUserName()) < 6 || StringUtils.length(agentUser.getUserName()) > 15) {
            return ResponseJson.body(Constants.RESP_FAIL, "请输入6-15位的用户名!");
        }
        if (StringUtils.isBlank(agentUser.getPassword())) {
            return ResponseJson.body(Constants.RESP_FAIL, "请输入密码!");
        }
        if (StringUtils.isBlank(agentUser.getSystemIds())) {
            return ResponseJson.body(Constants.RESP_FAIL, "请至少选择一个检测系统!");
        }
        if (StringUtils.isBlank(agentUser.getMajorNames())) {
            return ResponseJson.body(Constants.RESP_FAIL, "请至少选择一个检测专业!");
        }
        if (agentUserService.getByUserName(agentUser.getUserName()) != null) {
            return ResponseJson.body(Constants.RESP_FAIL, "账号已经存在,请换一个试试!");
        }
        try {
            agentUserService.insert(agentUser);
            return ResponseJson.body(Constants.RESP_SUCCESS);
        } catch (Exception e) {
            logger.error("代理商注册失败", e);
            return ResponseJson.body(Constants.RESP_FAIL, "系统错误,请刷新后重试!");
        }
    }

    @RequestMapping(value = "/agentLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson agentLogin(String userName, String password) {
        AgentUser agentUser = agentUserService.validateLogin(userName, password, false);
        if (agentUser == null) {
            return ResponseJson.body(Constants.RESP_FAIL, "账号或密码错误!");
        }
        if (agentUser.getStatus() == 1) {
            return ResponseJson.body(Constants.RESP_FAIL, "账号审核中!");
        }
        if (agentUser.getStatus() == 0) {
            return ResponseJson.body(Constants.RESP_FAIL, "账号审核失败,请联系客服!");
        }
        saveSessionUserInfo(request, agentUser, Constants.AGENT_USER);
        return ResponseJson.body(Constants.RESP_SUCCESS);
    }

    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson adminLogin(String userName, String password) {
        AgentUser agentUser = agentUserService.validateLogin(userName, password, true);
        if (agentUser == null) {
            return ResponseJson.body(Constants.RESP_FAIL, "账号或密码错误!");
        }
        saveSessionUserInfo(request, agentUser, Constants.ADMIN_USER);
        return ResponseJson.body(Constants.RESP_SUCCESS);
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "error/404";
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String error500() {
        return "error/500";
    }
}
