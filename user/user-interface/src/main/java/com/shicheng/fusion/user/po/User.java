package com.shicheng.fusion.user.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Program: fusion
 * @Date: 2021/4/6 23:29
 * @Author: shicheng
 * @Description: 用户表映射类
 */
@Data
@TableName("user")
public class User implements Serializable {

    @TableId("userid")
    private String userId;

    @TableField("account")
    private String account;//用户账号

    @TableField("password")
    private String password;//密码

    @TableField("username")
    private String username;//用户名

    @TableField("type")
    private String type;//用户类型，00管理员，01普通用户

    @TableField("sign")
    private String sign;//签名

    @TableField("picture")
    private String picture;//头像

    @TableField("phone")
    private String phone;//手机

    @TableField("email")
    private String email;//邮箱

    @TableField("address")
    private String address;//地址

    @TableField("createdate")
    private Long createDate;//创建日期

    @TableField("lastlogindate")
    private Long lastLoginDate;//最后一次登录时间

    @TableField("valid")
    private String valid;//是否有效，00有效，01无效

    @TableField("remark")
    private String remark;//备注

    @TableField("fans")
    private Long fans;//粉丝数
}
