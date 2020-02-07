/**
 * FileName: Collection
 * Author:   xjh
 * Date:     2020-02-07 13:17
 * Description: 用户收藏问题
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
 * 〈用户收藏问题〉
 *
 * @author xjh
 * @create 2020-02-07
 * @since 1.0.0
 */
@Data
public class Collection {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long questionId;
    private Long gmtCreate;
}