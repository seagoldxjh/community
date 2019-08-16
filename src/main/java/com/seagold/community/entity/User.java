/**
 * FileName: User
 * Author:   xjh
 * Date:     2019-08-15 14:36
 * Description: User实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈User实体类〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String accountId;
    private String token;
    private String headImg;

}