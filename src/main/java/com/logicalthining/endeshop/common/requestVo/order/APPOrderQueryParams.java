package com.logicalthining.endeshop.common.requestVo.order;

import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/15 0015 上午 9:55
 **/
@Setter
@Getter
@ApiModel
public class APPOrderQueryParams extends PageAbleVo {

    /**
     * 订单状态 2待处理 3已发货 4已完成
     *
     * @since 上午 9:56 2019/11/15 0015
     **/
    @ApiModelProperty(value = "订单状态 2待处理 3已发货 4已完成")
    private Integer orderState;

    /**
     * 组订单id
     * @since 上午 11:13 2019/11/15 0015
     **/
    @ApiModelProperty(value = "组订单id")
    private String groupId;

    /**
     * 当前用户id
     * @since 下午 5:44 2019/11/21 0021
     **/
    private Integer currentUserId;

}
