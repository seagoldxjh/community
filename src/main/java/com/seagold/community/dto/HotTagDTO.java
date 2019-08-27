/**
 * FileName: HotTagDTO
 * Author:   xjh
 * Date:     2019-08-27 13:09
 * Description: 最热标签
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.dto;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈最热标签〉
 *
 * @author xjh
 * @create 2019-08-27
 * @since 1.0.0
 */
@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}