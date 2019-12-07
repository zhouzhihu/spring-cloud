package com.egrand.wfw.oauth.security.web;

import com.egrand.commons.base.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"authorizationRequest"})
public class OAuth2ApprovalController
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping({"/oauth/confirm_access"})
    public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        Map<String, String> scopes = model.containsKey("scopes") ? (Map<String, String>)model.get("scopes") : (Map<String, String>)request.getAttribute("scopes");
        List<String> scopeList = new ArrayList<>();
        if (scopes != null) {
            scopeList.addAll(scopes.keySet());
        }
        model.put("scopeList", scopeList);
        this.logger.info("权限数据是{}", JsonUtils.obj2String(model));
        return "oauthGrant";
    }

    @RequestMapping({"/oauth/approvale/error"})
    public String handleError(Map<String, Object> model, HttpServletRequest request) {
        String errorSummary;
        Object error = request.getAttribute("error");
        if (error instanceof OAuth2Exception) {
            OAuth2Exception oauthError = (OAuth2Exception)error;
            errorSummary = HtmlUtils.htmlEscape(oauthError.getSummary());
        } else {
            errorSummary = "Unknown error";
        }
        model.put("errorSummary", errorSummary);
        return "error/html/401";
    }

    @RequestMapping({"/authentication/require"})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
        DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();
        SavedRequest savedRequest = httpSessionRequestCache.getRequest(request, response);
        if (null != savedRequest) {
            String targetUrl = savedRequest.getRedirectUrl();
            if (this.logger.isDebugEnabled()) {
                this.logger.info("引发跳转的请求是：" + targetUrl);
            }
            defaultRedirectStrategy.sendRedirect(request, response, "/oauthLogin");
        }

        return "访问服务器需要验证，请引导用户到登录界面";
    }

    @RequestMapping({"/oauthLogin"})
    public String login() { return "login"; }
}