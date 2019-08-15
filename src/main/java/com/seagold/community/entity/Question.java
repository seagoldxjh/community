/**
 * FileName: Question
 * Author:   xjh
 * Date:     2019-08-15 17:20
 * Description: 问题实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈问题实体类〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Data
public class Question {
    @TableId(type = IdType.AUTO)
    private int id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int viewCount;
    private int commentCount;
    private int likeCount;
    private String headImg;
}