package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 系统配置数据
 * @author chenLiJia
 * @since 2019-11-06 10:30:41
 * @version 1.0
 **/
@ApiModel("系统配置数据")
@Table(name = "s_system_config")
@Setter
@Getter
@Accessors(chain = true)
public class SystemConfig {
    /**
     * 配置key
     */
    @ApiModelProperty("配置key")
    @PropertyCheck(name = "配置key")
    @Id
    @Column(name = "config_key")
    private String configKey;

    /**
     * 配置值
     */
    @ApiModelProperty("配置值")
    @PropertyCheck(name = "配置值")
    @Column(name = "config_value")
    private String configValue;


}
