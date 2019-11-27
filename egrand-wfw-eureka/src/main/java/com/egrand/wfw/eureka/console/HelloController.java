package com.egrand.wfw.eureka.console;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloController
{
    @Value("${hello:1000000}")
    private String hello;
    @GetMapping("/hello")
    public String hello(){
        return this.hello;
    }
}

