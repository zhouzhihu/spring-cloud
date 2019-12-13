package com.egrand.wfw.oauth.security.service.feign;

import com.egrand.cloud.ram.client.service.IUserServiceClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 用户FeignClient调用
 */
@Component
@FeignClient(value = "egrand-ram", fallback = UserServiceClientImpl.class)
public interface UserServiceClient extends IUserServiceClient {


}
