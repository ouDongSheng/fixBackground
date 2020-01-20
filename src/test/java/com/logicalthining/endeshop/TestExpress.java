package com.logicalthining.endeshop;

import com.github.chenlijia1111.utils.common.constant.RegConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.enums.CharSetType;
import com.github.chenlijia1111.utils.encrypt.MD5EncryptUtil;
import com.github.chenlijia1111.utils.http.HttpClientUtils;
import com.github.chenlijia1111.utils.list.Maps;
import com.github.chenlijia1111.utils.list.annos.MapType;
import com.logicalthining.endeshop.common.pojo.Constants;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 物流测试
 *
 * @since 下午 4:50 2019/11/14 0014
 **/
public class TestExpress {

    @Test
    public void test1() {

        Map build = Maps.mapBuilder(MapType.HASH_MAP).put("ShipperCode", "YTO").put("LogisticCode", "YT4210533714838").build();
        String s = JSONUtil.objToStr(build);
        try {
            HttpClientUtils params = HttpClientUtils.getInstance();

            params.putParams("RequestData", URLEncoder.encode(s, CharSetType.UTF8.name()));
            params.putParams("EBusinessID", Constants.EXPRESS_USERID);
            params.putParams("RequestType", "1002");

            String dataSign = Base64.getEncoder().encodeToString(MD5EncryptUtil.MD5StringToHexString((s + Constants.EXPRESS_APIKEY)).toLowerCase().getBytes(CharSetType.UTF8.getType()));
            params.putParams("DataSign", URLEncoder.encode(dataSign, CharSetType.UTF8.name()));
            params.putParams("DataType", "2");

            Map map = params.doPost("http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx").toMap();

            System.out.println(map);
            System.out.println(map.get("Success").toString().equals("true"));


            Object traces = map.get("Traces");
            if (Objects.nonNull(traces)) {
                List<Map> tracesList = (List) traces;
                for (Map traceMap : tracesList) {
                    String acceptStation = (String) traceMap.get("AcceptStation");
                    System.out.println(acceptStation);
                    if (Pattern.matches("^[\\s\\S]+1[3-9]\\d{9}[\\s\\S]+$", acceptStation)) {
                        System.out.println("命中");
                        int index = acceptStation.indexOf("1");
                        while (index != -1 && acceptStation.length() > index + 11) {
                            String substring = acceptStation.substring(index, index + 11);
                            if(Pattern.matches(RegConstant.MOBILE_PHONE,substring)){
                                //命中
                                return;
                            }
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void test2() {
        String s = "哈师大手机号的";
        System.out.println(Pattern.matches("^[\\s\\S]+$", s));
    }

}
