package com.egrand.wfw.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinStreamServer
public class Application {
    public static void main(String[] args) throws Exception { SpringApplication.run(Application.class, args); }
}
