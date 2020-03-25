/**
 * FileName: ReportMapper
 * Author:   xjh
 * Date:     2020-02-11 14:49
 * Description: 举报数据处理层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagold.community.entity.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈举报数据处理层〉
 *
 * @author xjh
 * @create 2020-02-11
 * @since 1.0.0
 */
public interface ReportMapper extends BaseMapper<Report> {
    List<Report> findAllReports();
    void updateReportStatus(@Param("status")int status,
                            @Param("questionId")int questionId);
}