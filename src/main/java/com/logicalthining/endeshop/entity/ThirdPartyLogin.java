package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;

/**
 * 用户第三方登录信息
 * @author chenLiJia
 * @since 2019-11-12 17:00:02
 * @version 1.0
 **/
@ApiModel("用户第三方登录信息")
@Table(name = "s_third_party_login")
public class ThirdPartyLogin {
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
     * 第三方类型 1微信
     */
    @ApiModelProperty("第三方类型 1微信")
    @PropertyCheck(name = "第三方类型 1微信")
    @Column(name = "third_type")
    private Integer thirdType;

    /**
     * 第三方唯一id
     */
    @ApiModelProperty("第三方唯一id")
    @PropertyCheck(name = "第三方唯一id")
    @Column(name = "open_id")
    private String openId;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    @PropertyCheck(name = "用户昵称")
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 1男 2女 0未知
     */
    @ApiModelProperty("1男 2女 0未知")
    @PropertyCheck(name = "1男 2女 0未知")
    @Column(name = "sex")
    private Integer sex;

    /**
     * 省
     */
    @ApiModelProperty("省")
    @PropertyCheck(name = "省")
    @Column(name = "province")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    @PropertyCheck(name = "市")
    @Column(name = "city")
    private String city;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @PropertyCheck(name = "头像")
    @Column(name = "head_image")
    private String headImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
