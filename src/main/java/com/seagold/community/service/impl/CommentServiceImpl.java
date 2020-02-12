/**
 * FileName: CommentServiceImpl
 * Author:   xjh
 * Date:     2019-08-17 12:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.StringUtil;
import com.seagold.community.dto.CommentDTO;
import com.seagold.community.dto.CommentUserDTO;
import com.seagold.community.entity.Comment;
import com.seagold.community.entity.JsonData;
import com.seagold.community.entity.Notification;
import com.seagold.community.entity.Question;
import com.seagold.community.entity.User;
import com.seagold.community.enums.NotificationStatusEnum;
import com.seagold.community.enums.NotificationTypeEnum;
import com.seagold.community.mapper.CommentMapper;
import com.seagold.community.mapper.NotificationMapper;
import com.seagold.community.mapper.QuestionMapper;
import com.seagold.community.mapper.UserMapper;
import com.seagold.community.service.CommentService;
import com.seagold.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-17
 * @since 1.0.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    private final QuestionMapper questionMapper;

    private final QuestionService questionService;

    private final UserMapper userMapper;

    private final NotificationMapper notificationMapper;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper, QuestionMapper questionMapper, QuestionService questionService, UserMapper userMapper, NotificationMapper notificationMapper) {
        this.commentMapper = commentMapper;
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.userMapper = userMapper;
        this.notificationMapper = notificationMapper;
    }

    @Override
    @Transactional
    public Object insert(CommentDTO commentDTO, HttpSession session) {

        User user = (User)session.getAttribute("user");
        if (user == null) {
            return JsonData.buildError("你还未登陆哦!请先登录在进行评论。");
        }

        if (commentDTO == null || StringUtil.isEmpty(commentDTO.getContent())){
            return JsonData.buildError("评论不能为空,请填写评论后在发表哦!");
        }

        if (commentDTO.getParentId() == null || commentDTO.getParentId() == 0){
            return JsonData.buildError("未选中任何问题或评论进行回复");
        }

        if (commentDTO.getType() == null) {
            return JsonData.buildError("评论类型错误或不存在");
        }

        //type为2表示回复的评论，判断评论是否存在
        if (commentDTO.getType().equals(2)){
            Comment dbComment = commentMapper.selectById(commentDTO.getParentId());
            if (dbComment == null){
                return JsonData.buildError("您回复的评论不存在或已被删除!");
            }
        }

        //type为1表示回复的问题，判断问题是否存在
        if (commentDTO.getType().equals(1)){
            Question question = questionMapper.selectById(commentDTO.getParentId());
            if (question == null){
                return JsonData.buildError("您回复的问题不存在或已被删除!");
            }
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        questionService.incCommentCount(commentDTO.getParentId());
        commentMapper.insert(comment);

        Question  question = questionMapper.selectById(commentDTO.getParentId());
        if(question != null){
            createNotify(comment, question.getCreator(), user.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }else {

            Comment comment1 = commentMapper.selectById(comment.getParentId());
            System.out.println(comment1);
            System.out.println(comment1);
            System.out.println(comment1);
            System.out.println(comment1);
            System.out.println(comment1);
            Long questionId = comment1.getParentId();


            createNotify(comment1, comment1.getCommentator(), user.getName(), comment1.getContent(), NotificationTypeEnum.REPLY_COMMENT, questionId);
        }

        return JsonData.buildSuccess("成功", comment.toString());
    }



    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        System.out.println("进入创建通知方法");

//        if (receiver.equals(comment.getCommentator())) {
//            System.out.println(receiver);
//            System.out.println(comment.getCommentator());
//            return;
//        }


        System.out.println("通知创建");
        System.out.println("+++++++++++++++++++++++++++++++++++++++");

        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }



    /**
     * 用于展示id的所有评论数据,包括评论人的name，头像，发起评论时间及评论内容
     * 查询question中id=？的type=1的所有评论，表示评论为评论问题，而不是二级评论
     * @param id 问题id
     * @param val
     * @return 返回评论问题id=？并且是一级评论的所有评论信息及评论人信息
     */
    @Override
    public List<CommentUserDTO> findAllById(Long id, int val) {
        List<CommentUserDTO> commentUserDTOS = new ArrayList<>();
        List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().eq("parent_id", id).eq("type", val));
        if(comments==null || comments.size()==0){
            System.out.println("没有评论存在");
            return new ArrayList<CommentUserDTO>(0);
        }
        for (Comment comment : comments) {
            User user = userMapper.selectById(comment.getCommentator());
            CommentUserDTO commentUserDTO = new CommentUserDTO();
            BeanUtils.copyProperties(comment, commentUserDTO);
            commentUserDTO.setUser(user);
            commentUserDTOS.add(commentUserDTO);
        }
        return commentUserDTOS;
    }
}