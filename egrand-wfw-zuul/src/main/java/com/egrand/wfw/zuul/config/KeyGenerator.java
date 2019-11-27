package com.egrand.wfw.zuul.config;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.RateLimitUtils;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerator implements RateLimitKeyGenerator {
    private final RateLimitProperties properties;
    private final RateLimitUtils rateLimitUtils;

    public KeyGenerator(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
        this.properties = properties;
        this.rateLimitUtils = rateLimitUtils;
    }

    public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
        List<RateLimitProperties.Policy.Type> types = (List<RateLimitProperties.Policy.Type>)policy.getType().stream().map(RateLimitProperties.Policy.MatchType::getType).collect(Collectors.toList());
        List<String> matchers = (List<String>)policy.getType().stream().map(RateLimitProperties.Policy.MatchType::getMatcher).collect(Collectors.toList());
        StringJoiner joiner = new StringJoiner(":");
        joiner.add(this.properties.getKeyPrefix());
        if (route != null) {
            joiner.add(route.getId());
        }
        if (!types.isEmpty()) {
            if (types.contains(RateLimitProperties.Policy.Type.URL) && route != null) {
                joiner.add(route.getPath());
            }
            if (types.contains(RateLimitProperties.Policy.Type.ORIGIN)) {
                joiner.add(this.rateLimitUtils.getRemoteAddress(request));
            }
            if (types.contains(RateLimitProperties.Policy.Type.USER)) {
                joiner.add(this.rateLimitUtils.getUser(request));
            }
        }
        if (!matchers.isEmpty()) {
            if (matchers.contains(route.getPath()) && route != null) {
                joiner.add(route.getPath());
            }
            if (matchers.contains(this.rateLimitUtils.getRemoteAddress(request))) {
                joiner.add(this.rateLimitUtils.getRemoteAddress(request));
            }
            if (matchers.contains(this.rateLimitUtils.getUser(request))) {
                joiner.add(this.rateLimitUtils.getUser(request));
            }
        }
        return joiner.toString();
    }
}

