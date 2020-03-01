package com.logicalthining.endeshop.common.responseVo.comment;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Setter
@Getter
public class CommentCount {
    private int count;

    private String name;
}
