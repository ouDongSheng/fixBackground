package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统数据统计
 * 减少在表里直接统计的消耗
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-12 14:49:19
 **/
@ApiModel("系统数据统计")
@Table(name = "s_system_count")
@Setter
@Getter
public class SystemCount {
    /**
     * 统计的key
     */
    @ApiModelProperty("统计的key")
    @PropertyCheck(name = "统计的key")
    @Id
    @Column(name = "count_key")
    private String countKey;

    /**
     * 统计的值
     */
    @ApiModelProperty("统计的值")
    @PropertyCheck(name = "统计的值")
    @Column(name = "count_value")
    private Double countValue;


    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @PropertyCheck(name = "更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
