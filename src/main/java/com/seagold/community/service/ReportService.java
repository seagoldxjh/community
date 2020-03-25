package com.seagold.community.service;

import com.seagold.community.entity.Report;

import java.util.List;

public interface ReportService {
    /**
     * 查询用户是否举报过此问题
     * @param report：含有用户id，问题id
     * @return
     */
    Report isReport(Report report);

    void addReport(Report report);

    /**
     * 查询所有举报内容
     * @return
     */
    List<Report> findAllReports();


    /**
     * 修改问题状态
     * @param status 修改状态数值
     * @param questionId 修改问题id
     */
    void updateReportStatus(int status,int questionId);
}
