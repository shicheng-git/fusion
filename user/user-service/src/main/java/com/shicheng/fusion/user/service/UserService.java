package com.shicheng.fusion.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shicheng.fusion.user.dto.ResponseUser;
import com.shicheng.fusion.user.po.User;

/**
 * @Program: fusion
 * @Date: 2021/4/7 21:19
 * @Author: shicheng
 * @Description:
 */
public interface UserService extends IService<User> {


    /**
     * 用户登录
     * @param account 账号
     * @param password 密码
     * @return
     */
    ResponseUser query(String account, String password);

    /**
     * 判断是否存在账号
     * @param account 用户账号
     * @return
     */
    boolean hasUser(String account);


    /**
     * 注册用户，账号密码注册
     * @param account 账号
     * @param password 密码
     * @return
     */
    boolean register(String account, String password);
}
