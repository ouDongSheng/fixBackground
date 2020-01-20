package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * 用户积分
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-12 09:30:05
 **/
@ApiModel("用户积分")
@Table(name = "s_user_score")
@Setter
@Getter
public class UserScore {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户积分
     */
    @ApiModelProperty("用户积分")
    @PropertyCheck(name = "用户积分")
    @Column(name = "score")
    private Double score;

    /**
     * 累计获得积分
     */
    @ApiModelProperty("累计获得积分")
    @PropertyCheck(name = "累计获得积分")
    @Column(name = "grand_total_score")
    private Double grandTotalScore;

    /**
     * 变更时间
     */
    @ApiModelProperty("变更时间")
    @PropertyCheck(name = "变更时间")
    @Column(name = "update_time")
    private Date updateTime;

    public UserScore setScore(Double score) {
        if (Objects.isNull(score) || score < 0) {
            score = 0.0;
        }
        score = NumberUtil.doubleToFixLengthDouble(score, 2);
        this.score = score;
        return this;
    }

    public UserScore setGrandTotalScore(Double grandTotalScore) {
        if (Objects.isNull(grandTotalScore) || grandTotalScore < 0) {
            grandTotalScore = 0.0;
        }
        grandTotalScore = NumberUtil.doubleToFixLengthDouble(grandTotalScore, 2);
        this.grandTotalScore = grandTotalScore;
        return this;
    }
}
