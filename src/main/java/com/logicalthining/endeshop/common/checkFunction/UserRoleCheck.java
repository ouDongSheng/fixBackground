package com.logicalthining.endeshop.common.checkFunction;

import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.common.enums.UserRole;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 2:26
 **/
public class UserRoleCheck implements Predicate<Integer> {

    @Override
    public boolean test(Integer integer) {

        UserRole[] values = UserRole.values();
        //系统支持的用户角色集合
        Set<Integer> userRoleSet = Lists.asList(values).stream().map(e -> e.getRole()).collect(Collectors.toSet());
        return userRoleSet.contains(integer);
    }
}
