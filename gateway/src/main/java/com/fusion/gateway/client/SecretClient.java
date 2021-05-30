package com.fusion.gateway.client;

import com.shicheng.fusion.auth.api.SecretApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Program: fusion
 * @Date: 2021/5/30 16:13
 * @Author: shicheng
 * @Description:
 */
@FeignClient(value = "auth-service")
public interface SecretClient extends SecretApi {
}
