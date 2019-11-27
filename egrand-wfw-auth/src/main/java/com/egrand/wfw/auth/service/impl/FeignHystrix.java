package com.egrand.wfw.auth.service.impl;

import com.egrand.wfw.auth.service.AppAuthService;
import com.egrand.wfw.auth.service.UserAuthService;
import com.egrand.commons.lang.model.RestResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class FeignHystrix
        implements UserAuthService, AppAuthService {
    public RestResponse getIdentity(@RequestParam("accountName") String accountName, @RequestParam("password") String password) {
        return RestResponse.failed("0101", "连接用户中心失败");
    }

    public RestResponse getInfo(@RequestParam("identityId") String identityId) {
        return RestResponse.failed("0101", "连接用户中心失败");
    }

    public RestResponse yyglGetQueryByAP(@RequestParam("account") String account) {
        return RestResponse.failed("0101", "连接用户中心失败");
    }

    public RestResponse getAcountByPhone(@RequestParam("phone") String phone) {
        return RestResponse.failed("0101", "连接用户中心失败");
    }

    public RestResponse getIdentityByAccountCode(@RequestParam("accountCode") String accountCode) {
        return RestResponse.failed("0101", "连接用户中心失败");
    }
}
