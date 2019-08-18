/**
 * FileName: FileDTO
 * Author:   xjh
 * Date:     2019-08-18 18:36
 * Description: 富文本编辑器上传图片返回json数据类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈富文本编辑器上传图片返回json数据类〉
 *
 * @author xjh
 * @create 2019-08-18
 * @since 1.0.0
 */
@Data
public class FileDTO {
    private int success;
    private String message;
    private String url;
}