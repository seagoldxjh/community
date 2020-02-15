/**
 * FileName: UserLike
 * Author:   xjh
 * Date:     2020-02-15 15:01
 * Description: 用户点赞表
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.entity;

import com.seagold.community.enums.LikedStatusEnum;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户点赞表〉
 *
 * @author xjh
 * @create 2020-02-15
 * @since 1.0.0
 */
@Data
public class UserLike {
    private Integer id;

    //被点赞的用户的id
    private String likedUserId;

    //点赞的用户的id
    private String likedPostId;

    //点赞的状态.默认未点赞
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public UserLike() {
    }

    public UserLike(String likedUserId, String likedPostId, Integer status) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }
}