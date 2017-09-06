package com.tocheck.parent.admin.ctrl.admin;

import com.github.pagehelper.PageInfo;
import com.tocheck.parent.admin.ctrl.BaseCtrl;
import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.common.util.ResponseJson;
import com.tocheck.parent.core.interfaces.AdminAuth;
import com.tocheck.parent.core.service.UserActivityService;
import com.tocheck.parent.core.vo.UserActivityVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pangliang
 * @create 2017-07-05 14:40
 **/
@Controller
@RequestMapping("/admin")
public class ActivityAuditCtrl extends BaseCtrl {

    @Autowired
    private UserActivityService userActivityService;
    private Logger logger = LoggerFactory.getLogger(ActivityAuditCtrl.class);

    @AdminAuth
    @RequestMapping(value = "/activityList", method = RequestMethod.GET)
    public String activityList(Integer p) {
        try {
            p = p == null ? 1 : p;
            PageInfo<UserActivityVo> page = userActivityService.listPage(p);
            model.addAttribute("page", page);
            return "admin/activityList";
        } catch (Exception e) {
            logger.error("进入活动审核页面出错", e);
        }
        return "redirect:/500.html";
    }

    @AdminAuth
    @ResponseBody
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseJson audit(String ids, int status) {
        try {
            return userActivityService.activityAudit(ids, status);
        } catch (Exception e) {
            logger.error("审核失败", e);
            return ResponseJson.body(Constants.RESP_FAIL,"审核出错,请刷新后重试!");
        }
    }
}
