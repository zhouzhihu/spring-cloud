package com.egrand.provider.ram.api.service;

import com.egrand.commons.base.model.RestResponse;
import com.egrand.provider.ram.api.service.impl.UserServiceImpl;
import com.egrand.provider.ram.api.model.vo.UserVo;
import com.egrand.security.feign.OAuth2FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "egrand-ram", configuration = OAuth2FeignAutoConfiguration.class, fallback = UserServiceImpl.class)
public interface UserService {
    @GetMapping("user/query/username/{username}")
    RestResponse<UserVo> findByUsername(@PathVariable("username") String username);
    @GetMapping("user/{id}")
    RestResponse<UserVo> findByUserId(@PathVariable("id") Long id);
}
