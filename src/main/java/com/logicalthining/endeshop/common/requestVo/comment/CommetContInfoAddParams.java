package com.logicalthining.endeshop.common.requestVo.comment;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2020/1/9.
 */
@ApiModel
@Setter
@Getter
public class CommetContInfoAddParams {
    @ApiModelProperty("评论详情id")
    @PropertyCheck(name = "评论详情id")
    private String id;

    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    private String userId;

    @ApiModelProperty("评论内容")
    @PropertyCheck(name = "评论内容")
    private String content;

    @ApiModelProperty("评论图片地址")
    @PropertyCheck(name = "评论图片地址")
    private String images;

    @ApiModelProperty("评论id")
    @PropertyCheck(name = "评论id")
    private String commentId;

    @ApiModelProperty("父评论详情id")
    @PropertyCheck(name = "父评论详情id")
    private String parentId;

}