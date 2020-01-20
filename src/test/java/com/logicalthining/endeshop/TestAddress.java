package com.logicalthining.endeshop;

import com.logicalthining.endeshop.dao.AreaMapper;
import com.logicalthining.endeshop.dao.CityMapper;
import com.logicalthining.endeshop.dao.ProvinceMapper;
import com.logicalthining.endeshop.entity.Area;
import com.logicalthining.endeshop.entity.City;
import com.logicalthining.endeshop.entity.Province;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 上午 10:24
 **/
@SpringBootTest
@Rollback(value = false)
public class TestAddress {

    @Resource
    private ProvinceMapper provinceMapper;
    @Resource
    private CityMapper cityMapper;
    @Resource
    private AreaMapper areaMapper;

    @Test
    public void test() {
        List<Province> provinces = provinceMapper.selectAll();
        for (Province province : provinces) {
            if (province.getCode().startsWith("\uFEFF")) {
                System.out.println("发现" + province.getName());
                province.setCode(province.getCode().replaceAll("\uFEFF", ""));
                provinceMapper.updateByPrimaryKey(province);
            }
        }
        List<City> cities = cityMapper.selectAll();
        for (City city : cities) {
            if (city.getCode().startsWith("\uFEFF")) {
                System.out.println("发现" + city.getName() + city.getCode());
                String s = city.getCode().replaceAll("\uFEFF", "");
                city.setCode(s);
                cityMapper.updateByPrimaryKey(city);
            }
        }
        List<Area> areas = areaMapper.selectAll();
        for (Area area : areas) {
            if (area.getCode().startsWith("\uFEFF")) {
                System.out.println("发现" + area.getName() + area.getCode());
                area.setCode(area.getCode().replaceAll("\uFEFF", ""));
                areaMapper.updateByPrimaryKey(area);
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(" 11m".contains(" "));
        System.out.println(" 11m".replaceAll(" ", ""));
    }
}
