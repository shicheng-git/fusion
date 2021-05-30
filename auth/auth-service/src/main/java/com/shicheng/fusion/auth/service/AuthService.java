package com.shicheng.fusion.auth.service;

import com.shicheng.fusion.user.dto.ResponseUser;


/**
 * @Program: fusion
 * @Date: 2021/5/30 9:38
 * @Author: shicheng
 * @Description:
 */
public interface AuthService {

    ResponseUser authentication(String account, String password);

    String verifyToken(String token);
}
