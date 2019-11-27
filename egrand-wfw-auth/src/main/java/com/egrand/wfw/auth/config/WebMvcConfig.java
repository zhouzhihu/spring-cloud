package com.egrand.wfw.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig
        extends WebMvcConfigurerAdapter
{
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[] { "/static/**" }).addResourceLocations(new String[] { "classpath:/page/" });
        super.addResourceHandlers(registry);
    }

    public void addViewControllers(ViewControllerRegistry registry) {}
}