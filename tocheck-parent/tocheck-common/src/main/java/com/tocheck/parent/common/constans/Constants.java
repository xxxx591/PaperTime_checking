package com.tocheck.parent.common.constans;

/**
 * Created by kchen on 2017/2/23.
 */
public class Constants {

    private Constants() {
    }

    public static final long ROOT_AGENT_ID = 1L;  //官网ID
    public static final String TOCHECK_S_ID = "tocheck_s_id";
    public static final int COOKIE_MAX_AGE = 72000; //cookie保存多久
    public static final int USER_SESSION_TIME = 43200;
    public static final int PAGE_LIMIT = 12;
    public static final int STATE_ENABLE = 1;    //状态可用
    public static final int STATE_DISABLE = 0;   //状态不可用
    public static final String WORD_SUFFIX = ".txt|.doc|.docx";
    public static final String REPORT_SUFFIX = ".rar|.zip";
    public static final String IMG_SUFFIX = ".bmp|.jpg|.gif|.png";
    public static final int CHECK_STATUS_CHECK_ERROR = -1;  //检测出错
    public static final int CHECK_STATUS_REPORT_ERROR = -2;  //检测出错
    public static final int CHECK_STATUS_RUSH = 1;    //抢单池
    public static final int CHECK_STATUS_WAIT = 0;    //正在排队
    public static final int CHECK_STATUS_CHECKING = 2;  //正在检测
//    public static final int CHECK_STATUS_REPORT = 2;  //正在生成报告
    public static final int CHECK_STATUS_FINISH = 3;  //已完成
    public static final int CHECK_STATUS_DOWN = 4;  //已下载报告
    public static final int CHECK_STATUS_DELETE = 5;  //已删除
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";
    public static final String STATUS = "status";
    public static final String RESP_STATE = "state";
    public static final boolean RESP_SUCCESS = true;
    public static final boolean RESP_FAIL = false;
    public static final String RESP_MESSAGE = "message";
    public static final String COMMIT_ERROR = "提交失败,请刷新后重试!";
    public static final String TAOBAO_SEELER_NICK = "董ht91";
    public static final int WAIT_PAY_STATUS = 0;
    public static final int DONE_PAY_STATUS = 1;
    public static final String ADMIN_USER = "adminUser";
    public static final String AGENT_USER = "agentUser";
    public static final String AGENT_URL = "www.tocheck.cn/s/{0}";
    public static final String TOP_LEVEL_DOMAIN = "tocheck.cn";
    public static final String OFFICIAL_WEBSITE = "www.tocheck.cn";
    public static final int ENABLED = 1;   //可用
    public static final int DISABLE = 0;   //不可用
    public static final long CHECK_SYSTEM_WANFANG = 4;
    public static final long CHECK_SYSTEM_PAPERTIME = 5;
    public static final long CHECK_SYSTEM_PAPERPASS = 6;
    public static final long CHECK_SYSTEM_PAPERFREE = 7;
    public static final int ACTIVITY_AUDIT = 0;
    public static final int ACTIVITY_PASS = 1;
    public static final int ACTIVITY_NOPASS = -1;
    public static final int LOGIN_TYPE_QQ = 1;    //qq
    public static final int LOGIN_TYPE_WX = 2;    //微信
    public static final String COMMON_PWD = "paperpass";
    public static final String AGENT_RECOMMEND = "agent_recommend";



}