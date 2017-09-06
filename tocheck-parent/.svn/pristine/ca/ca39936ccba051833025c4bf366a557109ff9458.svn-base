package com.tocheck.parent.admin.ctrl.agent;

import com.github.pagehelper.PageInfo;
import com.tocheck.parent.admin.ctrl.BaseCtrl;
import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.common.util.ResponseJson;
import com.tocheck.parent.core.interfaces.AgentAuth;
import com.tocheck.parent.core.service.ToCheckTaskService;
import com.tocheck.parent.core.vo.ToCheckTaskVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pangliang
 * @create 2017-06-26 8:56
 **/
@Controller
@RequestMapping(value = "/agent")
public class OrderSettlementCtrl extends BaseCtrl {

    @Autowired
    private ToCheckTaskService toCheckTaskService;
    private Logger logger = LoggerFactory.getLogger(OrderSettlementCtrl.class);

    @AgentAuth
    @RequestMapping(value = "/targetFee", method = RequestMethod.GET)
    public String targetFee(Integer p) {
        try {
            p = p == null ? 1 : p;
            PageInfo<ToCheckTaskVo> page = toCheckTaskService.targetFee(p, getSessionUserInfo(request, Constants.AGENT_USER).getId());
            model.addAttribute("page", page);
            return "agent/targetFee";
        } catch (Exception e) {
            logger.error("进入检测费结算页面失败!", e);
        }
        return "error/500";
    }

    @AgentAuth
    @RequestMapping(value = "/flowFee", method = RequestMethod.GET)
    public String flowFee(Integer p) {
        try {
            p = p == null ? 1 : p;
            PageInfo<ToCheckTaskVo> page = toCheckTaskService.flowFee(p, getSessionUserInfo(request, Constants.AGENT_USER).getId());
            model.addAttribute("page", page);
            return "agent/flowFee";
        } catch (Exception e) {
            logger.error("进入流量费结算页面失败!", e);
        }
        return "error/500";
    }

    @AgentAuth
    @ResponseBody
    @RequestMapping(value = "/targetWithdraw", method = RequestMethod.POST)
    public ResponseJson targetWithdraw() {
        try {
            return toCheckTaskService.targetWithdraw(getSessionUserInfo(request, Constants.AGENT_USER).getId());
        } catch (Exception e) {
            logger.error("申请失败,请刷新后再试!", e);
            return ResponseJson.body(Constants.RESP_FAIL, "申请失败,请刷新后再试!");
        }
    }

    @AgentAuth
    @ResponseBody
    @RequestMapping(value = "/flowWithdraw", method = RequestMethod.POST)
    public ResponseJson flowWithdraw() {
        try {
            return toCheckTaskService.flowWithdraw(getSessionUserInfo(request, Constants.AGENT_USER).getId());
        } catch (Exception e) {
            logger.error("申请失败,请刷新后再试!", e);
            return ResponseJson.body(Constants.RESP_FAIL, "申请失败,请刷新后再试!");
        }
    }

}
