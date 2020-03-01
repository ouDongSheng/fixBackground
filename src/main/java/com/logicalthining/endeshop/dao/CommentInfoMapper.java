package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.responseVo.comment.CommentInfoListVo;
import com.logicalthining.endeshop.entity.CommentInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2020/1/10.
 */
public interface CommentInfoMapper extends Mapper<CommentInfo> {

    Integer batchAdd(@Param("list") List<CommentInfo> list);

    List<CommentInfoListVo> getCommentInfoById(@Param("commentIdSet") Set<String> commentIdSet);

    List<CommentInfoListVo> getImageCommentInfoById(@Param("commentIdSet") Set<String> commentIdSet);

}