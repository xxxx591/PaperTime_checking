package com.tocheck.parent.admin.ctrl.agent;

import com.github.pagehelper.PageInfo;
import com.tocheck.parent.admin.ctrl.BaseCtrl;
import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.core.entity.ToCheckMajor;
import com.tocheck.parent.core.entity.WithdrawRecord;
import com.tocheck.parent.core.interfaces.AgentAuth;
import com.tocheck.parent.core.service.WithdrawRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author pangliang
 * @create 2017-06-26 17:21
 **/
@Controller
@RequestMapping(value = "/agent")
public class AgentWithdrawCtrl extends BaseCtrl {

    @Autowired
    private WithdrawRecordService withdrawRecordService;

    private Logger logger = LoggerFactory.getLogger(AgentWithdrawCtrl.class);

    @AgentAuth
    @RequestMapping(value = "/withdrawRecord", method = RequestMethod.GET)
    public String info(Integer p) {
        try {
        p = p == null ? 1 : p;
        PageInfo<WithdrawRecord> page = withdrawRecordService.listPage(p, getSessionUserInfo(request, Constants.AGENT_USER).getId());
        model.addAttribute("page", page);
        return "agent/withdrawRecord";
        } catch (Exception e) {
            logger.error("进入提现记录页面失败!", e);
        }
        return "error/500";
    }
}
