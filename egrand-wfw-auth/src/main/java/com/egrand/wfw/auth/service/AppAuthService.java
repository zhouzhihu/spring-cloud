package com.egrand.wfw.auth.service;

import com.egrand.wfw.auth.security.FeignConfig;
import com.egrand.wfw.auth.service.impl.FeignHystrix;
import com.egrand.commons.lang.model.RestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "http://nbjcpt-application", configuration = {FeignConfig.class}, fallback = FeignHystrix.class)
public interface AppAuthService {
    @RequestMapping(value = {"/open/yygl/yyglGetQueryByAP"}, method = {RequestMethod.GET})
    RestResponse yyglGetQueryByAP(@RequestParam("account") String paramString);
}
