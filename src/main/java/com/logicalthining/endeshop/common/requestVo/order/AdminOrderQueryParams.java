package com.logicalthining.endeshop.common.requestVo.order;

import com.github.chenlijia1111.utils.dateTime.DateTimeConvertUtil;
import com.logicalthining.endeshop.common.requestVo.PageTimeLimitQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 后台订单列表查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 2:29
 **/
@ApiModel
@Setter
@Getter
public class AdminOrderQueryParams extends PageTimeLimitQueryVo {


    /**
     * 搜索条件
     * 订单号、收货人手机号
     *
     * @since 下午 2:29 2019/11/7 0007
     **/
    @ApiModelProperty(value = "搜索条件")
    private String searchKey;


    /**
     * 订单状态
     * 订单状态定义 1初始状态 2已付款 3已发货 4已收货 5已取消
     *
     * @since 下午 2:36 2019/11/7 0007
     **/
    @ApiModelProperty(value = "订单状态 1初始状态 2已付款 3已发货 4已收货 5已取消")
    private Integer orderState;

    @Override
    public void setStartTime(Date startTime) {
        startTime = DateTimeConvertUtil.initStartTime(startTime);
        super.setStartTime(startTime);
    }

    @Override
    public void setEndTime(Date endTime) {
        endTime = DateTimeConvertUtil.initEndTime(endTime);
        super.setEndTime(endTime);
    }
}
