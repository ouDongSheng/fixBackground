package com.logicalthining.endeshop.common.responseVo.user;

import com.logicalthining.endeshop.entity.CapitalFlowUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 客户端下级用户列表
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 下午 3:02
 **/
@ApiModel
@Setter
@Getter
public class AppUserChildUserListVo {

    /**
     * 下级名称
     * @since 下午 3:02 2019/11/14 0014
     **/
    @ApiModelProperty(value = "下级名称")
    private String name;

    /**
     * 下级账户
     * @since 下午 3:03 2019/11/14 0014
     **/
    @ApiModelProperty(value = "下级账户")
    private String account;

    /**
     * 绑定时间
     * @since 下午 3:03 2019/11/14 0014
     **/
    @ApiModelProperty(value = "绑定时间")
    private Date bindTime;

    /**
     * 在下级用户与上级用户第一次绑定时，上级用户所获取到的钱
     * 其实只需要判断分享奖就行了
     * 因为相对于下级的会员，上级可以获取的钱也就只有分享奖
     * {@link CapitalFlowUser#getType()}
     * @since 下午 2:54 2019/11/20 0020
     **/
    @ApiModelProperty(value = "获取奖励")
    private Double money;

}
