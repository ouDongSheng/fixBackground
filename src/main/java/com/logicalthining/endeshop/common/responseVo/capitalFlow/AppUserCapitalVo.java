package com.logicalthining.endeshop.common.responseVo.capitalFlow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

/**
 * 客户端用户流水返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 上午 9:20
 **/
@Setter
@Getter
@ApiModel
public class AppUserCapitalVo {

    /**
     * 流水类别 1分享奖收益 2提现
     *
     * @since 上午 9:22 2019/11/14 0014
     **/
    private Integer type;

    /**
     * 流水类别名称
     *
     * @since 上午 9:22 2019/11/14 0014
     **/
    @ApiModelProperty(value = "流水类别名称")
    private String typeName;

    /**
     * 交易金额
     *
     * @since 上午 9:22 2019/11/14 0014
     **/
    @ApiModelProperty(value = "交易金额")
    private Double transactionMoney;

    /**
     * 时间
     *
     * @since 上午 9:22 2019/11/14 0014
     **/
    @ApiModelProperty(value = "时间")
    private Date createTime;


    public void setType(Integer type) {
        this.type = type;
        if (Objects.nonNull(type)) {
            switch (type) {
                case 1:
                    this.typeName = "收益";
                    break;
                case 2:
                    this.typeName = "提现";
                    break;
            }
        }
    }
}
