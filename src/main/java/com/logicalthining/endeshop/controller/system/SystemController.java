package com.logicalthining.endeshop.controller.system;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.RegConstant;
import com.github.chenlijia1111.utils.core.RandomUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.core.enums.CharSetType;
import com.github.chenlijia1111.utils.http.HttpClientUtils;
import com.github.chenlijia1111.utils.http.HttpUtils;
import com.github.chenlijia1111.utils.http.ResponseUtil;
import com.github.chenlijia1111.utils.image.ValidateImageUtil;
import com.logicalthining.endeshop.common.enums.SMSType;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.entity.MsgSendRecode;
import com.logicalthining.endeshop.service.MsgSendRecodeServiceI;
import com.logicalthining.endeshop.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyuncs.DefaultAcsClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Description 系统层级控制器
 * @since 下午 5:05 2018/11/10 0010
 **/
@Controller
@Api(tags = "系统接口")
public class SystemController {


    @Value("${upload.imgSavePath}")
    private String imgSavePath;

    @Value("${upload.fileSavePath}")
    private String fileSavePath;


    @Autowired
    private MsgSendRecodeServiceI msgSendRecodeService;//消息记录

    /**
     * 上传文件接口
     *
     * @param fileType   文件类型  img/file
     * @param request
     * @param file
     * @param isfileName 是否需要源文件名称
     * @return
     */
    @PostMapping(value = "system/upload")
    @ResponseBody
    @ApiOperation(value = "上传文件接口", notes = "返回文件下载地址,fileType: 文件类型  img/file;isfileName: 是否需要源文件名称")
    public Result uploadFile(@RequestParam(defaultValue = "img") String fileType, HttpServletRequest request, MultipartFile file,
                             @RequestParam(defaultValue = "false") boolean isfileName) {
        Result result = WebUtils.saveFile(request, file, fileType.equals("img") ? imgSavePath : fileSavePath, "system/downLoad", isfileName, fileType);
        return result;
    }


    /**
     * 上传文件接口
     * <p>
     * 前端代码如下
     * <input type="file" name="file" multiple="multiple" onchange="showPreview(this)">
     * function showPreview(source) {
     * var files = source.files;
     * console.log(files);
     * <p>
     * var formData = new FormData();
     * formData.append("fileType", "file");
     * formData.append("isfileName", "false");
     * for (var i = 0; i < files.length; i++) {
     * formData.append("files", files[i]);
     * }
     * $.ajax(
     * {
     * url: "http://127.0.0.1:8087/ende/system/upload/batch",
     * data: formData,
     * type: "POST",
     * processData: false,
     * contentType: false,
     * success: function (res) {
     * console.log(res);
     * }
     * }
     * );
     * }
     *
     * @param fileType   文件类型  img/file
     * @param request
     * @param files
     * @param isfileName 是否需要源文件名称
     * @return
     */
    @PostMapping(value = "system/upload/batch")
    @ResponseBody
    @ApiOperation(value = "批量上传文件接口", notes = "返回文件下载地址,fileType: 文件类型  img/file;isfileName: 是否需要源文件名称,files 文件数组")
    public Result batchUploadFile(@RequestParam(defaultValue = "img") String fileType, HttpServletRequest request,
                                  MultipartFile[] files,
                                  @RequestParam(defaultValue = "false") boolean isfileName) {

        if (null == files || files.length == 0) {
            return Result.failure("上传文件为空");
        }

        ArrayList<Object> resultPath = new ArrayList<>();
        for (MultipartFile file : files) {
            Result result = WebUtils.saveFile(request, file, fileType.equals("img") ? imgSavePath : fileSavePath, "system/downLoad", isfileName, fileType);
            if (!result.getSuccess()) {
                return result;
            }
            resultPath.add(result.getData());

        }
        return Result.success("上传成功", resultPath);
    }

    /**
     * @param fileType 文件类型 img,qr,file
     * @param filePath 文件地址
     * @param fileName 文件下载返回名称
     * @param isDelete 下载之后是否删除
     * @param response
     */
    @GetMapping(value = "system/downLoad")
    @ApiOperation(value = "下载文件接口")
    public void downLoadFile(String fileType, String filePath, String fileName, @RequestParam(defaultValue = "false") boolean isDelete,
                             HttpServletResponse response, HttpServletRequest request) {
        String pathSuffix = "";
        switch (fileType) {
            case "img":
                pathSuffix = imgSavePath;
                break;
            case "file":
                pathSuffix = fileSavePath;
                break;
        }
        //二维码保存路径为绝对路径，不需要前缀
        WebUtils.downloadFile(StringUtils.isNotEmpty(pathSuffix) ? (pathSuffix + "/" + filePath) : filePath, fileName, isDelete, response, request);
    }


    @GetMapping(value = "system/ip")
    @ResponseBody
    @ApiOperation(value = "获取ip")
    public Result getRequstIp(HttpServletRequest request) {
        String ipAddr = HttpUtils.getIpAddr(request);
        if (StringUtils.isNotEmpty(ipAddr)) {
            Result success = Result.success("获取成功");
            success.setData(ipAddr);
            return success;
        }
        return Result.failure("获取失败");
    }


    @GetMapping(value = "system/uuid")
    @ResponseBody
    @ApiOperation(value = "生成uuid")
    public Result createUUID() {
        String uuid = RandomUtil.createUUID();
        return Result.success("生成成功", uuid);
    }

    @PostMapping(value = "system/file/copy")
    @ResponseBody
    @ApiOperation(value = "文件复制")
    public Result copyFile(String filePath, HttpServletRequest request) {

        try {
            filePath = URLDecoder.decode(filePath, CharSetType.UTF8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //判断文件是否存在
        File file = new File(fileSavePath + "/" + filePath);
        if (!file.exists())
            return Result.failure("文件不存在");

        String uuid = RandomUtil.createUUID();
        int i = filePath.lastIndexOf(".");
        String suffix = "";
        if (i != -1) {
            suffix = filePath.substring(i);
        }

        File file1 = new File(fileSavePath + "/" + uuid + suffix);
        try {
            FileCopyUtils.copy(file, file1);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure("文件复制失败");
        }
        //http://192.168.1.134:8082/expertise/system/downLoad?filePath=82b838dc22a942afa7284778ee88e456.jpg&fileType=img
        String urlPrefix = HttpUtils.currentRequestUrlPrefix(request);
        urlPrefix = urlPrefix + "/system/downLoad?filePath=" + uuid + suffix + "&fileType=file";
        return Result.success("复制成功", urlPrefix);
    }

    /**
     * 生成验证码
     *
     * @param key      1
     * @param response 2
     * @return void
     * @since 上午 10:48 2019/10/30 0030
     **/
    @GetMapping(value = "system/verifyCode")
    @ApiOperation(value = "生成验证码")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "key", value = "验证码key,可调用生成uuid的时候生成,必传", paramType = "query")})
    public void verifyCode(String key, HttpServletResponse response) {
        if (StringUtils.isEmpty(key)) {
            ResponseUtil.printRest(Result.failure("验证码key为空"), response);
            return;
        }

        //生成验证码
        String validCode = ValidateImageUtil.createValidCode(4);
        //存取验证码到数据库中用于后面的校验
        Date currentTime = new Date();
        //查询这个key是否存在,存在则直接覆盖
        MsgSendRecode msgSendRecode = msgSendRecodeService.findByKeyAndType(key, 1);
        if (Objects.isNull(msgSendRecode)) {
            msgSendRecode = new MsgSendRecode().
                    setMsgKey(key).
                    setMsgType(1).
                    setMskValue(validCode).
                    setSendTime(currentTime).
                    setLimitTime(new Date(currentTime.getTime() + Constants.ADMIN_VERIFY_IMAGE_TIMEOUT));
            msgSendRecodeService.add(msgSendRecode);
        } else {
            msgSendRecode.
                    setMskValue(validCode).
                    setMsgType(1).
                    setSendTime(currentTime).
                    setLimitTime(new Date(currentTime.getTime() + Constants.ADMIN_VERIFY_IMAGE_TIMEOUT));
            msgSendRecodeService.update(msgSendRecode);
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            //返回图片流
            ValidateImageUtil.writeValidCode(validCode, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送登录短信验证码
     *
     * @param telephone 1
     * @return void
     * @since 上午 10:48 2019/10/30 0030
     **/
    @GetMapping(value = "system/msmCode/login")
    @ResponseBody
    @ApiOperation(value = "发送登录短信验证码")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "telephone", value = "手机号", paramType = "query")})
    public Result msmLoginCode(String telephone) {
        if (StringUtils.isEmpty(telephone)) {
            Result.failure("手机号为空");
        }

        //生成验证码
        String validCode = RandomUtil.createRandomCode(6);
        //发送验证码
//        Result result = sendMsg(SMSType.LOGIN, telephone, validCode);
        Result result = sendAliyunMsg(telephone, validCode);

        if (result.getSuccess()) {
            //存取验证码到数据库中用于后面的校验
            Date currentTime = new Date();
            //查询这个key是否存在,存在则直接覆盖
            MsgSendRecode msgSendRecode = msgSendRecodeService.findByKeyAndType(telephone, 2);
            if (Objects.isNull(msgSendRecode)) {
                msgSendRecode = new MsgSendRecode().
                        setMsgKey(telephone).
                        setMsgType(2).
                        setMskValue(validCode).
                        setSendTime(currentTime).
                        setLimitTime(new Date(currentTime.getTime() + Constants.APP_MSM_CODE_TIMEOUT));
                msgSendRecodeService.add(msgSendRecode);
            } else {
                msgSendRecode.
                        setMskValue(validCode).
                        setMsgType(2).
                        setSendTime(currentTime).
                        setLimitTime(new Date(currentTime.getTime() + Constants.APP_MSM_CODE_TIMEOUT));
                msgSendRecodeService.update(msgSendRecode);
            }
        }
        return result;
    }

    /**
     * 发送提现短信验证码
     *
     * @param telephone 1
     * @return void
     * @since 上午 10:48 2019/10/30 0030
     **/
    @GetMapping(value = "system/msmCode/withdraw")
    @ResponseBody
    @ApiOperation(value = "发送提现短信验证码")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "telephone", value = "手机号", paramType = "query")})
    public Result msmWithdrawCode(String telephone) {
        if (StringUtils.isEmpty(telephone)) {
            Result.failure("手机号为空");
        }

        //生成验证码
        String validCode = RandomUtil.createRandomCode(6);
        //发送验证码
        Result result = sendMsg(SMSType.LOGIN, telephone, validCode);
        if (result.getSuccess()) {
            //存取验证码到数据库中用于后面的校验
            Date currentTime = new Date();
            //查询这个key是否存在,存在则直接覆盖
            MsgSendRecode msgSendRecode = msgSendRecodeService.findByKeyAndType(telephone, 3);
            if (Objects.isNull(msgSendRecode)) {
                msgSendRecode = new MsgSendRecode().
                        setMsgKey(telephone).
                        setMsgType(3).
                        setMskValue(validCode).
                        setSendTime(currentTime).
                        setLimitTime(new Date(currentTime.getTime() + Constants.APP_MSM_CODE_TIMEOUT));
                msgSendRecodeService.add(msgSendRecode);
            } else {
                msgSendRecode.
                        setMskValue(validCode).
                        setMsgType(2).
                        setSendTime(currentTime).
                        setLimitTime(new Date(currentTime.getTime() + Constants.APP_MSM_CODE_TIMEOUT));
                msgSendRecodeService.update(msgSendRecode);
            }
        }
        return result;
    }


    /**
     * 发送短信验证码
     *
     * @param smsType   验证码类型
     * @param telephone 手机号
     * @param code      验证码
     * @since 上午 11:30 2019/11/14 0014
     **/
    private static Result sendMsg(SMSType smsType, String telephone, String code) {

        if (Objects.isNull(smsType)) {
            return Result.failure("验证码类型为空");
        }

        if (StringUtils.isEmpty(telephone) || !Pattern.matches(RegConstant.MOBILE_PHONE, telephone)) {
            return Result.failure("手机号不合法");
        }

        if (StringUtils.isEmpty(code)) {
            return Result.failure("验证码为空");
        }

        String template = smsType.getTemplate();
        template = template.replaceAll("#code#", code);

        Map map = HttpClientUtils.getInstance().
                putParams("apikey", Constants.SMS_APIKEY).
                putParams("mobile", telephone).
                putParams("text", template).doPost("https://sms.yunpian.com/v2/sms/single_send.json").toMap();

        Object code1 = map.get("code");
        if (Objects.nonNull(code1) && Objects.equals("0", code1.toString())) {
            return Result.success("发送成功");
        }

        return Result.failure(map.get("msg").toString());
    }

    /**
     * 阿里云发送短信验证码
     *
     * @param telephone 手机号
     * @param code      验证码
     **/
    private static Result sendAliyunMsg(String telephone, String code) {
        if (StringUtils.isEmpty(telephone) || !Pattern.matches(RegConstant.MOBILE_PHONE, telephone)) {
            return Result.failure("手机号不合法");
        }

        if (StringUtils.isEmpty(code)) {
            return Result.failure("验证码为空");
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FgJFHkK8FEjb4Ey4Ywg", "qZOUNM7CF844BoyAgvf0zGlaUgbRdD");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", telephone);
        request.putQueryParameter("SignName", "大图环保健康服务");
        request.putQueryParameter("TemplateCode", "SMS_187240845");
        request.putQueryParameter("TemplateParam", "{code:\"" + code + "\"}");
        try {
            //TODO 还要添加报错
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return Result.success("发送成功");
        } catch (ServerException e) {
            return Result.failure(e.toString());
        } catch (ClientException e) {
            return Result.failure(e.toString());
        }
    }
}
