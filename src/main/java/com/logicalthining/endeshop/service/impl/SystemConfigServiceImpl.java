package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.dao.SystemConfigMapper;
import com.logicalthining.endeshop.entity.SystemConfig;
import com.logicalthining.endeshop.service.SystemConfigServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 系统配置数据
 *
 * @author chenLiJia
 * @since 2019-11-06 10:30:53
 **/
@Service
public class SystemConfigServiceImpl implements SystemConfigServiceI {


    @Resource
    private SystemConfigMapper systemConfigMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:30:53
     **/
    public Result add(SystemConfig params) {
        //先判断是否存在
        SystemConfig systemConfig = systemConfigMapper.selectByPrimaryKey(params.getConfigKey());
        if (Objects.nonNull(systemConfig)) {
            return update(params);
        }
        int i = systemConfigMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:30:53
     **/
    public Result update(SystemConfig params) {

        int i = systemConfigMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查找系统配置信息
     *
     * @param configKey 1
     * @return com.logicalthining.endeshop.entity.SystemConfig
     * @since 上午 10:33 2019/11/6 0006
     **/
    @Override
    public SystemConfig selectByConfigKey(String configKey) {
        if (StringUtils.isNotEmpty(configKey)) {
            SystemConfig systemConfig = systemConfigMapper.selectByPrimaryKey(configKey);
            if (Objects.isNull(systemConfig)) {
                //初始化信息
                String initValue = Constants.SYSTEM_CONFIG_INIT_MAP.get(configKey);
                if (StringUtils.isNotEmpty(initValue)) {
                    systemConfig = new SystemConfig().setConfigKey(configKey).setConfigValue(initValue);
                    add(systemConfig);
                }
            }
            return systemConfig;
        }
        return null;
    }


}
