package com.shicheng.fusion.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shicheng.fusion.article.mapper.FolderMapper;
import com.shicheng.fusion.article.po.Folder;
import com.shicheng.fusion.article.service.FolderService;
import org.springframework.stereotype.Service;

/**
 * @Program: fusion
 * @Date: 2021/4/9 22:46
 * @Author: shicheng
 * @Description:
 */
@Service("folderService")
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {
}
