/**
 * FileName: adminController
 * Author:   xjh
 * Date:     2020-03-01 19:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller.admin;

import com.seagold.community.entity.Report;
import com.seagold.community.entity.User;
import com.seagold.community.service.ReportService;
import com.seagold.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2020-03-01
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @GetMapping("/report")
    public String page(Model model){
        List<Report> allReports = reportService.findAllReports();
        model.addAttribute("Reports",allReports);
        return "admin";
    }

    @ResponseBody
    @GetMapping("/report/{status}")
    public void reportStatus(@PathVariable(value = "status")int status, @RequestParam("questionId")int questionId){
        reportService.updateReportStatus(status, questionId);
    }

    @GetMapping("user")
    public String userPage(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "user";
    }
}