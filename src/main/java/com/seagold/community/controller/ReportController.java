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

import com.seagold.community.dto.QuestionDTO;
import com.seagold.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/report/{id}")
    public String reportView(@PathVariable(name = "id")Long id, Model model){
        QuestionDTO que = questionService.findById(id);
        model.addAttribute("question",que);
        return "report";
    }
}