package com.shicheng.fusion.auth.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Program: fusion
 * @Date: 2021/5/29 11:17
 * @Author: shicheng
 * @Description: 读取配置文件
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class SecretProperties {

    private String secret;
    private String expire;

    public String getSecret(){
        return secret;
    }

    public void setSecret(String secret){
        this.secret = secret;
    }

    public String getExpire(){
        return expire;
    }

    public void setExpire(String expire){
        this.expire = expire;
    }

}
