package com.shicheng.fusion.article.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Program: fusion
 * @Date: 2021/4/9 22:27
 * @Author: shicheng
 * @Description:
 */
@Data
@TableName("folder")
public class Folder {

    @TableId("folderid")
    private String folderId;

    @TableField("userid")
    private String userId;

    @TableField("name")
    private String name;

    @TableField("createdate")
    private Long createDate;
}
