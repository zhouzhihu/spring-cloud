package com.egrand.wfw.auth.service;

import com.egrand.wfw.auth.security.FeignConfig;
import com.egrand.wfw.auth.service.impl.FeignHystrix;
import com.egrand.commons.lang.model.RestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "http://nbjcpt-user", configuration = {FeignConfig.class}, fallback = FeignHystrix.class)
public interface UserAuthService {
    @RequestMapping(value = {"/open/account/identity/list"}, method = {RequestMethod.GET})
    RestResponse getIdentity(@RequestParam("accountName") String paramString1, @RequestParam("password") String paramString2);

    @RequestMapping(value = {"/open/account/info"}, method = {RequestMethod.GET})
    RestResponse getInfo(@RequestParam("identityId") String paramString);

    @RequestMapping(value = {"/open/account/getAcountByPhone"}, method = {RequestMethod.GET})
    RestResponse getAcountByPhone(@RequestParam("phone") String paramString);

    @RequestMapping(value = {"/open/account/identity/getIdentityByAccountCode"}, method = {RequestMethod.GET})
    RestResponse getIdentityByAccountCode(@RequestParam("accountCode") String paramString);
}
