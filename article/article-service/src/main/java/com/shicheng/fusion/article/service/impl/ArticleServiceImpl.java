package com.shicheng.fusion.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shicheng.fusion.article.mapper.ArticleMapper;
import com.shicheng.fusion.article.po.Article;
import com.shicheng.fusion.article.service.ArticleService;
import com.shicheng.fusion.common.utils.KeyId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Program: fusion
 * @Date: 2021/4/8 22:30
 * @Author: shicheng
 * @Description:
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public boolean release(String userId, String folderId, String type, String content, String status) {
        boolean result = false;
        Article article = new Article();
        article.setArticleId(KeyId.nextId());
        article.setUserId(userId);
        article.setFolderId(folderId);
        article.setContent(content);
        if (StringUtils.isNotBlank(type)) {
            article.setType(type);
        }
        if (StringUtils.isNotBlank(status)) {
            article.setStatus(status);
        }
        article.setCreateDate(new Date().getTime());

        int insert = articleMapper.insert(article);
        if (insert > 0){
            result = true;
        }
        return result;
    }

    @Override
    public boolean hasArticle(String articleId) {
        Article article = articleMapper.selectById(articleId);
        if (article == null){
            return false;
        }else {
            return true;
        }
    }


}
