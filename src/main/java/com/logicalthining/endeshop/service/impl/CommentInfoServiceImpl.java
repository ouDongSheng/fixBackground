package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.dao.CommentInfoMapper;
import com.logicalthining.endeshop.entity.CommentInfo;
import com.logicalthining.endeshop.service.CommentInfoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2020/1/9.
 */
@Service
public class CommentInfoServiceImpl implements CommentInfoServiceI {
    @Autowired
    private CommentInfoMapper commentInfoMapper;

    @Override
    public Result add(List<CommentInfo> commentInfoList) {
        if (Lists.isNotEmpty(commentInfoList)) {
            commentInfoMapper.batchAdd(commentInfoList);
        }
        return Result.success("操作成功");
    }
}