package com.egrand.wfw.eureka;

import com.egrand.wfw.eureka.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Application
{
    public static void main(String[] args) throws Exception { SpringApplication.run(Application.class, args); }
}
