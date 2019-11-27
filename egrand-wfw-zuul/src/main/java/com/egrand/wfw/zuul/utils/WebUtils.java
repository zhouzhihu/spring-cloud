package com.egrand.wfw.zuul.utils;

import com.alibaba.fastjson.JSONObject;
import com.egrand.wfw.zuul.service.FeignService;
import com.egrand.wfw.zuul.utils.SpringUtil;
import com.egrand.commons.lang.StringUtils;
import com.egrand.commons.lang.model.RestResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Enumeration;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebUtils {
    private static Logger logger = LoggerFactory.getLogger(WebUtils.class);

    public static void logRequestParams(HttpServletRequest request) {
        insertLogger(request);
        String url = request.getRequestURI();
        String[] excludes = { "log.exclude.url" };
        for (int i = 0; i < excludes.length; i++) {
            if (StringUtils.isNotEmpty(excludes[i]) && url.indexOf(excludes[i]) >= 0) {
                return;
            }
        }
        String ip = getRemoteAddress(request);
        logger.info("Http请求 [" + ip + "][" + request.getSession().getId() + "]" + request.getRequestURL() + " ");
        if (logger.isDebugEnabled()) {
            logger.debug("ContentType:{}", request.getContentType());
            logger.debug("Method:{}", request.getMethod());
            logger.debug("Encoding:{}", request.getCharacterEncoding());
            logger.debug("Headers:");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                logger.debug("   {}:{}", name, value);
            }
        }
        logger.info("Params:");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String value = request.getParameter(paramName);
            logger.info(paramName + ":" + value);
        }
    }

    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-real-ip");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
            {
                ip = request.getRemoteAddr();
            }
        }

        return ip;
    }

    public static String getMACAddress(String ip) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -a " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null)
                {
                    if (str.indexOf("MAC") > 1) {
                        macAddress = str.substring(str
                                .indexOf("=") + 2, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("IO异常", e);
        }
        return macAddress;
    }

    private static void insertLogger(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String[] url = request.getServletPath().split("/");
        String ip = getRemoteAddress(request);
        String project = url[1];
        String path = request.getServletPath();
        String authorization = request.getHeader("authorization");
        String token = authorization.split(" ")[1];
        json.put("id", uuid());
        json.put("ip", ip);
        json.put("project", project);
        json.put("className", "");
        json.put("classDescription", "");
        json.put("method", "");
        json.put("methodDescription", "");
        json.put("path", path);
        json.put("parameter", "");
        json.put("token", token);
        json.put("sql", "");
        json.put("createtime", time());
        json.put("version", "");
        json.put("demandCodeList", "");
        json.put("identityName", "");
        json.put("batchId", uuid());
        json.put("interfaceTime", time());
        logger.debug("日志表内容：{}", json.toString());
        FeignService feignService = (FeignService)SpringUtil.getBean(FeignService.class);
        RestResponse message = feignService.insertLogger(json);
        logger.debug("调用结果{}", message);
    }

    private static String uuid() { return UUID.randomUUID().toString().replace("-", ""); }

    private static Long time() { return Long.valueOf(System.currentTimeMillis()); }
}
