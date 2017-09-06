package com.tocheck.parent.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtils {

    private static final Logger logger = LogManager.getLogger(RestTemplateUtils.class);

    /**
     * 使用spring RestTemplate发起post请求
     */
    public static <T> T post(String url, T formData, Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<T> formEntity = new HttpEntity<>(formData, headers);
            RestTemplate template = new RestTemplate();
            ResponseEntity<T> responseEntity = template.postForEntity(url, formEntity, responseType);
            logger.info("返回信息body={}", responseEntity.getBody());
            return responseEntity.getBody();
        } catch (Exception e) {
            logger.warn("rest post请求失败,url={},formData={}", url, JSON.toJSONString(formData), e);
        }
        return null;
    }

    /**
     * 使用spring RestTemplate发起get请求
     */
    public static <T> T get(String url, Class<T> responseType, Object ... params) {
        try {
            RestTemplate template = new RestTemplate();
            return template.getForObject(url, responseType, params);
        } catch (Exception e) {
            logger.warn("rest get请求失败,url={},params={}", url, params, e);
        }
        return null;
    }
}
