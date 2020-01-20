package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.dao.AreaMapper;
import com.logicalthining.endeshop.dao.CityMapper;
import com.logicalthining.endeshop.dao.ProvinceMapper;
import com.logicalthining.endeshop.entity.Area;
import com.logicalthining.endeshop.entity.City;
import com.logicalthining.endeshop.entity.Province;
import com.logicalthining.endeshop.service.AddressServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 上午 9:37
 **/
@Service
public class AddressServiceImpl implements AddressServiceI {

    @Resource
    private ProvinceMapper provinceMapper;//省
    @Resource
    private CityMapper cityMapper;//市
    @Resource
    private AreaMapper areaMapper;//区

    /**
     * 条件查询省
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Province>
     * @since 上午 9:45 2019/11/1 0001
     **/
    @Override
    public List<Province> listByCondition(Province condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition,true);
        return provinceMapper.select(condition);
    }

    /**
     * 条件查询市
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.City>
     * @since 上午 9:47 2019/11/1 0001
     **/
    @Override
    public List<City> listByCondition(City condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition,true);
        return cityMapper.select(condition);
    }

    /**
     * 条件查询区
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Area>
     * @since 上午 9:47 2019/11/1 0001
     **/
    @Override
    public List<Area> listByCondition(Area condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition,true);
        return areaMapper.select(condition);
    }
}
