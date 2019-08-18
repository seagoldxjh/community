/**
 * FileName: NotificationUserDTO
 * Author:   xjh
 * Date:     2019-08-18 14:41
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
 * @create 2019-08-18
 * @since 1.0.0
 */
@Data
public class NotificationUserDTO {
    private Long id;
    private Long notifier;

    private Long outerid;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private String typeName;
}