/**
 * FileName: QuestionServiceImpl
 * Author:   xjh
 * Date:     2019-08-15 19:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.StringUtil;
import com.seagold.community.dto.QuestionDTO;
import com.seagold.community.entity.Comment;
import com.seagold.community.entity.Question;
import com.seagold.community.entity.User;
import com.seagold.community.enums.SortEnum;
import com.seagold.community.mapper.CommentMapper;
import com.seagold.community.mapper.QuestionMapper;
import com.seagold.community.mapper.UserMapper;
import com.seagold.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private  UserMapper userMapper;

    @Autowired
    private  CommentMapper commentMapper;

    @Override
    public void insertQuestion(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public List<Question> findAll(String search,String tag,String sort) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (!StringUtil.isEmpty(search)){
            wrapper.like("title", search);
        }
        if (!StringUtil.isEmpty(tag)){
            wrapper.like("tag", tag);
        }
        if (SortEnum.NO.name().toLowerCase().equals(sort)){
            wrapper.orderByDesc("gmt_create").eq("comment_count", 0);
            return questionMapper.selectList(wrapper);
        }

        if (StringUtil.isEmpty(sort)){
            wrapper.orderByDesc("gmt_create");
            return questionMapper.selectList(wrapper);
        }

        if (SortEnum.CHOICE.name().toLowerCase().equals(sort)){
            System.out.println("进入精华");
            wrapper.orderByDesc("gmt_create").eq("choice", 1);
            return questionMapper.selectList(wrapper);
        }

        wrapper.orderByDesc("view_count","comment_count");
        if (SortEnum.HOT7.name().toLowerCase().equals(sort)){
            wrapper.gt("gmt_create", System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
        }

        if (SortEnum.HOT30.name().toLowerCase().equals(sort)){
            wrapper.gt("gmt_create", System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
        }

        return questionMapper.selectList(wrapper);

    }

    @Override
    public List<Question> findAllQuestion() {
        return questionMapper.selectList(new QueryWrapper<Question>().orderByDesc("id"));
    }

    /**
     * 根据用户个人id，查询自己发起过的所有问题
     * @param id
     * @return
     */
    @Override
    public List<Question> findAllByCreator(Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("creator", id);
        return questionMapper.selectByMap(map);
    }

    @Override
    public List<Question> searchQuestions(String search) {
        return questionMapper.selectList(new QueryWrapper<Question>().like("title", search).orderByDesc("id"));
    }

    /**
     * 根据问题的id查询该问题的所有信息，在封装一个发起问题用户的个人信息
     * @param id
     * @return
     */
    @Override
    public QuestionDTO findById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();

        Question question = questionMapper.selectById(id);
        if (question == null){
            return null;
        }
        question.setViewCount(question.getViewCount() + 1);
        questionMapper.updateById(question);



        User user = userMapper.selectById(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void updata(Question question) {
        questionMapper.updateById(question);
    }

    /**
     * 根据id查询到问题，对问题的浏览数+1
     * @param id
     */
    @Override
    public void incView(Long id) {
        Question question = questionMapper.selectById(id);
        question.setViewCount(question.getViewCount() + 1);
        questionMapper.updateById(question);
    }

    /**
     * 根据id查询到问题，对问题的评论数+1
     * 回复了评论或者问题也同时发出通知用户回答了问题
     * @param id
     */
    @Override
    public void incCommentCount(Long id) {
        Question question = questionMapper.selectById(id);
        if(question != null){
            question.setCommentCount(question.getCommentCount() + 1);
            questionMapper.updateById(question);
        }else {
            Comment comment = commentMapper.selectById(id);
            comment.setCommentCount(comment.getCommentCount() + 1);
            commentMapper.updateById(comment);
        }
    }

    /**
     * 根据传入标签,并查找与该标签相关的返回
     * @param tag
     * @return
     */
    @Override
    public List<Question> selectRelated(String tag) {
        if (StringUtil.isEmpty(tag)){
            return new ArrayList<>(0);
        }

        QueryWrapper<Question> wrapper = new QueryWrapper<Question>();
        String[] split = tag.split(",");

        for (String s : split) {
            wrapper.like("tag", s).or();
        }
        return questionMapper.selectList(wrapper);
    }

    @Override
    public List<Question> searchQuestionsByTag(String tag) {
        return questionMapper.selectList(new QueryWrapper<Question>().like("tag",tag));
    }

    /**
     * 消灭零回复直接查询评论数为0的问题
     * 构造7天最热，30天最热，最热话题查询sql
     * @param sort
     * @return
     */
    @Override
    public List<Question> searchQuestionsBySort(String sort) {
        QueryWrapper<Question> wrapper = new QueryWrapper<Question>();

        if (SortEnum.NO.name().toLowerCase().equals(sort)){
            wrapper.orderByDesc("gmt_create").eq("comment_count", 0);
            return questionMapper.selectList(wrapper);
        }


        wrapper.orderByDesc("view_count","comment_count");
        if (SortEnum.HOT7.name().toLowerCase().equals(sort)){
            wrapper.gt("gmt_create", System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
        }

        if (SortEnum.HOT30.name().toLowerCase().equals(sort)){
            wrapper.gt("gmt_create", System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
        }

        return questionMapper.selectList(wrapper);
    }

    @Override
    public int deleteQuestion(int questionId) {
        return questionMapper.deleteById(questionId);
    }

    @Override
    public void addChoice(int questionId) {
        questionMapper.addChoice(questionId);
    }
}