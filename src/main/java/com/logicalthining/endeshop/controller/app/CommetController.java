package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.CommentBiz;
import com.logicalthining.endeshop.common.requestVo.comment.CommentQueryParams;
import com.logicalthining.endeshop.common.requestVo.comment.CommetContAddParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020/1/8.
 */
@RestController
@RequestMapping(value = "app/comment")
@Api(tags = "评论接口")
public class CommetController {

    @Autowired
    private CommentBiz commentBiz;

    /**
     * 添加评论
     *
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "添加评论")
    public Result add(@RequestBody CommetContAddParams params) {
        return commentBiz.add(params);
    }

    /**
     * 全部评论
     *
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "查询全部商品评论")
    public Result listAll(CommentQueryParams commentQueryParams) {
        return commentBiz.listAll(commentQueryParams);
    }

}