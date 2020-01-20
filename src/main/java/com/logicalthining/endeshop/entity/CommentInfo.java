package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2020/1/9.
 */
@ApiModel("评论详情表")
@Table(name = "s_commentInfo")
@Setter
@Getter
@Accessors(chain = true)
public class CommentInfo {
    @ApiModelProperty("评论详情id")
    @PropertyCheck(name = "评论详情id")
    @Id
    @Column(name = "id")
    private String id;

    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("评论内容")
    @PropertyCheck(name = "评论内容")
    @Column(name = "content")
    private String content;

    @ApiModelProperty("评论图片地址")
    @PropertyCheck(name = "评论图片地址")
    @Column(name = "images")
    private String images;

    @ApiModelProperty("评论id")
    @PropertyCheck(name = "评论id")
    @Column(name = "comment_id")
    private String commentId;

    @ApiModelProperty("父评论详情id")
    @PropertyCheck(name = "父评论详情id")
    @Column(name = "parent_id")
    private String parentId;
}