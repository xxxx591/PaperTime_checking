package com.tocheck.parent.common.util;

import com.tocheck.parent.common.constans.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kchen on 2015-09-10.
 */
public class ToCheckFileUtils {

    public static String getWordSuffix(String fileName) {
        int index = StringUtils.lastIndexOf(fileName, ".");
        String suffix = StringUtils.substring(fileName, index);
        String[] sus = StringUtils.split(Constants.WORD_SUFFIX, "|");
        for (String sfx : sus) {
            if (StringUtils.equalsIgnoreCase(sfx, suffix)) {
                return suffix;
            }
        }
        return null;
    }
    public static String getReportSuffix(String fileName) {
        int index = StringUtils.lastIndexOf(fileName, ".");
        String suffix = StringUtils.substring(fileName, index);
        String[] sus = StringUtils.split(Constants.REPORT_SUFFIX, "|");
        for (String sfx : sus) {
            if (StringUtils.equalsIgnoreCase(sfx, suffix)) {
                return suffix;
            }
        }
        return null;
    }

    public static String getWordName(String fileName) {
        int index = StringUtils.lastIndexOf(fileName, ".");
        return StringUtils.substring(fileName, 0, index);
    }

    public static boolean isProperPaper(String filename) {
        String suffix = getWordSuffix(filename);
        if (StringUtils.isAnyBlank(suffix)) {
            return false;
        }
        Pattern pattern = Pattern.compile(Constants.WORD_SUFFIX);
        Matcher matcher = pattern.matcher(suffix);
        return matcher.matches();
    }

    public static boolean isReport(String filename) {
        String suffix = getReportSuffix(filename);
        if (StringUtils.isAnyBlank(suffix)) {
            return false;
        }
        Pattern pattern = Pattern.compile(Constants.REPORT_SUFFIX);
        Matcher matcher = pattern.matcher(suffix);
        return matcher.matches();
    }



    public static File saveMaterialFile(MultipartFile uploadFile, String uploadPath, Long id,String fileName) throws IOException {
        String userDir = DateUtil.getDate(new Date(), "yyyyMMddHH");
        String fileDir = uploadPath  + userDir + File.separator + id + File.separator;
        String suffix = getWordSuffix(uploadFile.getOriginalFilename());
        File fileParent = new File(fileDir);
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        File file = new File(fileDir + fileName + suffix);
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
        return file;
    }

    public static File saveReportFile(MultipartFile uploadFile, String uploadPath,String fileName) throws IOException {
        String suffix = getReportSuffix(uploadFile.getOriginalFilename());
        File fileParent = new File(uploadPath);
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        File file = new File(uploadPath + fileName + suffix);
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
        return file;
    }



    public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        //中文文件名支持
        String encodedfileName = null;
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && agent.contains("Mozilla")) {
            encodedfileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        } else {
            encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
    }

}