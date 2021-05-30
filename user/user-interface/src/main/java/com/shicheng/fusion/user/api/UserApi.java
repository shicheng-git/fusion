package com.shicheng.fusion.user.api;

import com.shicheng.fusion.user.dto.ResponseUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Program: fusion
 * @Date: 2021/5/30 9:17
 * @Author: shicheng
 * @Description:
 */
public interface UserApi {

    /**
     * 用户登录接口
     * @param account 账号
     * @param password 密码
     * @return 登录成功就返回用户信息，登录失败就返回错误信息
     */
    @PostMapping("user/query")
    ResponseUser query(@RequestParam String account, @RequestParam String password);
}
