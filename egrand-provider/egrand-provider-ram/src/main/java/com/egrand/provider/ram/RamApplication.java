package com.egrand.provider.ram;

import com.egrand.commons.swagger.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@Import({
        SwaggerConfig.class
})
public class RamApplication {
    public static void main(String[] args) {
        SpringApplication.run(RamApplication.class, args);
    }
}