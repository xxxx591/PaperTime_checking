package com.tocheck.parent.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by kchen on 2016/7/8.
 */
public class CommonUtil {

    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    private CommonUtil() {
    }

    /**
     * 第三方快登昵称过滤，只保留汉字，英文字母和数字
     */
    public static final String REG_FILTER_NICKNAME = "[^0-9a-zA-Z\\u4e00-\\u9fa5]";
    /**
     * 生成指定长度的随机数字串
     *
     * @param length
     * @return
     */
    public static String buildRandomNum(int length) {
        if (length < 0) {
            length = Math.abs(length);
        }
        return RandomStringUtils.randomNumeric(length);
    }

    public static boolean isMobile(String mobile) {
        return mobile.matches("^[1]{1}\\d{10}$");
    }

    public static boolean isTaoBaoOrder(String tid) {
        return tid.matches("^\\d{15,18}$");
    }

    public static synchronized String createToCheckOrderNum(long userId) {
        return DateUtil.getDate(new Date(), "yyyyMMddHHmmssSSS") + userId;
    }


    public static int contentLength(String content, Long systemId) {
        if (systemId == 5 || systemId == 6 || systemId == 7) {
            return paperFreeAndTimeAndPass(content, 2);
        } else if (systemId == 4 || systemId == 8) {
            return paperOther(content);
        } else if (systemId == 9 || systemId == 10) {
            return turnitin(content);
        }
        logger.info("未知的检测系统..systemId={}", systemId);
        return 0;
    }

    public static int turnitin(String content) {
        if (StringUtils.isBlank(content)) return 0;
        String[] t = content.split("\\s");
        int i = 0;
        for (String string : t) {
            String regex = "[\u4e00-\u9fa5 ！（）|【】；：。，、？]";
            String h = string.replaceAll(regex, " ").trim();
            String[] tees = h.split("\\s");
            for (String string2 : tees) {
                if (StringUtils.isNoneBlank(string2.trim())) {
                    string = string.replace(string2, " ");
                }
            }
            i += string.length();
        }
        return i;
    }

    private static int paperFreeAndTimeAndPass(String text, int weight) {
        int english = 1;
        int other = 0;
        for (char ch : text.toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                if (ch < 128) {
                    english++;
                } else {
                    other++;
                }
            }
        }
        return english / weight + other;
    }

    private static int paperOther(String content) {
        if (StringUtils.isBlank(content)) return 0;
        return content.length();
    }

    public static String encodeUserId(Long userId) {
        if (userId == null) {
            return "";
        }
        return Long.toString(userId + 10000, 36);
    }

    /**
     * 把36进制的用户id转换成10进制值，转换后再减10000得到真正的id
     *
     * @param str 36进制用户id
     * @return 10进制的用户id
     */
    public static Long decodeUserId(String str) {
        Long userId = 0L;
        if (StringUtils.isEmpty(str)) {
            return userId;
        }
        try {
            userId = Long.valueOf(str, 36) - 10000;
        } catch (Exception e) {
            logger.warn("解码用户id失败,userId={}", str, e);
            userId = 0L;
        }
        return userId;
    }

}
