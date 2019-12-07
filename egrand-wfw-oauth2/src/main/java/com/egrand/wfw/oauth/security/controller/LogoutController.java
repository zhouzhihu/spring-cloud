package com.egrand.wfw.oauth.security.controller;

import com.egrand.commons.base.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-12-07
 * Time: 19:17
 */
@RestController
public class LogoutController {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @DeleteMapping(value = "/exit")
    public @ResponseBody
    RestResponse revokeToken(String access_token){
        if (consumerTokenServices.revokeToken(access_token)){
            return RestResponse.success("注销成功");
        }else {
            return RestResponse.failed("002", "注销失败");
        }
    }
}
