package com.egrand.wfw.zuul.service;

import com.alibaba.fastjson.JSONObject;
import com.egrand.commons.lang.model.RestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "http://nbjcpt-log", fallback = FeignHystrix.class)
public interface FeignService {
    @RequestMapping(value = {"/say"}, method = {RequestMethod.GET})
    String say(@RequestParam("name") String paramString);

    @RequestMapping(value = {"/rest/operatelog/insertLogger"}, method = {RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    RestResponse insertLogger(@RequestBody JSONObject paramJSONObject);
}