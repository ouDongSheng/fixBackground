package com.logicalthining.endeshop.common.requestVo.comment;

import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2020/1/13.
 */
@ApiModel
@Setter
@Getter
public class CommentQueryParams extends PageAbleVo {

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String productId;

}