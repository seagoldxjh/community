/**
 * FileName: WeiBoUser
 * Author:   xjh
 * Date:     2020-02-12 14:53
 * Description: 微博授权用户信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈微博授权用户信息〉
 *
 * @author xjh
 * @create 2020-02-12
 * @since 1.0.0
 */
@Data
public class WeiBoUser {
    private Long uuid;
    private String username;
    //描述
//    private String bio;
    //微博头像
    private String avatar;
}