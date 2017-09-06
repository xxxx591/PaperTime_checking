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
 * @create 2017-06-23 10:42
 **/
@Controller
@RequestMapping(value = "/agent")
public class AgentRushOrderCtrl extends BaseCtrl {

    @Autowired
    private ToCheckTaskService toCheckTaskService;
    private Logger logger = LoggerFactory.getLogger(AgentRushOrderCtrl.class);

    @AgentAuth
    @RequestMapping(value = "/rushOrder", method = RequestMethod.GET)
    public String rushOrder(Integer p) {
        try {
            p = p == null ? 1 : p;
            PageInfo<ToCheckTaskVo> page = toCheckTaskService.rushOrder(p);
            model.addAttribute("page", page);
            return "agent/rushOrder";
        }catch (Exception e){
           logger.error("进入抢单池页面出错!",e);
        }
        return "/error/500";
    }

    @AgentAuth
    @ResponseBody
    @RequestMapping(value = "/rushOrder", method = RequestMethod.POST)
    public ResponseJson rushOrder(long id) {
        try {
            return toCheckTaskService.rushOrder(id, getSessionUserInfo(request, Constants.AGENT_USER).getId());
        } catch (Exception e) {
            logger.error("抢单失败,请刷新后重试!", e);
            return ResponseJson.body(false, "抢单失败,请刷新后重试!");
        }
    }
}