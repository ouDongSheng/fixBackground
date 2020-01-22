package com.logicalthining.endeshop.common.responseVo.comment;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2020/1/13.
 */
@ApiModel
@Setter
@Getter
public class CommentListVo {
    @ApiModelProperty("评论id")
    @PropertyCheck(name = "评论id")
    private String id;

    @ApiModelProperty("订单id")
    @PropertyCheck(name = "订单id")
    private String orderId;

    @ApiModelProperty("产品id")
    @PropertyCheck(name = "产品id")
    private String productId;

    @ApiModelProperty("评论星级")
    @PropertyCheck(name = "评论星级")
    private String grade;

    @ApiModelProperty("评论时间")
    @PropertyCheck(name = "评论时间")
    private Date time;
}