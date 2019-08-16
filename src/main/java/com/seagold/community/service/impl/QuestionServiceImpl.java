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

import com.seagold.community.dto.QuestionDTO;
import com.seagold.community.entity.Question;
import com.seagold.community.entity.User;
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
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public void insertQuestion(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public List<QuestionDTO> findAll() {
        List<Question> questions = questionMapper.selectList(null);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    @Override
    public List<Question> findAllQuestion() {
        return questionMapper.selectList(null);
    }

    /**
     * 根据用户个人id，查询自己发起过的所有问题
     * @param id
     * @return
     */
    @Override
    public List<Question> findAllByCreator(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("creator", id);
        return questionMapper.selectByMap(map);
    }

    /**
     * 根据问题的id查询该问题的所有信息，在封装一个发起问题用户的个人信息
     * @param id
     * @return
     */
    @Override
    public QuestionDTO findById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();

        Question question = questionMapper.selectById(id);
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

    @Override
    public void incView(Integer id) {
        Question question = questionMapper.selectById(id);
        question.setViewCount(question.getViewCount() + 1);
        questionMapper.updateById(question);
    }
}