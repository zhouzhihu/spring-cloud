package com.egrand.wfw.auth.security.handler;

import com.egrand.commons.lang.JsonUtils;
import com.egrand.commons.lang.model.RestResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        this.logger.info("登录失败");
        response.setStatus(500);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtils.obj2String(RestResponse.failed("", exception.getMessage())));
    }
}
