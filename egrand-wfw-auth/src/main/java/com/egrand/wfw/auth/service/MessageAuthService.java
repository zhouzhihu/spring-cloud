package com.egrand.wfw.auth.service;

import com.egrand.wfw.auth.security.FeignConfig;
import com.egrand.wfw.auth.service.impl.FeignHystrix;
import com.egrand.commons.lang.model.RestResponse;
import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "http://nbjcpt-message", configuration = {FeignConfig.class}, fallback = FeignHystrix.class)
public interface MessageAuthService {
    @RequestMapping(value = {"/rest/sms/sendMesCode"}, method = {RequestMethod.POST})
    RestResponse sendMessage(@RequestBody Map<String, String> paramMap);

    @RequestMapping(value = {"/rest/sms/vSend"}, method = {RequestMethod.POST})
    RestResponse sendMessage(@RequestParam("phone") String paramString1, @RequestParam("content") String paramString2, @RequestParam("type") Integer paramInteger);
}
