package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.pojo.IDGenerateFactory;
import com.logicalthining.endeshop.common.requestVo.product.*;
import com.logicalthining.endeshop.common.responseVo.count.ProductSaleVo;
import com.logicalthining.endeshop.common.responseVo.product.*;
import com.logicalthining.endeshop.entity.*;
import com.logicalthining.endeshop.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品表
 *
 * @since 2019-11-01 13:46:54
 **/
@Service
public class ProductBiz {

    @Autowired
    private ProductServiceI productService;//产品
    @Autowired
    private GoodsServiceI goodsService;//商品
    @Autowired
    private ProductSpecServiceI productSpecService;//产品规格名称
    @Autowired
    private ProductSpecValueServiceI productSpecValueService;//产品规格值
    @Autowired
    private GoodSpecServiceI goodSpecService;//商品规格


    /**
     * 添加商品
     *
     * @param params 1
     * @since 下午 2:18 2019/11/1 0001
     **/
    @Transactional
    public Result add(ProductAddParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkPropertyWithIgnore(params, Lists.asList("content"));
        if (!result.getSuccess()) {
            return result;
        }

        //当前时间
        Date currentTime = new Date();

        //产品id
        String productId = IDGenerateFactory.createProductId();

        Product product = new Product().setId(productId).
                setName(params.getName()).
                setSmallPic(params.getSmallPic()).
                setContent(params.getContent()).
                setShelfStatus(params.getShelfStatus()).
                setCreateTime(currentTime).
                setUpdateTime(currentTime).
                setMasterCategory(params.getMasterCategory()).
                setProductType(params.getProductType()).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //添加产品
        productService.add(product);

        //所有产品规格记录,用于后面添加商品关联id
        List<ProductSpec> allProductSpecRecode = Lists.newArrayList();
        List<ProductSpecValue> allProductSpecValueRecode = Lists.newArrayList();

        //添加产品规格
        List<ProductSpecParams> productSpecParamsList = params.getProductSpecParamsList();
        for (ProductSpecParams specParams : productSpecParamsList) {
            ProductSpec productSpec = new ProductSpec().setProductId(productId).setSpecName(specParams.getSpecName());
            productSpecService.add(productSpec);
            allProductSpecRecode.add(productSpec);

            //添加产品规格值
            List<ProductSpecValueParams> specValueList = specParams.getSpecValueList();
            List<ProductSpecValue> productSpecValueList = specValueList.stream().map(e -> new ProductSpecValue().
                    setProductSpecId(productSpec.getId()).
                    setSpecValueImage(e.getImageValue()).
                    setSpecValue(e.getValue())).collect(Collectors.toList());
            productSpecValueService.batchAdd(productSpecValueList);
            allProductSpecValueRecode.addAll(productSpecValueList);
        }

        //添加商品
        List<GoodAddParams> goodList = params.getGoodList();
        for (GoodAddParams goodAddParams : goodList) {
            //商品id
            String goodId = IDGenerateFactory.createGoodId();
            Goods goods = new Goods().setId(goodId).
                    setProductId(productId).
                    setPrice(goodAddParams.getPrice()).
                    setVipPrice(goodAddParams.getVipPrice()).
                    setMarketPrice(goodAddParams.getMarketPrice()).
                    setGoodNo(goodAddParams.getGoodNo()).
                    setCreateTime(currentTime).
                    setUpdateTime(currentTime).
                    setDeleteStatus(BooleanConstant.NO_INTEGER);

            goodsService.add(goods);

            //添加这个商品的规格属性
            List<GoodSpecParams> goodSpecParamsList = goodAddParams.getGoodSpecParamsList();
            for (GoodSpecParams goodSpecParams : goodSpecParamsList) {
                //查询这个规格所对应的的前面的产品规格值的id  比较名字相同的就是
                Optional<ProductSpec> any = allProductSpecRecode.stream().filter(e -> Objects.equals(e.getSpecName(), goodSpecParams.getSpecName())).findAny();
                if (any.isPresent()) {
                    ProductSpec productSpec = any.get();
                    Optional<ProductSpecValue> any1 = allProductSpecValueRecode.stream().filter(e -> Objects.equals(e.getProductSpecId(), productSpec.getId()) &&
                            Objects.equals(e.getSpecValueImage(), goodSpecParams.getSpecImageValue()) &&
                            Objects.equals(e.getSpecValue(), goodSpecParams.getSpecValue())).findAny();
                    if (any1.isPresent()) {
                        ProductSpecValue productSpecValue = any1.get();
                        GoodSpec goodSpec = new GoodSpec().setGoodId(goodId).setSpecValueId(productSpecValue.getId());
                        goodSpecService.add(goodSpec);
                    }
                }
            }
        }

        return Result.success("操作成功");
    }

    /**
     * 修改商品
     * 需要判断是不是把某个商品规格删除了
     * 直接通过规格名字去比对
     * <p>
     * 规格可以先删除再加，
     * 但是商品因为是关联到订单的，
     * 所以不可以贸贸然的随便删，
     * 需要对比确实这个商品不存在了再删,
     * 而且是逻辑删除
     * <p>
     * 通过规格确定一个唯一的sku
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:18 2019/11/1 0001
     **/
    @Transactional
    public Result update(ProductUpdateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkPropertyWithIgnore(params, Lists.asList("content"));
        if (!result.getSuccess()) {
            return result;
        }

        //判断商品是否存在
        Product productCondition = new Product().setId(params.getId()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Product> products = productService.listByCondition(productCondition);
        if (Lists.isEmpty(products)) {
            return Result.failure("数据不存在");
        }

        //当前时间
        Date currentTime = new Date();

        //产品id
        Product product = new Product().setId(params.getId()).
                setName(params.getName()).
                setSmallPic(params.getSmallPic()).
                setContent(params.getContent()).
                setShelfStatus(params.getShelfStatus()).
                setUpdateTime(currentTime).
                setDeleteStatus(BooleanConstant.NO_INTEGER);

        //添加产品
        productService.update(product);

        //查询出这个产品原先的所有商品
        Goods goodCondition = new Goods().setProductId(params.getId()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<GoodVo> listByCondition = goodsService.listByCondition(goodCondition);

        //这是前端传递过来修改的商品id集合,那么没传过来的就是要删除的了
        List<String> updatedGoodIdList = Lists.newArrayList();

        //先删除这个产品所有的规格记录，这个删除必须要在查询出这个产品原先的所有商品之后，不然就查不到规格了
        productSpecService.deleteByProductId(params.getId());
        //删除所有商品的规格值,后面直接重新添加这些商品的规格值
        goodSpecService.deleteByProductId(params.getId());

        //所有产品规格记录,用于后面添加商品关联id
        List<ProductSpec> allProductSpecRecode = Lists.newArrayList();
        List<ProductSpecValue> allProductSpecValueRecode = Lists.newArrayList();

        //添加产品规格
        List<ProductSpecParams> productSpecParamsList = params.getProductSpecParamsList();
        for (ProductSpecParams specParams : productSpecParamsList) {
            ProductSpec productSpec = new ProductSpec().setProductId(params.getId()).setSpecName(specParams.getSpecName());
            productSpecService.add(productSpec);
            allProductSpecRecode.add(productSpec);

            //添加产品规格值
            List<ProductSpecValueParams> specValueList = specParams.getSpecValueList();
            List<ProductSpecValue> productSpecValueList = specValueList.stream().map(e -> new ProductSpecValue().
                    setProductSpecId(productSpec.getId()).
                    setSpecValueImage(e.getImageValue()).
                    setSpecValue(e.getValue())).collect(Collectors.toList());
            productSpecValueService.batchAdd(productSpecValueList);
            allProductSpecValueRecode.addAll(productSpecValueList);
        }

        //添加商品
        //添加参数里的商品集合
        List<GoodAddParams> goodList = params.getGoodList();
        for (GoodAddParams goodAddParams : goodList) {

            //判断这个商品是不是存在的,如果是存在的,就做修改操作,否则,就做新增操作
            List<GoodSpecParams> goodSpecParamsList1 = goodAddParams.getGoodSpecParamsList();
            List<GoodSpecCompareVo> collect = goodSpecParamsList1.stream().map(e -> e.toGoodSpecCompareVo()).
                    sorted(Comparator.comparing(e -> e.getSpecName())).collect(Collectors.toList());

            Optional<GoodVo> any2 = listByCondition.stream().filter(e -> {
                List<GoodSpecCompareVo> goodSpecCompareVos = e.toGoodSpecCompareVo();
                Collections.sort(goodSpecCompareVos, Comparator.comparing(e1 -> e1.getSpecName()));
                return Objects.equals(goodSpecCompareVos, collect);
            }).findAny();

            Goods goods = null;
            if (any2.isPresent()) {

                GoodVo goodVo = any2.get();
                //前端传过来的商品id
                updatedGoodIdList.add(goodVo.getId());
                //存在
                //做变更操作
                //商品id
                String goodId = goodVo.getId();
                goods = new Goods().setId(goodId).
                        setProductId(params.getId()).
                        setPrice(goodAddParams.getPrice()).
                        setVipPrice(goodAddParams.getVipPrice()).
                        setMarketPrice(goodAddParams.getMarketPrice()).
                        setGoodNo(goodAddParams.getGoodNo()).
                        setUpdateTime(currentTime);

                goodsService.update(goods);
            } else {
                //新增
                //商品id
                String goodId = IDGenerateFactory.createGoodId();
                goods = new Goods().setId(goodId).
                        setProductId(params.getId()).
                        setPrice(goodAddParams.getPrice()).
                        setVipPrice(goodAddParams.getVipPrice()).
                        setMarketPrice(goodAddParams.getMarketPrice()).
                        setGoodNo(goodAddParams.getGoodNo()).
                        setCreateTime(currentTime).
                        setUpdateTime(currentTime).
                        setDeleteStatus(BooleanConstant.NO_INTEGER);

                goodsService.add(goods);
            }


            //添加这个商品的规格属性
            List<GoodSpecParams> goodSpecParamsList = goodAddParams.getGoodSpecParamsList();
            for (GoodSpecParams goodSpecParams : goodSpecParamsList) {
                //查询这个规格所对应的的前面的产品规格值的id  比较名字相同的就是
                Optional<ProductSpec> any = allProductSpecRecode.stream().filter(e -> Objects.equals(e.getSpecName(), goodSpecParams.getSpecName())).findAny();
                if (any.isPresent()) {
                    ProductSpec productSpec = any.get();
                    Optional<ProductSpecValue> any1 = allProductSpecValueRecode.stream().filter(e -> Objects.equals(e.getProductSpecId(), productSpec.getId()) &&
                            Objects.equals(e.getSpecValueImage(), goodSpecParams.getSpecImageValue()) &&
                            Objects.equals(e.getSpecValue(), goodSpecParams.getSpecValue())).findAny();
                    if (any1.isPresent()) {
                        ProductSpecValue productSpecValue = any1.get();
                        GoodSpec goodSpec = new GoodSpec().setGoodId(goods.getId()).setSpecValueId(productSpecValue.getId());
                        goodSpecService.add(goodSpec);
                    }
                }
            }
        }

        //判断那些前端删除了的商品,删掉
        List<GoodVo> deletedGoodList = listByCondition.stream().filter(e -> !updatedGoodIdList.contains(e.getId())).collect(Collectors.toList());
        Set<String> deletedGoodIdSet = deletedGoodList.stream().map(e -> e.getId()).collect(Collectors.toSet());
        goodsService.batchDelete(deletedGoodIdSet);

        return Result.success("操作成功");
    }

    /**
     * 删除商品
     *
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:19 2019/11/1 0001
     **/
    @Transactional
    public Result delete(String productId) {

        if (StringUtils.isEmpty(productId)) {
            return Result.failure("产品id为空");
        }

        //判断数据是否存在
        Product productCondition = new Product().setId(productId).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Product> products = productService.listByCondition(productCondition);
        if (Lists.isEmpty(products)) {
            return Result.failure("数据不存在");
        }

        //删除产品
        Product deleteProduct = new Product().setId(productId).setDeleteStatus(BooleanConstant.YES_INTEGER);
        productService.update(deleteProduct);

        //删除商品
        //查询出这个产品原先的所有商品
        Goods goodCondition = new Goods().setProductId(productId).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<GoodVo> listByCondition = goodsService.listByCondition(goodCondition);
        Set<String> deleteGoodIdSet = listByCondition.stream().map(e -> e.getId()).collect(Collectors.toSet());
        goodsService.batchDelete(deleteGoodIdSet);

        return Result.success("操作成功");
    }

    /**
     * 列表查询产品
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:19 2019/11/1 0001
     **/
    public Result listPage(AdminProductQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AdminProductListVo> list = productService.adminListPage(params);
        PageInfo<AdminProductListVo> pageInfo = new PageInfo<>(list);
        //查询分享奖累计金额以及间接奖累计金额
        if(Lists.isNotEmpty(list)){
            //产品id集合
            Set<String> productIdSet = list.stream().map(e -> e.getId()).collect(Collectors.toSet());
            List<ProductAwardCountInfo> productAwardCountInfos = productService.listProductAwardCountInfo(productIdSet);
            //查询分享奖和间接奖的累计发放值
            if(Lists.isNotEmpty(productAwardCountInfos)){
                for (AdminProductListVo productListVo : list) {
                    Optional<ProductAwardCountInfo> any = productAwardCountInfos.stream().filter(e -> Objects.equals(e.getProductId(), productListVo.getId())).findAny();
                    if(any.isPresent()){
                        ProductAwardCountInfo productAwardCountInfo = any.get();
                        productListVo.setShareAward(productAwardCountInfo.getShareAward());
                        productListVo.setIndirectAward(productAwardCountInfo.getIndirectAward());
                    }
                }
            }
        }
        return Result.success("查询成功", pageInfo);
    }

    /**
     * 修改上架状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:20 2019/11/1 0001
     **/
    public Result updateShelfStatus(UpdateShelfStatusParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断数据是否存在
        Product productCondition = new Product().setId(params.getProductId()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Product> products = productService.listByCondition(productCondition);
        if (Lists.isEmpty(products)) {
            return Result.failure("数据不存在");
        }

        Product product = new Product().setId(params.getProductId()).setShelfStatus(params.getState());
        return productService.update(product);
    }


    /**
     * 编辑排序值
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:27 2019/11/4 0004
     **/
    public Result updateSortNumber(UpdateSortNumberParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        Product product = new Product().setId(params.getId()).setSortNumber(params.getSortNumber());

        return productService.update(product);
    }


    /**
     * 通过产品Id查询商品详情
     *
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:21 2019/11/5 0005
     **/
    public Result findByProductId(String productId) {

        //校验参数
        if (StringUtils.isEmpty(productId)) {
            return Result.failure("产品Id不存在");
        }

        AdminProductVo productVo = productService.findAdminProductVoByProductId(productId);

        return Objects.isNull(productVo) ? Result.failure("产品信息不存在") : Result.success("查询成功", productVo);
    }


    /**
     * 查询产品列表
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:27 2019/11/5 0005
     **/
    public Result appListPage(AppProductQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AppProductListVo> list = productService.appListPage(params);
        PageInfo<AppProductListVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }

    /**
     * 按类别查询产品列表
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:27 2019/11/5 0005
     **/
    public Result appListByType(AppProductQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<AppProductVo> list = productService.appListByType(params);

        return  appFindByProductSet(list);
    }

    /**
     * 根据产品集合查询产品详情
     *
     * @param list
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:28 2019/11/5 0005
     **/
    public Result appFindByProductSet(List<AppProductVo> list) {
        Set<String> productSet = list.stream().map(e -> e.getId()).collect(Collectors.toSet());
        StringBuilder image = new StringBuilder();
        for (AppProductVo appProductVo: list) {
            if (appProductVo.getSmallPic().indexOf(",")!= -1) {
                image.append(appProductVo.getSmallPic().substring(0, appProductVo.getSmallPic().indexOf(","))).append(",");
            }else
                image.append(appProductVo.getSmallPic()).append(",");
        }

        list.get(0).setSmallPic(image.toString().substring(0,image.toString().length() - 1));
        AppProductVo productVo = new AppProductVo();
        BeanUtils.copyProperties(list.get(0), productVo);

        //取商品类一个商品的详情
        Product product = productService.findByProductId(list.get(0).getId());
        productVo.setContent(product.getContent());

        //查询产品的规格信息
        List<ProductSpecVo> productSpecVoList = productSpecService.listProductSpecVoByProductIdSet(productSet);
        productVo.setProductSpecVoList(productSpecVoList);

        //查询产品的商品信息
        List<GoodVo> goodVoList = goodsService.listByProductIdSet(productSet);
        productVo.setGoodVoList(goodVoList);

        //查询商品的销量,后面再做
        List<ProductSaleVo> productSaleVos = productService.listProductSaleVo(productSet);
        if(Lists.isNotEmpty(productSaleVos)){
            productVo.setSalesVolume(productSaleVos.get(0).getSalesVolume());
        }

        return Result.success("查询成功", productVo);
    }

    /**
     * 根据产品Id查询产品详情
     *
     * @param productId
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:28 2019/11/5 0005
     **/
    public Result appFindByProductId(String productId) {

        //校验参数
        if (StringUtils.isEmpty(productId)) {
            return Result.failure("产品Id不存在");
        }

        //判断产品是否存在
        Product productCondition = new Product().setId(productId).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Product> productList = productService.listByCondition(productCondition);
        if (Lists.isEmpty(productList)) {
            return Result.failure("产品信息不存在");
        }

        Product product = productList.get(0);
        if (Objects.equals(product.getShelfStatus(), BooleanConstant.NO_INTEGER)) {
            return Result.failure("该商品已下架");
        }

        AppProductVo productVo = new AppProductVo();
        BeanUtils.copyProperties(product, productVo);

        //查询产品的规格信息
        List<ProductSpecVo> productSpecVoList = productSpecService.listProductSpecVoByProductIdSet(Sets.asSets(productId));
        productVo.setProductSpecVoList(productSpecVoList);

        //查询产品的商品信息
        List<GoodVo> goodVoList = goodsService.listByProductIdSet(Sets.asSets(productId));
        productVo.setGoodVoList(goodVoList);

        //TODO 查询商品的销量,后面再做
        List<ProductSaleVo> productSaleVos = productService.listProductSaleVo(Sets.asSets(productId));
        if(Lists.isNotEmpty(productSaleVos)){
            productVo.setSalesVolume(productSaleVos.get(0).getSalesVolume());
        }

        return Result.success("查询成功", productVo);
    }


    /**
     * 批量修改产品上下架状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:50 2019/11/13 0013
     **/
    @Transactional
    public Result batchUpdateShelfStatus(BatchUpdateProductShelfStatusParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        List<String> idList = params.getIdList();
        for (String id : idList) {
            UpdateShelfStatusParams statusParams = new UpdateShelfStatusParams();
            statusParams.setProductId(id);
            statusParams.setState(params.getState());
            result = updateShelfStatus(statusParams);
            if (!result.getSuccess()) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return result;
            }
        }

        return Result.success("操作成功");
    }

    /**
     * 批量删除产品
     *
     * @param productIdList 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:50 2019/11/13 0013
     **/
    public Result batchDelete(List<String> productIdList) {

        //校验参数
        if (Lists.isEmpty(productIdList)) {
            return Result.failure("产品id为空");
        }

        for (String id : productIdList) {
            Result delete = delete(id);
            if (!delete.getSuccess()) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return delete;
            }
        }

        return Result.success("操作成功");
    }

}
