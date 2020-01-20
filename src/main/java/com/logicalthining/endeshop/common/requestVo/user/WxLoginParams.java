package com.logicalthining.endeshop.common.requestVo.user;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信登陆参数
 *
 * @author Chen LiJia
 * @since 2019/12/17
 */
@Setter
@Getter
@ApiModel
public class WxLoginParams {

    @PropertyCheck(name = "openId")
    private String openId;

    private String nickName;

    private String sex;

    private String province;

    private String city;

    private String country;

    private String headImgUrl;

}
