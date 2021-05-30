package com.shicheng.fusion.auth.controller;

import com.shicheng.fusion.auth.utils.SecretProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Program: fusion
 * @Date: 2021/5/29 9:57
 * @Author: shicheng
 * @Description: 密文获取
 */
@RestController
public class SecretController {

    @Autowired
    private SecretProperties secretProperties;

    @GetMapping("secret/{from}")
    public ResponseEntity<String> getSecret(@PathVariable String from){
        if (StringUtils.isBlank(from)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("访问错误");
        }
        String secret = null;
        if ("gateway".equals(from)){
            secret = secretProperties.getSecret();
        }
        return ResponseEntity.ok(secret);
    }
}
