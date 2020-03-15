package com.logicalthining.endeshop.common.responseVo.comment;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2020/1/14.
 */
@Setter
@Getter
public class CommentInfoListVo {
    private String id;

    private String userId;

    private String content;

    private String images;

    private String commentId;

    private String parentId;

    private String account;

    private String headImage;
}