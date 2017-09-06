package com.tocheck.parent.common.util;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * http请求
 *
 * @author Andy 2016年8月1日 上午10:28:16
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public final static int DEFAULT_THREAD_COUNT = 500;

    private static HttpClientBuilder clientBuilder;

    static {
        clientBuilder = HttpClientBuilder.create();
        clientBuilder.setMaxConnTotal(DEFAULT_THREAD_COUNT);
        clientBuilder.setMaxConnPerRoute(DEFAULT_THREAD_COUNT);
    }

    public static String doHttpClientPost(String uriHttp, String param) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse resHttp = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(uriHttp);
            StringEntity paramJsonEncode = new StringEntity(param, "UTF-8");
            post.setEntity(paramJsonEncode);
            post.setHeader("Content-Type", "application/json");
            resHttp = httpClient.execute(post);
            int statusHttp = resHttp.getStatusLine().getStatusCode();
            String entity = EntityUtils.toString(resHttp.getEntity(), "UTF-8");
            logger.debug("post http response status:{},content:{}", statusHttp, entity);
            if (HttpStatus.SC_OK == statusHttp) {// 正常返回
                return entity;
            }
        } catch (Exception e) {
            logger.error("get http[" + uriHttp + "] error:", e);
        } finally {
            try {
                if (null != resHttp) {
                    resHttp.close();
                }
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("get http[" + uriHttp + "] error:", e);
            }
        }
        return null;
    }
    /**
     * post请求
     *
     * @param uri
     * @param dataJson
     * @return
     */
    public static String doHttpPost(String uri, String dataJson) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(dataJson, headers);
        ResponseEntity<String> res = template.postForEntity(uri, formEntity, String.class);
        String result = res.getBody();
        logger.info("doHttpPost,data[{}],result[{}]", dataJson, result);
        return result;
    }

    /**
     * get 请求
     *
     * @param uri
     * @return
     */
    public static String doHttpGet(String uri) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse resHttp = null;
        try {
            HttpGet get = new HttpGet(uri);
            resHttp = httpClient.execute(get);
            int statusHttp = resHttp.getStatusLine().getStatusCode();
            String entity = EntityUtils.toString(resHttp.getEntity(), "UTF-8");
            logger.info("doHttpGet uri[" + uri + "], response status:" + statusHttp + ",content:" + entity);
            if (HttpStatus.SC_OK == statusHttp) {
                return entity;
            }
        } catch (Throwable e) {
            logger.error("get http[" + uri + "] error:", e);
        } finally {
            try {
                if (null != resHttp) {
                    resHttp.close();
                }
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("get http[" + uri + "] error:", e);
            }
        }
        return null;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
