package com.egrand.wfw.oauth.security.service;

import com.egrand.commons.lang.model.ApiResponse;
import com.egrand.wfw.oauth.api.vo.UserVo;
import com.egrand.wfw.oauth.security.FeignConfig;
import com.egrand.wfw.oauth.security.service.impl.UserServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Mr.Yangxiufeng on 2017/12/27.
 * Time:15:12
 * ProjectName:Mirco-Service-Skeleton
 */
@FeignClient(value = "service-hi", configuration = FeignConfig.class, fallback = UserServiceImpl.class)
public interface UserService {
    @GetMapping("user/findByUsername/{username}")
    ApiResponse<UserVo> findByUsername(@PathVariable("username") String username);
    @GetMapping("user/findByUserId/{userId}")
    ApiResponse<UserVo> findByUserId(@PathVariable("userId") Long userId);
}
