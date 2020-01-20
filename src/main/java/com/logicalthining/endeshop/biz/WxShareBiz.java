package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.IOUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.RandomUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.http.ResponseUtil;
import com.github.chenlijia1111.utils.image.QRCodeUtil;
import com.logicalthining.endeshop.common.requestVo.wx.WxJsApiSignParams;
import com.logicalthining.endeshop.common.responseVo.wx.WxJsApiSignVo;
import com.logicalthining.endeshop.service.UserServiceI;
import com.logicalthining.endeshop.service.WxShareServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 微信分享
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/20 0020 下午 3:19
 **/
@Service
public class WxShareBiz {

    @Autowired
    private WxShareServiceI wxShareService;
    @Autowired
    private UserServiceI userService;//用户


    //分享页面前端路径的前缀
    @Value("${share.urlSuffix}")
    private String shareUrlSuffix;


    /**
     * 微信公众号 接口凭证加密
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:10 2019/11/20 0020
     **/
    public Result jsApiTicketSign(WxJsApiSignParams params) {

        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        WxJsApiSignVo wxJsApiSignVo = wxShareService.createWxJsApiSignVo(params);
        return Result.success("操作成功", wxJsApiSignVo);
    }


    /**
     * 获取分享图片
     *
     * @param currentUserId 当前用户id
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:21 2019/11/25 0025
     **/
    public void shareImage(Integer currentUserId, HttpServletResponse response) {

        if (Objects.isNull(currentUserId)) {
            ResponseUtil.printRest(Result.failure("当前用户id为空"), response);
            return;
        }

        //背景图输入流
        InputStream inputStream = IOUtil.inputStreamToBaseProject("static/images/logo.png");
        //背景图
        int width = 562;
        int height = 700;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        //白色背景
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, width, height);//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g.drawOval(0, 0, width, height);

        //临时文件
        File tempFile = null;
        //开始合并
        try {
            //logo
            BufferedImage logoImage = ImageIO.read(inputStream);
            g.drawImage(logoImage, 0, 0, logoImage.getWidth(), logoImage.getHeight(), null);

            g.setFont(new Font("宋体", Font.BOLD, 25));
            Color color = new Color(25, 155, 255);
            g.setColor(color);
            g.drawString("恩德商城", 15, logoImage.getHeight() + 50);

            color = new Color(102, 102, 102);
            g.setColor(color);
            g.drawString("品质保证，好物与你分享！", 15, logoImage.getHeight() + 100);

            //二维码
            tempFile = File.createTempFile(RandomUtil.createUUID(), ".png");
            //http://192.168.1.134:9093/?shareUserId=12#/home
            if (StringUtils.isEmpty(shareUrlSuffix)) {
                ResponseUtil.printRest(Result.failure("分享页面前缀未设置"), response);
                return;
            }
            String shareUrl = shareUrlSuffix + "/?shareUserId=" + currentUserId + "#/home";
            new QRCodeUtil().output(shareUrl, tempFile);

            //合并二维码
            BufferedImage qrCodeImage = ImageIO.read(tempFile);
            g.drawImage(qrCodeImage, 400, logoImage.getHeight() + 30, 125, 125, null);

            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(tempFile)) {
                tempFile.deleteOnExit();
            }
        }
    }


    /**
     * 获取分享图片
     *
     * @param currentUserId 当前用户id
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:21 2019/11/25 0025
     **/
    public void shareImageV2(Integer currentUserId, HttpServletResponse response) {

        if (Objects.isNull(currentUserId)) {
            ResponseUtil.printRest(Result.failure("当前用户id为空"), response);
            return;
        }

        //二维码
        //http://192.168.1.134:9093/?shareUserId=12#/home
        if (StringUtils.isEmpty(shareUrlSuffix)) {
            ResponseUtil.printRest(Result.failure("分享页面前缀未设置"), response);
            return;
        }
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String shareUrl = shareUrlSuffix + "/?shareUserId=" + currentUserId + "#/home";
            new QRCodeUtil(500,500).output(shareUrl, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
