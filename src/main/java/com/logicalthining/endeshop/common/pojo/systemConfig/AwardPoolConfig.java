package com.logicalthining.endeshop.common.pojo.systemConfig;

import com.github.chenlijia1111.utils.list.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 奖金池配置信息
 * 如果起始范围或者结束范围为空则表示没有边界,但是不能两个都空
 * 且添加的时候需要判断有没有范围覆盖了
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/8 0008 上午 11:33
 **/
@ApiModel
@Setter
@Getter
@Accessors(chain = true)
public class AwardPoolConfig {

    public AwardPoolConfig(Double startRange, Double endRange, Double ratio) {
        this.startRange = startRange;
        this.endRange = endRange;
        this.ratio = ratio;
    }

    public AwardPoolConfig() {
    }

    /**
     * 金额区间起始范围
     * >= 起始值
     *
     * @since 上午 11:34 2019/11/8 0008
     **/
    @ApiModelProperty(value = "金额区间起始范围")
    private Double startRange;

    /**
     * 金额区间结束范围
     * < 结束值
     *
     * @since 上午 11:34 2019/11/8 0008
     **/
    @ApiModelProperty(value = "金额区间结束范围")
    private Double endRange;

    /**
     * 积分百分比
     *
     * @since 上午 11:35 2019/11/8 0008
     **/
    @ApiModelProperty(value = "积分百分比")
    private Double ratio;

    /**
     * 根据金额查询相对应的积分比率
     *
     * @param d    金额
     * @param list 配置信息
     * @return java.lang.Double
     * @since 上午 10:58 2019/11/12 0012
     **/
    public static Double findRatio(Double d, List<AwardPoolConfig> list) {
        if (Objects.nonNull(d) && Lists.isNotEmpty(list)) {

            //大的在前
            list = list.stream().sorted(Comparator.comparing(AwardPoolConfig::getStartRange).reversed()).collect(Collectors.toList());

            Optional<AwardPoolConfig> any = list.stream().filter(e -> d >= e.getStartRange() &&
                    (Objects.isNull(e.getEndRange()) || d < e.getEndRange())).findAny();
            if (any.isPresent()) {
                AwardPoolConfig awardPoolConfig = any.get();
                return awardPoolConfig.getRatio();
            }
        }
        return null;
    }
}
