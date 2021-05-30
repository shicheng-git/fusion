package com.shicheng.fusion.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shicheng.fusion.article.po.Article;

/**
 * @Program: fusion
 * @Date: 2021/4/8 22:29
 * @Author: shicheng
 * @Description:
 */
public interface ArticleService extends IService<Article> {

    /**
     * 发布文章
     * @param userId 发布用户id
     * @param folderId 所属文件夹id
     * @param type 类型，00文章
     * @param content 文章内容，富文本编辑器内容
     * @param status 状态，00草稿，01发布，02无效
     * @return
     */
    boolean release(String userId, String folderId, String type, String content, String status);


    /**
     * 是否存在文章
     * @param articleId 文章id
     * @return
     */
    boolean hasArticle(String articleId);

}
