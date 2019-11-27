package com.egrand.wfw.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class TokenFilter extends ZuulFilter {
    public boolean shouldFilter() { return true; }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("authorization", ctx.getRequest().getHeader("authorization"));
        return null;
    }

    public String filterType() { return "pre"; }

    public int filterOrder() { return 0; }
}

