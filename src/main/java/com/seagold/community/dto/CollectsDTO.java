/**
 * FileName: CollectsDTO
 * Author:   xjh
 * Date:     2020-02-07 16:19
 * Description: 个人收藏数据传输类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈个人收藏数据传输类〉
 *
 * @author xjh
 * @create 2020-02-07
 * @since 1.0.0
 */
@Data
public class CollectsDTO {
    private Long collectId;
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
    private Long gmtCreate2;
}