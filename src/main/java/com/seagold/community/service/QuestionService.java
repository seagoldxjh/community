/**
 * FileName: QuestionService
 * Author:   xjh
 * Date:     2019-08-15 19:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.service;

import com.seagold.community.dto.QuestionDTO;
import com.seagold.community.entity.Question;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
public interface QuestionService {
    void insertQuestion(Question question);
    List<Question> findAll(String search,String tag,String sort);
    List<Question> findAllQuestion();

    /**
     * 根据传入的用户id，查询该用户提出的所有问题
     * @param id
     * @return
     */
    List<Question> findAllByCreator(Long id);

    List<Question> searchQuestions(String search);

    QuestionDTO findById(Long id);


    void updata(Question question);

    void incView(Long id);
    void incCommentCount(Long id);

    List<Question> selectRelated(String tag);

    List<Question> searchQuestionsByTag(String tag);

    List<Question> searchQuestionsBySort(String sort);

    /**
     * 根据问题id删除问题
     * @param questionId 问题id
     * @return
     */
    int deleteQuestion(int questionId);
}