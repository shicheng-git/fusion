package com.shicheng.fusion.user.controller;

import com.shicheng.fusion.user.dto.ResponseUser;
import com.shicheng.fusion.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Program: fusion
 * @Date: 2021/4/7 21:22
 * @Author: shicheng
 * @Description:
 */
@Api(value = "用户controller")
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口，只用于认证模块的调用
     * @param account 账号
     * @param password 密码
     * @return 登录成功就返回用户信息，登录失败就返回错误信息
     */
    @ApiIgnore
    @PostMapping("user/query")
    public ResponseEntity query(@RequestParam String account, @RequestParam String password){
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("账号或密码不能为空");
        }
        ResponseUser user = userService.query(account, password);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    /**
     * 判断用户账号是否存在
     * @param account 用户账号
     * @return 根据结果返回布尔值
     */
    @ApiOperation(value = "用户判断", notes = "判断账号是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户账号", dataType = "String", paramType = "path", required = true)
    })
    @GetMapping("user/account/{account}")
    public ResponseEntity hasUser(@PathVariable("account") String account){
        if (StringUtils.isEmpty(account)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名不能为空");
        }
        boolean hasUser = userService.hasUser(account);
        return ResponseEntity.ok(hasUser);
    }


    /**
     * 用户注册接口，通过账号密码注册
     * @param account 账号
     * @param password 密码
     * @return
     */
    @ApiOperation(value = "用户注册接口", notes = "通过账号密码注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户账号", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", dataType = "String", paramType = "form", required = true)
    })
    @PostMapping("user/register")
    public ResponseEntity registerByAccount(@RequestParam String account, @RequestParam String password){
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("账号或密码不能为空");
        }
        //判断账号是否合法
        if (account.length() > 20){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("账号长度不能超过20");
        }
        String  regex ="^[0-9a-zA-Z_]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(account);
        if (!m.matches()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("账号只能由字母数字下划线组成");
        }
        boolean register = userService.register(account, password);
        if (register){
            return ResponseEntity.status(HttpStatus.CREATED).body("注册成功");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败，请稍后再试");
        }
    }

}
