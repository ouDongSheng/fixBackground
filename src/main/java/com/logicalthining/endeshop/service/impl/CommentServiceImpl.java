package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.common.requestVo.comment.CommentQueryParams;
import com.logicalthining.endeshop.common.responseVo.comment.CommentCount;
import com.logicalthining.endeshop.common.responseVo.comment.CommentInfoListVo;
import com.logicalthining.endeshop.common.responseVo.comment.CommentListVo;
import com.logicalthining.endeshop.dao.CommentInfoMapper;
import com.logicalthining.endeshop.dao.CommentMapper;
import com.logicalthining.endeshop.entity.Comment;
import com.logicalthining.endeshop.service.CommentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/1/9.
 */
@Service
public class CommentServiceImpl implements CommentServiceI {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentInfoMapper commentInfoMapper;

    @Override
    public Result add(Comment params) {
        int i = commentMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    @Override
    public List<CommentListVo> listAll(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        List<CommentListVo> commentListVos = commentMapper.listAll(params);     //评价

        Set commentIdSet = new HashSet<>();
        for (CommentListVo commentListVo : commentListVos) {
            commentIdSet.add(commentListVo.getId());
        }

        List<CommentInfoListVo> commentInfoList = commentInfoMapper.getCommentInfoById(commentIdSet);   //评价详情

        for (CommentListVo commentListVo : commentListVos) {
            List<CommentInfoListVo> collect = commentInfoList.stream().filter(e -> Objects.equals(e.getCommentId(), commentListVo.getId())).collect(Collectors.toList());
            commentListVo.setCommentInfoListVo(collect);
        }

        return commentListVos;
    }

    @Override
    //带图评价
    public List<CommentListVo> listImage(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<CommentListVo> commentListVos = commentMapper.listAll(params);

        Set commentIdSet = new HashSet<>();
        for (CommentListVo commentListVo : commentListVos) {
            commentIdSet.add(commentListVo.getId());
        }

        List<CommentInfoListVo> commentInfoList = commentInfoMapper.getImageCommentInfoById(commentIdSet);   //评价详情

        List<CommentListVo> outIsImageListVo = new ArrayList<>();
        for (CommentListVo commentListVo : commentListVos) {
            List<CommentInfoListVo> collect = commentInfoList.stream().filter(e -> Objects.equals(e.getCommentId(), commentListVo.getId())).collect(Collectors.toList());
            if (collect.size() != 0) {
                commentListVo.setCommentInfoListVo(collect);
                outIsImageListVo.add(commentListVo);
            }
        }

        return outIsImageListVo;
    }

    @Override
    public List<CommentListVo> listGood(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        List<CommentListVo> commentListVos = commentMapper.listGood(params); //获取好评

        Set commentIdSet = new HashSet<>();
        for (CommentListVo commentListVo : commentListVos) {
            commentIdSet.add(commentListVo.getId());
        }

        List<CommentInfoListVo> commentInfoList = commentInfoMapper.getCommentInfoById(commentIdSet);   //评价详情

        for (CommentListVo commentListVo : commentListVos) {
            List<CommentInfoListVo> collect = commentInfoList.stream().filter(e -> Objects.equals(e.getCommentId(), commentListVo.getId())).collect(Collectors.toList());
            commentListVo.setCommentInfoListVo(collect);
        }

        return commentListVos;
    }
    @Override
    public List<CommentListVo> listMedium(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<CommentListVo> commentListVos = commentMapper.listMedium(params);     //评价

        Set commentIdSet = new HashSet<>();
        for (CommentListVo commentListVo : commentListVos) {
            commentIdSet.add(commentListVo.getId());
        }

        List<CommentInfoListVo> commentInfoList = commentInfoMapper.getCommentInfoById(commentIdSet);   //评价详情

        for (CommentListVo commentListVo : commentListVos) {
            List<CommentInfoListVo> collect = commentInfoList.stream().filter(e -> Objects.equals(e.getCommentId(), commentListVo.getId())).collect(Collectors.toList());
            commentListVo.setCommentInfoListVo(collect);
        }

        return commentListVos;
    }

    @Override
    public List<CommentListVo> listBad(CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<CommentListVo> commentListVos = commentMapper.listBad(params);     //评价

        Set commentIdSet = new HashSet<>();
        for (CommentListVo commentListVo : commentListVos) {
            commentIdSet.add(commentListVo.getId());
        }

        List<CommentInfoListVo> commentInfoList = commentInfoMapper.getCommentInfoById(commentIdSet);   //评价详情

        for (CommentListVo commentListVo : commentListVos) {
            List<CommentInfoListVo> collect = commentInfoList.stream().filter(e -> Objects.equals(e.getCommentId(), commentListVo.getId())).collect(Collectors.toList());
            commentListVo.setCommentInfoListVo(collect);
        }

        return commentListVos;
    }

    @Override
    public List<CommentCount> listAllCount (CommentQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        List<CommentCount> allCounts = new ArrayList<>();
        allCounts.add(commentMapper.allCount(params));
        allCounts.add(commentMapper.goodCount(params));
        allCounts.add(commentMapper.mediumCount(params));
        allCounts.add(commentMapper.badCount(params));
        allCounts.add(commentMapper.imageCount(params));
        return  allCounts;
    }
}