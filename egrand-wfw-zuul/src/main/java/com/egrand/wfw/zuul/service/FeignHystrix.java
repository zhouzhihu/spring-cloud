package com.egrand.wfw.zuul.service;

import com.alibaba.fastjson.JSONObject;
import com.egrand.commons.lang.model.RestResponse;
import org.springframework.stereotype.Component;

@Component
public class FeignHystrix implements FeignService {
    public String say(String name) {
        return "hi," + name + ",sorry,error!";
    }

    public RestResponse insertLogger(JSONObject json) {
        return RestResponse.failed("0101", "连接日志中心失败！");
    }
}
