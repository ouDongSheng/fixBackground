package com.logicalthining.endeshop.common.requestVo.userScore;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 新晋合伙人查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/11 0011 下午 5:50
 **/
@Setter
@Getter
@Accessors(chain = true)
public class NewPartnerQueryParams {

    /**
     * 祖宗id
     *
     * @since 下午 5:51 2019/11/11 0011
     **/
    private Integer ancestorId;

    /**
     * 用户账户
     *
     * @since 下午 5:52 2019/11/11 0011
     **/
    private String userAccount;

    /**
     * 晋升时间筛选
     *
     * @since 下午 5:52 2019/11/11 0011
     **/
    private Date promotionTimeStartTime;

    /**
     * 晋升时间筛选
     *
     * @since 下午 5:52 2019/11/11 0011
     **/
    private Date promotionTimeEndTime;

}
