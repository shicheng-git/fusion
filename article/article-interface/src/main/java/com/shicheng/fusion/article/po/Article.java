package com.shicheng.fusion.article.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Program: fusion
 * @Date: 2021/4/8 22:22
 * @Author: shicheng
 * @Description: 文章实体映射类
 */
@Data
@TableName("article")
public class Article implements Serializable {

    @TableId("articleid")
    private String articleId;

    @TableField("userid")
    private String userId;

    @TableField("folderid")
    private String folderId;

    @TableField("title")
    private String title;//标题

    @TableField("type")
    private String type;//类型，00文章

    @TableField("content")
    private String content;//内容

    @TableField("show")
    private String show;//是否展示，00展示，01隐藏

    @TableField("createdate")
    private Long CreateDate;

    @TableField("lastupdate")
    private Long LastUpdate;

    @TableField("likes")
    private Long Likes;//点赞数

    @TableField("status")
    private String status;//状态，00草稿，01发布，02无效
}
