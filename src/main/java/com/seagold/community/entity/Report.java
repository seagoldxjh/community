/**
 * FileName: Report
 * Author:   xjh
 * Date:     2020-02-11 13:52
 * Description: 举报实体类
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
 * 〈举报实体类〉
 *
 * @author xjh
 * @create 2020-02-11
 * @since 1.0.0
 */
@Data
public class Report {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long questionId;
    private String questionTitle;
    private String reportType;
    private String reportDetails;
    private Long reportCreator;
    private String reportUser;
    private Long gmtCreate;
}