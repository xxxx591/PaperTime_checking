package com.tocheck.parent.admin.ctrl.admin;

import com.github.pagehelper.PageInfo;
import com.tocheck.parent.admin.ctrl.BaseCtrl;
import com.tocheck.parent.common.constans.Constants;
import com.tocheck.parent.common.util.ResponseJson;
import com.tocheck.parent.core.interfaces.AdminAuth;
import com.tocheck.parent.core.service.ToCheckTaskService;
import com.tocheck.parent.core.utils.CnkiFileUtil;
import com.tocheck.parent.core.vo.ToCheckTaskVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * @author pangliang
 * @create 2017-06-22 14:26
 **/
@Controller
@RequestMapping(value = "/admin")
public class AdminCheckTaskCtrl extends BaseCtrl {

    @Autowired
    private ToCheckTaskService toCheckTaskService;
    @Autowired
    private CnkiFileUtil cnkiFileUtil;

    private Logger logger = LoggerFactory.getLogger(AdminCheckTaskCtrl.class);

    @AdminAuth
    @RequestMapping(value = "/checkTask", method = RequestMethod.GET)
    public String checkTask(Integer p) {
        try {
            p = p == null ? 1 : p;
            PageInfo<ToCheckTaskVo> page = toCheckTaskService.agentCheckTask(p, getSessionUserInfo(request, Constants.ADMIN_USER).getId());
            model.addAttribute("page", page);
            return "admin/checkTask";
        } catch (Exception e) {
            logger.error("进入检测任务页面失败",e);
        }
        return "redirect:/500.html";
    }

    @AdminAuth
    @RequestMapping(value = "/downloadPaper/{id:[0-9]+}", method = RequestMethod.GET)
    public void downloadPaper(@PathVariable long id) {
        try {
            cnkiFileUtil.downloadPaper(request, response, id);
        } catch (Exception e) {
            logger.error("下载检测论文出现异常,id={}", id, e);
        }
    }

    @AdminAuth
    @ResponseBody
    @RequestMapping(value = "/uploadReport", method = RequestMethod.POST)
    public ResponseJson uploadReport(MultipartHttpServletRequest servletRequest, Long id) {
        try {
            return cnkiFileUtil.uploadReport(servletRequest, id, getSessionUserInfo(request, Constants.ADMIN_USER).getId());
        } catch (Exception e) {
            logger.error("上传失败报告失败!", e);
            return ResponseJson.body(Constants.RESP_FAIL, "上传失败,请刷新后重试!");
        }
    }

    @AdminAuth
    @ResponseBody
    @RequestMapping(value = "/putRushOrder", method = RequestMethod.POST)
    public ResponseJson putRushOrder(long id) {
        try {
            toCheckTaskService.putRushOrder(id);
            return ResponseJson.body(Constants.RESP_SUCCESS, "", true);
        } catch (Exception e) {
            logger.error("放入抢单池失败!", e);
            return ResponseJson.body(false, "操作失败,请刷新后重试!");
        }
    }
}
