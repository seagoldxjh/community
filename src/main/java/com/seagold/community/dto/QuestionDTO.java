/**
 * FileName: QuestionDTO
 * Author:   xjh
 * Date:     2019-08-15 20:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import com.seagold.community.entity.User;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈问题的发起者及问题的所有信息类〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private int viewCount;
    private int commentCount;
    private int likeCount;
    private String headImg;
    private Integer choice;
    private User user;
}