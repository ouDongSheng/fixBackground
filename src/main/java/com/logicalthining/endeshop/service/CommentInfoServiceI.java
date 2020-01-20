package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.CommentInfo;

import java.util.List;

/**
 * Created by Administrator on 2020/1/9.
 */
public interface CommentInfoServiceI {
    Result add(List<CommentInfo> commentInfoList);
}