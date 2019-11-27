package com.egrand.wfw.zipkin.console;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloController
{
    @Value("${hello}")
    private String hello;

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eureka;

    @GetMapping("/hello")
    public String hello(){
        return this.hello + " " + this.eureka;
    }
}

