/**
 * FileName: LikedCountDTO
 * Author:   xjh
 * Date:     2020-02-15 15:18
 * Description: 被点赞次数
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈被点赞次数〉
 *
 * @author xjh
 * @create 2020-02-15
 * @since 1.0.0
 */
@Data
public class LikedCountDTO {
    private String likedUserId;
    private Integer count;

    public LikedCountDTO(){

    }

    public LikedCountDTO(String likedUserId,Integer count){
        this.likedUserId = likedUserId;
        this.count = count;
    }
}