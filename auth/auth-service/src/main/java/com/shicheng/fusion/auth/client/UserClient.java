package com.shicheng.fusion.auth.client;

import com.shicheng.fusion.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Program: fusion
 * @Date: 2021/5/30 9:29
 * @Author: shicheng
 * @Description:
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
