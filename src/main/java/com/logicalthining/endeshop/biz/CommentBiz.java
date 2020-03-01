package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.pagehelper.PageHelper;
import com.logicalthining.endeshop.common.requestVo.comment.CommentQueryParams;
import com.logicalthining.endeshop.common.requestVo.comment.CommetContAddParams;
import com.logicalthining.endeshop.common.requestVo.comment.CommentContInfoAddParams;
import com.logicalthining.endeshop.common.responseVo.comment.CommentListVo;
import com.logicalthining.endeshop.entity.Comment;
import com.logicalthining.endeshop.entity.CommentInfo;
import com.logicalthining.endeshop.service.CommentInfoServiceI;
import com.logicalthining.endeshop.service.CommentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/1/8.
 */
@Service
public class CommentBiz {
    @Autowired
    private  CommentServiceI commentService;
    @Autowired
    private CommentInfoServiceI commentInfoService;


    public Result add(CommetContAddParams params) {
        Result result = PropertyCheckUtil.checkPropertyWithIgnore(params, Lists.asList("content"));
        if (!result.getSuccess()) {
            return result;
        }

        Date currentTime = new Date();
        //TODO 订单id ?
        Comment comment = new Comment()
                .setId(params.getId())
                .setOrderId(params.getOrderId())
                .setProductId(params.getProductId())
                .setGrade(params.getGrade())
                .setTime(LocalDate.now());

        //添加评论
        commentService.add(comment);

        //添加评论详情
        List<CommentContInfoAddParams> commetContInfoAddParamsList = params.getCommentContInfoAddParamsList();
        List<CommentInfo> commentInfos = commetContInfoAddParamsList.stream().map(e -> new CommentInfo().
                 setId(e.getId())
                .setUserId(e.getUserId())
                .setContent(e.getContent())
                .setImages(e.getImages())
                .setCommentId(e.getCommentId())
                .setParentId(e.getParentId()))
                .collect(Collectors.toList());

        commentInfoService.add(commentInfos);

        return Result.success("操作成功");
    }

    public Result listAll(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());

        List<CommentListVo> commentListVos = commentService.listAll(params);

        return Result.success("查询成功", commentListVos);
    }

    public Result listImage(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());

        List<CommentListVo> commentListVos = commentService.listImage(params);

        return Result.success("查询成功", commentListVos);
    }

    public Result listGood(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());

        List<CommentListVo> commentListVos = commentService.listGood(params);

        return Result.success("查询成功", commentListVos);
    }

    public Result listBad(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());

        List<CommentListVo> commentListVos = commentService.listBad(params);

        return Result.success("查询成功", commentListVos);
    }

    public Result listMedium(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());

        List<CommentListVo> commentListVos = commentService.listMedium(params);

        return Result.success("查询成功", commentListVos);
    }

    public Result listAllCount(CommentQueryParams params) {
        return Result.success("查询成功", commentService.listAllCount(params));
    }



}