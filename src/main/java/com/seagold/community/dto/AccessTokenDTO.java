/**
 * FileName: AccessTokenDTO
 * Author:   xjh
 * Date:     2019-08-15 12:35
 * Description: github授权登陆数据传输对象类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈github授权登陆数据传输对象类〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}