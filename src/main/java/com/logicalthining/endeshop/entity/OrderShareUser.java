package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 用户分享订单记录，记录了订单下单时是由谁分享过来的
 * @author chenLiJia
 * @since 2019-11-06 11:05:01
 * @version 1.0
 **/
@ApiModel("用户分享订单记录，记录了订单下单时是由谁分享过来的")
@Table(name = "s_order_share_user")
@Setter
@Getter
@Accessors(chain = true)
public class OrderShareUser {
    /**
     * 组订单id
     */
    @ApiModelProperty("组订单id")
    @PropertyCheck(name = "组订单id")
    @Id
    @Column(name = "group_id")
    private String groupId;

    /**
     * 分享的用户Id
     */
    @ApiModelProperty("分享的用户Id")
    @PropertyCheck(name = "分享的用户Id")
    @Column(name = "share_user_id")
    private Integer shareUserId;

}
