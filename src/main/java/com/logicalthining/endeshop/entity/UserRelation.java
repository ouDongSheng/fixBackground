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
import java.util.Date;

/**
 * 用户上下级关系表
 * <p>
 * 在代码层面,用户的关系由这个表来维护
 * 但是在逻辑层面，一旦用户的下级升级为合伙人之后，他们之间的关系就断了
 * 即使上级是合伙人，他们之间的关系也还是断了，只不过在表里维护了他们以前的上下级关系，
 * 这时候只是用来维护业绩的，所以在查询上级合伙人的时候，只需要递归往上找，找到第一个就可以了
 * <p>
 * 所以逻辑上来说，一个合伙人是不会有上级的
 * <p>
 * 对于间接奖：普通用户升级为会员，其上级合伙人获得间接奖。此奖励为一次性奖励。此为积分显示
 * 往上找,找到第一个合伙人
 * <p>
 * 对于复购奖：合伙人的下属会员复购产品，合伙人获得复购奖。此为积分显示
 * 往上找，找到第一个合伙人
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-06 10:43:06
 **/
@ApiModel("用户上下级关系表")
@Table(name = "s_user_relation")
@Setter
@Getter
@Accessors(chain = true)
public class UserRelation {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 上级用户id
     */
    @ApiModelProperty("上级用户id")
    @PropertyCheck(name = "上级用户id")
    @Column(name = "parent_user_id")
    private Integer parentUserId;

    /**
     * 绑定时间
     */
    @ApiModelProperty("绑定时间")
    @PropertyCheck(name = "绑定时间")
    @Column(name = "create_time")
    private Date createTime;


}
