package com.egrand.wfw.zuul.filter;

import com.egrand.wfw.zuul.utils.WebUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PrintRequestLogFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public String filterType() {
        return "post";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //WebUtils.logRequestParams(request);
        String method = request.getMethod();
        String interfaceMethod = request.getServletPath();
        this.logger.info("请求方法method={},url={}", method, interfaceMethod);
        return null;
    }
}
