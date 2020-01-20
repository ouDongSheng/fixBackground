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
 * 用户的上级,上上级,祖宗，全部关联起来,方便直接判断是
 * @author chenLiJia
 * @since 2019-11-11 17:02:21
 * @version 1.0
 **/
@ApiModel("用户的上级,上上级,祖宗，全部关联起来,方便直接判断是")
@Table(name = "s_user_ancestor")
@Setter
@Getter
@Accessors(chain = true)
public class UserAncestor {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 祖宗id,表示他的上级或者上级的上级
     */
    @ApiModelProperty("祖宗id,表示他的上级或者上级的上级")
    @PropertyCheck(name = "祖宗id,表示他的上级或者上级的上级")
    @Column(name = "ancester_id")
    private Integer ancesterId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

}
