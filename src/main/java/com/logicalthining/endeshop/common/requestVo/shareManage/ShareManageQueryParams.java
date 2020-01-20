package com.logicalthining.endeshop.common.requestVo.shareManage;

import com.logicalthining.endeshop.common.requestVo.PageTimeLimitQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分享记录查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 上午 10:41
 **/
@ApiModel
@Setter
@Getter
public class ShareManageQueryParams extends PageTimeLimitQueryVo {

    /**
     * 搜索条件
     * 手机号  即用户的账户 因为列表里只显示了用户的账户
     * 订单编号
     *
     * @since 上午 10:41 2019/11/7 0007
     **/
    @ApiModelProperty(value = "搜索条件 手机号、订单编号")
    private String searchKey;

}
