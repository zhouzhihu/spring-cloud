package com.egrand.wfw.zuul.filter;

import com.egrand.core.interceptor.FeignRequestInterceptor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class TokenFilter extends ZuulFilter {
    public boolean shouldFilter() { return true; }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("authorization", ctx.getRequest().getHeader("authorization"));
        ctx.addZuulRequestHeader(FeignRequestInterceptor.X_REQUEST_ID, ctx.getRequest().getHeader(FeignRequestInterceptor.X_REQUEST_ID));
        System.out.println("=======================tokenFilter==================");
        System.out.println("authorization = " + ctx.getRequest().getHeader("authorization"));
        System.out.println("X_REQUEST_ID = " + ctx.getRequest().getHeader(FeignRequestInterceptor.X_REQUEST_ID));
        System.out.println("=======================tokenFilter==================");
        return null;
    }

    public String filterType() { return "pre"; }

    public int filterOrder() { return 0; }
}

