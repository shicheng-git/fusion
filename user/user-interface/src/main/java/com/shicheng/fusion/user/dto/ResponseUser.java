package com.shicheng.fusion.user.dto;

import lombok.Data;

/**
 * @Program: superblog
 * @Date: 2021/4/7 22:37
 * @Author: shicheng
 * @Description: 用户dto
 */
@Data
public class ResponseUser {

    private String userId;

    private String account;

    private String type;

    private String sign;

    private String picture;

    private String phone;

    private String email;

    private String address;

    private Long fans;

    private String token;
}
