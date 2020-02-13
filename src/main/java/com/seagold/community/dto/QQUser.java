/**
 * FileName: QQUser
 * Author:   xjh
 * Date:     2020-02-13 17:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2020-02-13
 * @since 1.0.0
 */
@Data
public class QQUser {
    private Long uuid;
    private String username;
    //描述
//    private String bio;
    //微博头像
    private String avatar;
}