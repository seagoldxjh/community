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
    List<QuestionDTO> findAll();
}