package com.egrand.wfw.oauth.security.service;

import com.egrand.commons.lang.model.ApiResponse;
import com.egrand.wfw.oauth.api.vo.RoleVo;
import com.egrand.wfw.oauth.security.service.impl.RoleServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


/**
 * Created by Mr.Yangxiufeng on 2017/12/29.
 * Time:12:30
 * ProjectName:Mirco-Service-Skeleton
 */
@FeignClient(value = "service-hi", fallback = RoleServiceImpl.class)
public interface RoleService {
    @GetMapping("role/getRoleByUserId/{userId}")
    ApiResponse<List<RoleVo>> getRoleByUserId(@PathVariable("userId") Integer userId);
}
