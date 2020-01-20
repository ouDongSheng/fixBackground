package com.logicalthining.endeshop;

import com.github.chenlijia1111.utils.code.mybatis.CommonMapperCommentGenerator;
import com.github.chenlijia1111.utils.code.mybatis.MybatisCodeGeneratorUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * 代码生成
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 9:46
 **/
public class CodeGenerateTest {

    private static MybatisCodeGeneratorUtil mybatisCodeGeneratorUtil = MybatisCodeGeneratorUtil.getInstance();

    static {
        mybatisCodeGeneratorUtil.setCommentGeneratorType(CommonMapperCommentGenerator.class.getName())
                .setConnectionUrl("jdbc:mysql://58.250.17.31:3345/ende?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8").
                setDriverClass("com.mysql.jdbc.Driver").
                setUserId("root").setPassword("1015tqljsw")
                .setTargetProjectPath("D:\\ssmProject\\waibao\\endeshop\\src\\main\\java").
                setTargetDAOPackage("com.logicalthining.endeshop.dao").setTargetEntityPackage("com.logicalthining.endeshop.entity").
                setTargetXMLPackage("com.logicalthining.endeshop.mapper").
                setTargetControllerPackage("com.logicalthining.endeshop.controller.admin").setTargetBizPackage("com.logicalthining.endeshop.biz").
                setTargetServicePackage("com.logicalthining.endeshop.service")
                .setAuthor("chenLiJia");

        mybatisCodeGeneratorUtil.setExampleCode(false);
        mybatisCodeGeneratorUtil.setCommonCode(false);

        Map<String, String> tableToDomain = mybatisCodeGeneratorUtil.getTableToDoMain();
        tableToDomain.put("s_user_performance_history", "UserPerformanceHistory");

        List<String> ignoreDoMainToBusiness = mybatisCodeGeneratorUtil.getIgnoreDoMainToBusiness();
    }


    //生成entity,mapper,dao
    @Test
    public void test1WithChen() {
        mybatisCodeGeneratorUtil.generateCode();
    }

    //生成controller,biz,service
    @Test
    public void test2WithChen() {
        mybatisCodeGeneratorUtil.setTargetControllerPackage(null).setTargetBizPackage(null);
        mybatisCodeGeneratorUtil.generateWithBusinssCode();
    }

}
