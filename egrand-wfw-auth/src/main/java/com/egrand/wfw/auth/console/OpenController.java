package com.egrand.wfw.auth.console;

import com.egrand.wfw.auth.console.OpenController;
import com.egrand.wfw.auth.service.MessageAuthService;
import com.egrand.wfw.auth.service.UserAuthService;
import com.egrand.wfw.component.cache.redis.RedisCacheService;
import com.egrand.commons.lang.model.RestResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
public class OpenController {
    @Autowired
    private UserAuthService appAuthService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private MessageAuthService messageAuthService;
    @Value("${messageCodeService.isOpen:}")
    private boolean messageServiceIsOpen;
    @Value("${messageCodeIpLimitNums:}")
    private int ipLimitNums;
    @Value("${messageCodeIpLimitTime:}")
    private int ipLimitTime;
    @Value("${messageCodePhoneLimitNums:}")
    private int phoneLimitNums;
    @Value("${messageCodePhoneLimitTime:}")
    private int phoneLimitTime;
    @Value("${message.sendType.isPrivate}")
    private boolean messageSendType;
    private static final Logger logger = LoggerFactory.getLogger(OpenController.class);

    @RequestMapping({"/login/user"})
    public RestResponse loginByUserName(@RequestParam String username, @RequestParam String password) {
        return this.appAuthService.getIdentity(username, password);
    }

    @RequestMapping({"/login/info"})
    public void loginInfo(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(this.messageServiceIsOpen);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String jsonStr = "";
        String type = request.getParameter("method");
        if ("phoneCode".equals(type)) {
            jsonStr = phoneCode(request);
        } else if ("checkFirst".equals(type)) {
            jsonStr = checkFirst(request);
        } else if ("getMessageCode".equals(type)) {
            jsonStr = getMessageCode(request);
        } else if ("isSupportMes".equals(type)) {
            jsonStr = isSupportMes();
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public String isSupportMes() {
        return RestResponse.success(Boolean.valueOf(this.messageServiceIsOpen)).toString();
    }

    public String phoneCode(HttpServletRequest request) {
        String mobilePhone = request.getParameter("mobilePhone");
        String messageCode = request.getParameter("messagecode");
        try {
            String ip = getIpAddress(request);
            String ip_phone = ip.concat("_");
            ip_phone = ip_phone.concat(mobilePhone);
            RestResponse response = this.appAuthService.getAcountByPhone(mobilePhone);
            Map<String, String> map = (Map<String, String>) response.getData();
            if (this.redisCacheService.exists(ip_phone) && messageCode.equals(this.redisCacheService.get(ip_phone))) {
                System.out.println("短信验证成功！");
                this.redisCacheService.del(new String[]{ip_phone});
                RestResponse result = this.appAuthService.getIdentityByAccountCode(map.get("accountCode"));
                return RestResponse.success(result.getData()).toString();
            }
            return RestResponse.failed("0007", "短信验证失败").toString();

        } catch (Exception e) {
            return RestResponse.failed("0006", "验证码错误").toString();
        }
    }

    public String checkFirst(HttpServletRequest request) {
        String mobilePhone = request.getParameter("mobilePhone");
        try {
            String ip = getIpAddress(request);
            RestResponse response = this.appAuthService.getAcountByPhone(mobilePhone);
            Map<String, String> map = (Map<String, String>) response.getData();
            String username = map.get("accountName");
            String ip_account = ip.concat("_").concat(username);
            if (this.redisCacheService.exists(ip_account)) {
                return RestResponse.failed("0000", "并非首次检验").toString();
            }
            return RestResponse.success(ip_account).toString();
        } catch (Exception e) {
            logger.error("首次登录校验失败", e.getMessage());
            return RestResponse.failed("0001", "该手机号无对应账号").toString();
        }
    }

    public String getMessageCode(HttpServletRequest request) {
        String mobilePhone = request.getParameter("mobilePhone");
        String code = "";
        String ip_phone = "";
        try {
            if (StringUtils.isNotBlank(mobilePhone)) {
                String ip = getIpAddress(request);
                RestResponse response = this.appAuthService.getAcountByPhone(mobilePhone);
                Map<String, String> map = (Map<String, String>) response.getData();
                String username = map.get("accountName");
                String ip_account = ip.concat("_").concat(username);
                if (denialOfService(ip, this.ipLimitNums, this.ipLimitTime) || denialOfService(mobilePhone, this.phoneLimitNums, this.phoneLimitTime)) {
                    return RestResponse.failed("0009", "超过限制").toString();
                }
                System.out.println("ip_nums:" + this.redisCacheService.get(ip));
                System.out.println("phone_nums:" + this.redisCacheService.get(mobilePhone));
                Random random = new Random();
                for (int i = 0; i < 6; i++) {
                    code = code + random.nextInt(10);
                }
                ip_phone = ip.concat("_").concat(mobilePhone);
                if (this.redisCacheService.exists(ip_phone)) {
                    this.redisCacheService.del(new String[]{ip_phone});
                }
                this.redisCacheService.set(ip_phone, code, 300L);
                this.redisCacheService.incr(ip_account);
                this.redisCacheService.expire(ip_account, 3600L);
                System.out.println("ip_phone_code:" + this.redisCacheService.get(ip_phone));
                Map<String, String> params = new HashMap<>();
                params.put("phone", mobilePhone);
                params.put("content", "测试动态验证码" + code);

                if (this.messageSendType) {
                    this.messageAuthService.sendMessage(params);
                } else {

                    this.messageAuthService.sendMessage(mobilePhone, code, Integer.valueOf(2));
                }
                return RestResponse.success(mobilePhone).toString();
            }
            return RestResponse.failed("0000", "请求验证码失败").toString();
        } catch (Exception e) {
            return RestResponse.failed("0000", "请求验证码失败").toString();
        }
    }

    private boolean denialOfService(String userId, int limitNums, int limitTime) {
        if (this.redisCacheService.exists(userId)) {
            this.redisCacheService.incr(userId);
            long val = Long.valueOf(this.redisCacheService.get(userId)).longValue();
            if (val <= limitNums) {
                return false;
            }
        } else {

            this.redisCacheService.incr(userId);
            this.redisCacheService.expire(userId, (limitTime * 60));
            return false;
        }
        return true;
    }


    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @RequestMapping({"/user/info"})
    public RestResponse loginInfo(Principal principal) {
        return RestResponse.success(principal);
    }

    @RequestMapping({"/login/changeIdentity"})
    public RestResponse loginIdentity(@RequestParam String username, @RequestParam String password) {
        return this.appAuthService.getIdentity(username, password);
    }
}