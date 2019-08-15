/**
 * FileName: GithubUser
 * Author:   xjh
 * Date:     2019-08-15 12:54
 * Description: GitHub用户类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈GitHub用户类〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Data
public class GithubUser {

    private Long id;
    private String name;
    private String bio;
    //描述
}