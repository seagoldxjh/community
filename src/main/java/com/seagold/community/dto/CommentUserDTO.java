/**
 * FileName: CommentUserDTO
 * Author:   xjh
 * Date:     2019-08-17 15:53
 * Description: 返回问题的所有评论列表,评论人信息数据类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import com.seagold.community.entity.User;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈返回问题的所有评论列表,评论人信息数据类〉
 *
 * @author xjh
 * @create 2019-08-17
 * @since 1.0.0
 */
@Data
public class CommentUserDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;
    private Integer status;
    private Integer likedCount = 0;
}