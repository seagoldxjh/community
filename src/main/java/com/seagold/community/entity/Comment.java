/**
 * FileName: Comment
 * Author:   xjh
 * Date:     2019-08-17 12:31
 * Description: 评论回复实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.entity;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈评论回复实体类〉
 *
 * @author xjh
 * @create 2019-08-17
 * @since 1.0.0
 */
@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private int commentCount;
}