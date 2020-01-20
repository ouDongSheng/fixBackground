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
@ApiModel("评论表")
@Table(name = "s_comment")
@Setter
@Getter
@Accessors(chain = true)
public class Comment {
    @ApiModelProperty("评论id")
    @PropertyCheck(name = "评论id")
    @Id
    @Column(name = "id")
    private String id;

    @ApiModelProperty("订单id")
    @PropertyCheck(name = "订单id")
    @Column(name = "order_id")
    private String order_id;

    @ApiModelProperty("产品id")
    @PropertyCheck(name = "产品id")
    private String product_id;

    @ApiModelProperty("评论星级")
    @PropertyCheck(name = "评论星级")
    @Column(name = "grade")
    private String grade;

    @ApiModelProperty("评论时间")
    @PropertyCheck(name = "评论时间")
    @Column(name = "time")
    private String time;
}