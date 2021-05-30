package com.shicheng.fusion.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shicheng.fusion.common.utils.KeyId;
import com.shicheng.fusion.common.utils.UserUtils;
import com.shicheng.fusion.user.dto.ResponseUser;
import com.shicheng.fusion.user.mapper.UserMapper;
import com.shicheng.fusion.user.po.User;
import com.shicheng.fusion.user.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Program: fusion
 * @Date: 2021/4/7 21:21
 * @Author: shicheng
 * @Description:
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseUser query(String account, String password) {
        ResponseUser responseUser = null;
        String passwordEncryption = null;
        try {
            passwordEncryption = new String(Base64.encodeBase64(password.getBytes("UTF-8")));
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("account", account)
                    .eq("password", passwordEncryption);
            User user = userMapper.selectOne(wrapper);
            if (user != null){
                responseUser = new ResponseUser();
                responseUser.setUserId(user.getUserId());
                responseUser.setAccount(user.getAccount());
                responseUser.setAddress(user.getAddress());
                responseUser.setEmail(user.getEmail());
                responseUser.setFans(user.getFans());
                responseUser.setPhone(user.getPhone());
                responseUser.setPicture(user.getPicture());
                responseUser.setSign(user.getSign());
                responseUser.setType(user.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseUser;
    }

    @Override
    public boolean hasUser(String account) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        User user = userMapper.selectOne(wrapper);
        if (user == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean register(String account, String password) {
        boolean result = false;
        synchronized(this){
            boolean hasUser = hasUser(account);
            if (!hasUser){
                try {
                    User user = new User();
                    user.setUserId(KeyId.nextId());
                    user.setAccount(account);
                    user.setPassword(new String(Base64.encodeBase64(password.getBytes("UTF-8"))));
                    user.setUsername("新用户");
                    user.setType(UserUtils.USER_TYPE_NORMAL);//默认普通用户
                    user.setCreateDate(new Date().getTime());
                    user.setValid(UserUtils.USER_VALID_YES);//默认有效

                    int insert = userMapper.insert(user);
                    if (insert > 0){
                        result = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
