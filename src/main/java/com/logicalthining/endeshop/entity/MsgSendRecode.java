package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import javax.persistence.*;

/**
 * 信息发放记录
 * @author chenLiJia
 * @since 2019-11-05 10:40:06
 * @version 1.0
 **/
@ApiModel("信息发放记录")
@Table(name = "s_msg_send_recode")
@Setter
@Getter
@Accessors(chain = true)
public class MsgSendRecode {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 消息key
     */
    @ApiModelProperty("消息key")
    @PropertyCheck(name = "消息key")
    @Column(name = "msg_key")
    private String msgKey;

    /**
     * 消息类型 1后台发放验证码 2前端登录验证码 3前端提现验证码
     */
    @ApiModelProperty("消息类型 1后台发放验证码 2前端登录验证码 3前端提现验证码")
    @PropertyCheck(name = "消息类型 1后台发放验证码 2前端登录验证码 3前端提现验证码")
    @Column(name = "msg_type")
    private Integer msgType;

    /**
     * 消息值
     */
    @ApiModelProperty("消息值")
    @PropertyCheck(name = "消息值")
    @Column(name = "msk_value")
    private String mskValue;

    /**
     * 消息发送时间
     */
    @ApiModelProperty("消息发送时间")
    @PropertyCheck(name = "消息发送时间")
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 消息过期时间
     */
    @ApiModelProperty("消息过期时间")
    @PropertyCheck(name = "消息过期时间")
    @Column(name = "limit_time")
    private Date limitTime;


}
