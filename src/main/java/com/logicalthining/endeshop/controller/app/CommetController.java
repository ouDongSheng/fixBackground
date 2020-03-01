package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.CommentBiz;
import com.logicalthining.endeshop.common.requestVo.comment.CommentQueryParams;
import com.logicalthining.endeshop.common.requestVo.comment.CommetContAddParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 商品全部评论
     *
     **/
    @GetMapping(value = "all")
    @ApiOperation(value = "查询全部商品评论")
    public Result listAll(CommentQueryParams commentQueryParams) {
        return commentBiz.listAll(commentQueryParams);
    }

    /**
     * 商品带图评论
     *
     **/
    @GetMapping(value = "image")
    @ApiOperation(value = "查询商品带图评论")
    public Result listImage(CommentQueryParams commentQueryParams) {
        return commentBiz.listImage(commentQueryParams);
    }

    /**
     * 查询好评列表
     *
     **/
    @GetMapping(value = "good")
    @ApiOperation(value = "查询好评列表")
    public Result listGood(CommentQueryParams commentQueryParams) {
        return commentBiz.listGood(commentQueryParams);
    }

    /**
     * 查询好评列表
     *
     **/
    @GetMapping(value = "bad")
    @ApiOperation(value = "查询差评列表")
    public Result listBad(CommentQueryParams commentQueryParams) {
        return commentBiz.listBad(commentQueryParams);
    }

   /**
     * 查询中评列表
     *
     **/
    @GetMapping(value = "medium")
    @ApiOperation(value = "查询中评列表")
    public Result listMedium(CommentQueryParams commentQueryParams) {
        return commentBiz.listMedium(commentQueryParams);
    }

    /**
     * 查询评论人数
     *
     **/
    @GetMapping(value = "listallcount")
    @ApiOperation(value = "查询评论人数")
    public Result listAllCount(CommentQueryParams commentQueryParams) {
        return commentBiz.listAllCount(commentQueryParams);
    }
}