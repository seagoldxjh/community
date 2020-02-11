package com.seagold.community.service;

import com.seagold.community.entity.Report;

public interface ReportService {
    Report isReport(Report report);

    void addReport(Report report);
}
