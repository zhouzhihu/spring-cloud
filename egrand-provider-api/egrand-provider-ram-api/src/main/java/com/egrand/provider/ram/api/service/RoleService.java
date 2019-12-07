package com.egrand.provider.ram.api.service;

import com.egrand.commons.base.model.RestResponse;
import com.egrand.provider.ram.api.service.impl.RoleServiceImpl;
import com.egrand.provider.ram.api.model.vo.RoleVo;
import com.egrand.security.feign.OAuth2FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(value = "egrand-ram", configuration = OAuth2FeignAutoConfiguration.class, fallback = RoleServiceImpl.class)
public interface RoleService {
    @GetMapping("role/query/userId/{userId}")
    RestResponse<List<RoleVo>> getRoleByUserId(@PathVariable("userId") Integer userId);
}
