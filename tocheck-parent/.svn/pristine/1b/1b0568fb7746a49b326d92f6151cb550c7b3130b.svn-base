package com.tocheck.parent.admin.ctrl.agent;

import com.tocheck.parent.admin.ctrl.BaseCtrl;
import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.common.util.ResponseJson;
import com.tocheck.parent.core.entity.AgentUser;
import com.tocheck.parent.core.entity.ToCheckMajor;
import com.tocheck.parent.core.interfaces.AgentAuth;
import com.tocheck.parent.core.service.AgentUserService;
import com.tocheck.parent.core.service.ToCheckMajorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @author pangliang
 * @create 2017-06-15 17:53
 **/
@Controller
@RequestMapping(value = "/agent")
public class AgentInfoCtrl extends BaseCtrl {

    @Autowired
    private ToCheckMajorService toCheckMajorService;

    @Autowired
    private AgentUserService agentUserService;

    private Logger logger = LoggerFactory.getLogger(AgentInfoCtrl.class);

    @AgentAuth
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        List<ToCheckMajor> list = toCheckMajorService.getAll();
        model.addAttribute("list", list);
        return "agent/info";
    }

    @AgentAuth
    @ResponseBody
    @RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
    public ResponseJson modifyInfo(AgentUser agentUser) {
        AgentUser s = getSessionUserInfo(request, Constants.AGENT_USER);
        if (StringUtils.isBlank(agentUser.getSystemIds())) {
            return ResponseJson.body(Constants.RESP_FAIL, "请至少选择一个检测系统!");
        }
        if (StringUtils.isBlank(agentUser.getMajorNames())) {
            return ResponseJson.body(Constants.RESP_FAIL, "请至少选择一个检测专业!");
        }
        try {
            agentUser.setId(s.getId());
            agentUserService.modifyInfo(agentUser);
            saveSessionUserInfo(request, agentUserService.getById(s.getId()), Constants.AGENT_USER);
            return ResponseJson.body(Constants.RESP_SUCCESS);
        } catch (Exception e) {
            logger.error("代理商修改个人信息失败", e);
            return ResponseJson.body(Constants.RESP_FAIL, "系统错误,请刷新后再试!");
        }
    }

    @AgentAuth
    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public String exit() {
        delSessionUserInfo(request, Constants.AGENT_USER);
        return "redirect:/login.html";
    }
}
