package com.shicheng.fusion.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shicheng.fusion.article.po.Article;
import com.shicheng.fusion.article.po.Folder;
import com.shicheng.fusion.article.service.ArticleService;
import com.shicheng.fusion.article.service.FolderService;
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

/**
 * @Program: fusion
 * @Date: 2021/4/8 22:34
 * @Author: shicheng
 * @Description:
 */
@Api(value = "文章controller")
@Slf4j
@RestController
public class ArticleController {

    private static int PAGE_SIZE = 10;//默认分页大小
    private static int PAGE_NUM = 1;//默认分页页数

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FolderService folderService;

    /**
     * 发布文章接口
     * @param userId 作者id
     * @param folderId 文件夹id
     * @param type 文章类型
     * @param content 内容
     * @param status 状态
     * @return
     */
    @ApiOperation(value = "发布文章接口", notes = "用户发布文章接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(name = "folderId", value = "文件夹id", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(name = "type", value = "文章类型", dataType = "String", paramType = "form", required = false),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String", paramType = "form", required = false),
    })
    @PostMapping("article")
    public ResponseEntity release(@RequestParam String userId,
                                  @RequestParam String folderId,
                                  @RequestParam String type,
                                  @RequestParam String content,
                                  @RequestParam String status){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(folderId) || StringUtils.isBlank(content)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请传入必要的参数");
        }
        boolean release = articleService.release(userId, folderId, type, content, status);
        if (release){
            return ResponseEntity.status(HttpStatus.CREATED).body("发布成功");
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("发布失败，请稍后再试");
        }
    }


    /**
     * 改变文章状态
     * @param articleId 文章id
     * @param status 状态
     * @return
     */
    @ApiOperation(value = "改变文章状态接口", notes = "用户改变文章状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String", paramType = "form", required = true),
    })
    @PatchMapping("article/status")
    public ResponseEntity changeStatus(@RequestParam String articleId,
                                       @RequestParam String status){
        if (StringUtils.isBlank(articleId) || StringUtils.isBlank(status)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请传入必要的参数");
        }
        Article article = articleService.getById(articleId);
        if (article == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文章不存在");
        }
        article.setStatus(status);
        boolean update = articleService.updateById(article);
        if (update){
            return ResponseEntity.status(HttpStatus.OK).body("修改成功");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改失败，请稍后再试");
        }
    }


    /**
     * 改变文章的文件夹
     * @param articleId 文章id
     * @param folderId 文件夹id
     * @return
     */
    @ApiOperation(value = "改变文章的文件夹", notes = "用户改变文章的所属文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(name = "folderId", value = "文件夹id", dataType = "String", paramType = "form", required = true),
    })
    @PatchMapping("article/folder")
    public ResponseEntity changeFolder(@RequestParam String articleId,
                                       @RequestParam String folderId){
        if (StringUtils.isBlank(articleId) || StringUtils.isBlank(folderId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请传入必要的参数");
        }
        Article article = articleService.getById(articleId);
        if (article == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文章不存在");
        }
        Folder folder = folderService.getById(folderId);
        if (folder == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文件夹不存在");
        }
        article.setFolderId(folderId);
        boolean update = articleService.updateById(article);
        if (update){
            return ResponseEntity.status(HttpStatus.OK).body("修改成功");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改失败，请稍后再试");
        }
    }


    /**
     * 切换文章是否展示
     * @param articleId 文章id
     * @param show 状态
     * @return
     */
    @ApiOperation(value = "切换文章是否展示", notes = "切换文章的展示状态，展示/隐藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(name = "show", value = "状态", dataType = "String", paramType = "form", required = true),
    })
    @PatchMapping("article/show")
    public ResponseEntity changeShow(@RequestParam String articleId,
                                     @RequestParam String show){
        if (StringUtils.isBlank(articleId) || StringUtils.isBlank(show)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请传入必要的参数");
        }
        Article article = articleService.getById(articleId);
        if (article == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文章不存在");
        }
        article.setShow(show);
        boolean update = articleService.updateById(article);
        if (update){
            return ResponseEntity.status(HttpStatus.OK).body("修改成功");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改失败，请稍后再试");
        }
    }


    /**
     * 删除文章
     * @param articleId 文章id
     * @return
     */
    @ApiOperation(value = "删除文章", notes = "使用文章id，删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", dataType = "String", paramType = "form", required = true),
    })
    @DeleteMapping("article")
    public ResponseEntity delete(@RequestParam String articleId){
        if (StringUtils.isBlank(articleId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请传入必要的参数");
        }
        boolean remove = articleService.removeById(articleId);
        if (remove){
            return ResponseEntity.status(HttpStatus.OK).body("删除成功");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败，请稍后再试");
        }
    }


    /**
     * 根据文章id查询文章
     * @param articleId 文章id
     * @return
     */
    @ApiOperation(value = "文章查询接口", notes = "使用文章id，查询文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", dataType = "String", paramType = "path", required = true),
    })
    @GetMapping("article/{articleId}")
    public ResponseEntity findArticleById(@PathVariable("articleId") String articleId){
        if (StringUtils.isBlank(articleId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请传入必要的参数");
        }
        Article article = articleService.getById(articleId);
        if (article != null){
            return ResponseEntity.status(HttpStatus.OK).body(article);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文章不存在");
        }
    }


    /**
     * 分页查询，按照文章标题模糊查询
     * @param userId 作者id
     * @param title 标题
     * @param type 类型
     * @param show 展示状态
     * @param status 文章状态
     * @param pageNum 分页页数
     * @param pageSize 分页大小
     * @return
     */
    @ApiOperation(value = "文章查询接口", notes = "多条件查询，其中标题按照模糊查询，其余条件精确查询，带有分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "作者id", dataType = "String", paramType = "form", required = false),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "form", required = false),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "form", required = false),
            @ApiImplicitParam(name = "show", value = "展示状态", dataType = "String", paramType = "form", required = false),
            @ApiImplicitParam(name = "status", value = "文章状态", dataType = "String", paramType = "form", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页数", dataType = "int", paramType = "form", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "form", required = false),
    })
    @PostMapping("articles/title")
    public ResponseEntity fuzzyQueryByTitle(@RequestParam String userId,
                                            @RequestParam String title,
                                            @RequestParam String type,
                                            @RequestParam String show,
                                            @RequestParam String status,
                                            @RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize
    ){
        if (StringUtils.isBlank(title)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请传入必要的参数");
        }
        if (pageNum == null){
            pageNum = PAGE_NUM;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }
        Page<Article> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNoneBlank(userId), "userid", userId)
                .like(StringUtils.isNoneBlank(title),"title", title)
                .eq(StringUtils.isNoneBlank(type),"type", type)
                .eq(StringUtils.isNoneBlank(show), "show", show)
                .eq(StringUtils.isNoneBlank(status), "status", status);
        IPage<Article> iPage = articleService.page(page, wrapper);
        //TODO 按照关键字保存到缓存或者搜索引擎中
        return ResponseEntity.ok(iPage);
    }

}
