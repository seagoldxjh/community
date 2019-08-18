/**
 * FileName: TagDTO
 * Author:   xjh
 * Date:     2019-08-18 12:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈标签库传输model类〉
 *
 * @author xjh
 * @create 2019-08-18
 * @since 1.0.0
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}