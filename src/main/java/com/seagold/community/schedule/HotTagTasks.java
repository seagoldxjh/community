/**
 * FileName: HotTagTasks
 * Author:   xjh
 * Date:     2019-08-27 12:33
 * Description: 利用定时任务查看热门标签
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.schedule;

import com.seagold.community.cache.HotTagCache;
import com.seagold.community.entity.Question;
import com.seagold.community.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈利用定时任务查看热门标签〉
 *  查询所有问题获取每个问题的标签
 *  标签名作为key,包含这个标签的问题数字及评论数按比例加入map中
 *  map实现Comparable接口用于排序放入list中
 *
 * @author xjh
 * @create 2019-08-27
 * @since 1.0.0
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    //每天中午12点
    @Scheduled(cron = "0 0 12 * * ?")
    public void hotTagSchedule(){
        Map<String, Integer> priorities = new HashMap<>(16);
        List<Question> questions = questionMapper.selectList(null);
        for (Question question : questions) {
            String[] tags = question.getTag().split(",");
            for (String tag : tags) {
                Integer priority = priorities.get(tag);
                if (priority != null) {
                    priorities.put(tag, priority + 5 + question.getCommentCount());
                } else {
                    priorities.put(tag, 5 + question.getCommentCount());
                }
            }
        }

        hotTagCache.updateTags(priorities);
    }

}