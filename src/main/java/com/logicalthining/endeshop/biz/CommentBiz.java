package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.pagehelper.PageHelper;
import com.logicalthining.endeshop.common.requestVo.comment.CommentQueryParams;
import com.logicalthining.endeshop.common.requestVo.comment.CommetContAddParams;
import com.logicalthining.endeshop.common.requestVo.comment.CommetContInfoAddParams;
import com.logicalthining.endeshop.common.responseVo.comment.CommentListVo;
import com.logicalthining.endeshop.entity.Comment;
import com.logicalthining.endeshop.entity.CommentInfo;
import com.logicalthining.endeshop.service.CommentInfoServiceI;
import com.logicalthining.endeshop.service.CommentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //TODO 先写上
        Result result = PropertyCheckUtil.checkPropertyWithIgnore(params, Lists.asList("content"));
        if (!result.getSuccess()) {
            return result;
        }

        //TODO 订单id ?
        Comment comment = new Comment()
                .setId(params.getId())
                .setOrder_id(params.getOrderId())
                .setProduct_id(params.getProductId())
                .setGrade(params.getGrade())
                .setTime(params.getTime());

        //添加评论
        commentService.add(comment);

        //添加评论详情
        List<CommetContInfoAddParams> commetContInfoAddParamsList = params.getCommetContInfoAddParamsList();
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

}