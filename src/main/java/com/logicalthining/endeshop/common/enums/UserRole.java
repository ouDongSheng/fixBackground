package com.logicalthining.endeshop.common.enums;

import java.util.Objects;

/**
 * 用户角色
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 2:13
 **/
public enum UserRole {

    COMMON_USER(1, "普通用户"),
    VIP_USER(2, "会员"),
    PARTNER_USER(3, "合伙人"),
    ;

    UserRole(Integer role, String roleName) {
        this.role = role;
        this.roleName = roleName;
    }

    private Integer role;

    private String roleName;

    public Integer getRole() {
        return role;
    }

    /**
     * 转换用户角色为中文
     *
     * @param role 1
     * @return java.lang.String
     * @since 下午 4:30 2019/11/7 0007
     **/
    public static String transferRoleName(Integer role) {
        if (Objects.nonNull(role)) {
            UserRole[] values = UserRole.values();
            for (UserRole value : values) {
                if (Objects.equals(value.getRole(), role)) {
                    return value.roleName;
                }
            }
        }
        return null;
    }

}
