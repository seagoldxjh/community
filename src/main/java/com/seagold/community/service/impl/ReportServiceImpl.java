/**
 * FileName: ReportServiceSImpl
 * Author:   xjh
 * Date:     2020-02-11 14:48
 * Description: 举报问题业务处理类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seagold.community.entity.Report;
import com.seagold.community.mapper.ReportMapper;
import com.seagold.community.service.QuestionService;
import com.seagold.community.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈举报问题业务处理类〉
 *
 * @author xjh
 * @create 2020-02-11
 * @since 1.0 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private QuestionService questionService;

    private static final int DELETE_STATUS = 2;


    @Override
    public Report isReport(Report report) {
        QueryWrapper<Report> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id",report.getQuestionId());
        wrapper.eq("report_creator", report.getReportCreator());
        return reportMapper.selectOne(wrapper);
    }

    @Override
    public void addReport(Report report) {
        reportMapper.insert(report);
    }

    @Override
    public List<Report> findAllReports() {
        return reportMapper.findAllReports();
    }

    @Override
    public void updateReportStatus(int status, int questionId) {
        reportMapper.updateReportStatus(status, questionId);
        if (status == DELETE_STATUS){
            questionService.deleteQuestion(questionId);
        }
    }
}