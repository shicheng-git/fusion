package com.shicheng.fusion.auth.controller;

import com.shicheng.fusion.auth.client.UserClient;
import com.shicheng.fusion.auth.service.AuthService;
import com.shicheng.fusion.user.dto.ResponseUser;
import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Program: fusion
 * @Date: 2021/5/29 11:34
 * @Author: shicheng
 * @Description: 认证controller
 */
@Slf4j
@RestController
@Api(value = "认证controller")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "用户授权登录接口", notes = "用户使用账号密码授权登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户账号", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", dataType = "String", paramType = "form", required = true)
    })
    @PostMapping("auth/accredit")
    public ResponseEntity authentication(@RequestParam String account,
                                         @RequestParam String password){
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("账号或密码不能为空");
        }
        ResponseUser responseUser = authService.authentication(account, password);
        if (responseUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败，请检查账号和密码");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(responseUser);
        }
    }


    @ApiOperation(value = "token解析接口", notes = "用于token的校验和刷新")
    @GetMapping("auth/verify")
    public ResponseEntity verifyToken(HttpServletRequest request){
        String token= request.getHeader("TOKEN");
        String refresh = authService.verifyToken(token);
        return ResponseEntity.ok(refresh);
    }
}
