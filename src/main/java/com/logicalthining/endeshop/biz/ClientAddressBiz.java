package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.clientAddress.AddParams;
import com.logicalthining.endeshop.common.requestVo.clientAddress.UpdateParams;
import com.logicalthining.endeshop.entity.ClientAddress;
import com.logicalthining.endeshop.entity.User;
import com.logicalthining.endeshop.service.ClientAddressServiceI;
import com.logicalthining.endeshop.service.UserServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户地址信息表
 *
 * @author chenLiJia
 * @since 2019-11-05 13:58:15
 **/
@Service
public class ClientAddressBiz {


    @Autowired
    private ClientAddressServiceI clientAddressService;//收货地址
    @Autowired
    private UserServiceI userService;//前端用户


    /**
     * 添加收货地址
     *
     * @param params 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 下午 12:47 2019/8/15 0015
     **/
    public Result add(AddParams params) {

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //检验参数是否为空
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //添加
        //如果当前地址是默认地址,那么需要先把其他的地址设为非默认地址
        if (Objects.equals(params.getIsCommonAddress(), BooleanConstant.YES_INTEGER)) {
            clientAddressService.updateClientCommonAddressByClient(optional.get().getId());
        }

        ClientAddress clientAddress = new ClientAddress();
        BeanUtils.copyProperties(params, clientAddress);

        clientAddress.setClientId(optional.get().getId());
        clientAddress.setUpdateTime(new Date());
        clientAddress.setIsDelete(BooleanConstant.NO_INTEGER);

        Result add = clientAddressService.add(clientAddress);
        return add;
    }


    /**
     * 编辑收货地址
     *
     * @param params 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 下午 12:47 2019/8/15 0015
     **/
    public Result update(UpdateParams params) {
        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //检验参数是否为空
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //编辑
        //如果当前地址是默认地址,那么需要先把其他的地址设为非默认地址
        if (Objects.equals(params.getIsCommonAddress(), BooleanConstant.YES_INTEGER)) {
            clientAddressService.updateClientCommonAddressByClient(optional.get().getId());
        }

        ClientAddress clientAddress = new ClientAddress();
        BeanUtils.copyProperties(params, clientAddress);

        clientAddress.setClientId(optional.get().getId());
        clientAddress.setUpdateTime(new Date());

        Result update = clientAddressService.update(clientAddress);
        return update;
    }

    /**
     * 删除收货地址
     *
     * @param id 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 下午 12:47 2019/8/15 0015
     **/
    public Result delete(Integer id) {

        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //判断是否存在
        ClientAddress condition = new ClientAddress().setId(id).setIsDelete(BooleanConstant.NO_INTEGER);
        List<ClientAddress> clientAddresses = clientAddressService.listByCondition(condition);
        if (Lists.isEmpty(clientAddresses)) {
            return Result.failure("数据不存在");
        }

        ClientAddress clientAddress = clientAddresses.get(0);
        clientAddress.setIsDelete(BooleanConstant.YES_INTEGER);

        Result update = clientAddressService.update(clientAddress);
        return update;
    }


    /**
     * 查询当前用户收货地址
     *
     * @param commonAddress 是否默认地址 0否 1是
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 下午 12:48 2019/8/15 0015
     **/
    public Result listByClient(Integer commonAddress) {

        //判断参数是否合法
        if (Objects.nonNull(commonAddress) && !Lists.asList(0, 1).contains(commonAddress)) {
            return Result.failure("参数不合法");
        }

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        ClientAddress condition = new ClientAddress().
                setClientId(optional.get().getId()).
                setIsDelete(BooleanConstant.NO_INTEGER);

        if (Objects.nonNull(commonAddress)) {
            condition.setIsCommonAddress(commonAddress);
        }
        List<ClientAddress> list = clientAddressService.listByCondition(condition);
        return Result.success("查询成功", list);
    }

    /**
     * 主键查询地址
     *
     * @param id 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @since 下午 3:45 2019/10/9 0009
     **/
    public Result findById(Integer id) {

        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        ClientAddress condition = new ClientAddress().setId(id);
        List<ClientAddress> clientAddresses = clientAddressService.listByCondition(condition);
        return Result.success("查询成功", Lists.isNotEmpty(clientAddresses) ? clientAddresses.get(0) : null);
    }


    /**
     * 修改收货地址是否默认地址
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:07 2019/11/19 0019
     **/
    public Result editAddressCommonStatus(UpdateStateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //判断收货地址是否存在
        ClientAddress clientAddress = clientAddressService.findById(params.getId());
        if (Objects.isNull(clientAddress) || Objects.equals(BooleanConstant.YES_INTEGER, clientAddress.getIsDelete())) {
            return Result.failure("收货地址不存在");
        }

        //判断是否是当前用户的地址
        if (!Objects.equals(clientAddress.getClientId(), optional.get().getId())) {
            return Result.failure("该地址不属于当前用户");
        }

        //进行修改
        if (Objects.equals(BooleanConstant.YES_INTEGER, params.getState())) {
            //如果是修改为默认地址
            //先把其他地址都修改为非默认地址
            clientAddressService.updateClientCommonAddressByClient(optional.get().getId());
        }
        clientAddress.setIsCommonAddress(params.getState());

        return clientAddressService.update(clientAddress);
    }

}
