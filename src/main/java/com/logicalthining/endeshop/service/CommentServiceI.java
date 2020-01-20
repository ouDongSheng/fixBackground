package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.comment.CommentQueryParams;
import com.logicalthining.endeshop.common.responseVo.comment.CommentListVo;
import com.logicalthining.endeshop.entity.Comment;

import java.util.List;


/**
 * Created by Administrator on 2020/1/9.
 */
public interface CommentServiceI {
    Result add(Comment params);

    List<CommentListVo> listAll(CommentQueryParams params);
}