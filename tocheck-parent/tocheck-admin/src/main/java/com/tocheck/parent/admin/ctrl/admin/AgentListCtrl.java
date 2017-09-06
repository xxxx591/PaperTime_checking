package com.tocheck.parent.admin.ctrl.admin;

import com.github.pagehelper.PageInfo;
import com.tocheck.parent.admin.ctrl.BaseCtrl;
import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.common.util.ResponseJson;
import com.tocheck.parent.core.entity.AgentUser;
import com.tocheck.parent.core.interfaces.AdminAuth;
import com.tocheck.parent.core.service.AgentUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pangliang
 * @create 2017-06-16 14:57
 **/
@Controller
@RequestMapping(value = "/admin")
public class AgentListCtrl extends BaseCtrl {

    @Autowired
    private AgentUserService agentUserService;

    private Logger logger = LoggerFactory.getLogger(AgentListCtrl.class);

    @AdminAuth
    @RequestMapping(value = "/agentList", method = RequestMethod.GET)
    public String agentList(Integer p) {
        try {
            p = p == null ? 1 : p;
            PageInfo<AgentUser> page = agentUserService.listPage(p);
            model.addAttribute("page", page);
            return "admin/agentList";
        } catch (Exception e) {
            logger.error("进入代理商列表页面失败!", e);
        }
        return "error/500";
    }

    @AdminAuth
    @ResponseBody
    @RequestMapping(value = "/pass")
    public ResponseJson audit(long id) {
        try {
            agentUserService.auditAgent(id, 2);
        } catch (Exception e) {
            logger.error("通过代理商审核失败!", e);
            return ResponseJson.body(Constants.RESP_FAIL,"系统错误,请刷新后再试!");
        }
        return ResponseJson.body(true,"",true);
    }

    @AdminAuth
    @ResponseBody
    @RequestMapping(value = "/unPass")
    public ResponseJson unPass(long id) {
        try {
            agentUserService.auditAgent(id, 0);
        } catch (Exception e) {
            logger.error("拒绝代理商审核失败!", e);
            return ResponseJson.body(Constants.RESP_FAIL,"系统错误,请刷新后再试!");
        }
        return ResponseJson.body(true,"",true);
    }
}
