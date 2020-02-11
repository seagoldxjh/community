/**
 * FileName: ReportController
 * Author:   xjh
 * Date:     2020-02-10 18:52
 * Description: 问题举报控制器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.github.pagehelper.StringUtil;
import com.seagold.community.dto.QuestionDTO;
import com.seagold.community.entity.JsonData;
import com.seagold.community.entity.Report;
import com.seagold.community.entity.User;
import com.seagold.community.service.QuestionService;
import com.seagold.community.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 〈一句话功能简述〉<br> 
 * 〈问题举报控制器〉
 *
 * @author xjh
 * @create 2020-02-10
 * @since 1.0.0
 */
@Controller
public class ReportController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ReportService reportService;

    /**
     * 返回该问题内容显示到举报页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/report/{id}")
    public String reportView(@PathVariable(name = "id")Long id, Model model){
        QuestionDTO que = questionService.findById(id);
        model.addAttribute("question",que);
        return "report";
    }

    /**
     * 查询用户是否举报过此问题
     */
    @ResponseBody
    @GetMapping("/isReport")
    public JsonData isReport(@RequestParam(value = "questionId", required = false) Long questionId, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return JsonData.buildError("用户未登录", 401);
        }
        if (questionId == null){
            return JsonData.buildError("问题id为空", 402);
        }
        Report report = new Report();
        report.setReportCreator(user.getId());
        report.setQuestionId(questionId);
        if (reportService.isReport(report) != null){
            return JsonData.buildSuccess("您已经举报过此问题了", 200);
        }
        return JsonData.buildSuccess("还未举报此问题",201);

    }

    @PostMapping("/report")
    public String form(Report report,Model model){


        if (report == null){
            return "redirect:/";
        }

        if (report.getQuestionId() == null){
            return "redirect:/";
        }

        if (StringUtil.isEmpty(report.getReportUser())){
            model.addAttribute("error","请不要非法篡改数据!");
            return "redirect:/report/" + report.getQuestionId();
        }

        if (StringUtil.isEmpty(report.getQuestionTitle())){
            return "redirect:/report/" + report.getQuestionId();
        }

        if (StringUtil.isEmpty(report.getReportType())){
            model.addAttribute("error","请选择举报原因!");
            return "redirect:/report/" + report.getQuestionId();
        }

        if (StringUtil.isEmpty(report.getReportDetails()) || report.getReportDetails().length()>100){
            model.addAttribute("error","请填写详细说明（最多支持100字符）!");
            return "redirect:/report/" + report.getQuestionId();
        }

        report.setGmtCreate(System.currentTimeMillis());
        reportService.addReport(report);
        return "redirect:/question/" + report.getQuestionId();
    }
}