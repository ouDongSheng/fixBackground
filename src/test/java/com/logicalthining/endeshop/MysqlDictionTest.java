package com.logicalthining.endeshop;

import com.github.chenlijia1111.utils.database.MysqlDataDictonaryUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

/**
 * 导出数据字典
 */
public class MysqlDictionTest {


    @Test
    public void test1(String pa) {
        File file = new File("D:\\公司资料\\恩德生态\\交付\\数据字典.docx");
        try {
            new MysqlDataDictonaryUtil().writeToWord("jdbc:mysql://58.250.17.31:3345/ende?serverTimezone=Asia/Shanghai&useSSL=false&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL",
                    "root", "1015tqljsw", "ende", file);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
