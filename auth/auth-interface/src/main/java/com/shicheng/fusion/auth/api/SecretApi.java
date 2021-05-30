package com.shicheng.fusion.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Program: fusion
 * @Date: 2021/5/29 11:30
 * @Author: shicheng
 * @Description:
 */
public interface SecretApi {

    @GetMapping("secret/{from}")
    String getSecret(@PathVariable String from);
}
