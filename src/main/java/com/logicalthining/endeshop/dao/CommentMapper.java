package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.comment.CommentQueryParams;
import com.logicalthining.endeshop.common.responseVo.comment.CommentCount;
import com.logicalthining.endeshop.common.responseVo.comment.CommentListVo;
import com.logicalthining.endeshop.entity.Comment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2020/1/9.
 */
public interface CommentMapper extends Mapper<Comment> {

    List<CommentListVo> listAll(CommentQueryParams params);

    List<CommentListVo> listGood(CommentQueryParams params);

    List<CommentListVo> listMedium(CommentQueryParams params);

    List<CommentListVo> listBad(CommentQueryParams params);

    CommentCount allCount(CommentQueryParams params);

    CommentCount goodCount(CommentQueryParams params);

    CommentCount mediumCount(CommentQueryParams params);

    CommentCount badCount(CommentQueryParams params);

    CommentCount imageCount(CommentQueryParams params);
}