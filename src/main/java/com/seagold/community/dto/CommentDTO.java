/**
 * FileName: CommentDTO
 * Author:   xjh
 * Date:     2019-08-17 12:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用于展示评论所必须的提供的参数封装类〉
 *  parentId： 评论的问题或者评论的id
 *  type：1表示评论的是问题，2表示评论的是评论
 * @author xjh
 * @create 2019-08-17
 * @since 1.0.0
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
    private Integer commentCount;
}