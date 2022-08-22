package com.seagold.community.controller;

import com.seagold.community.entity.Question;
import com.seagold.community.service.QuestionService;
import com.seagold.community.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xjh
 * @date 2021-01-14
 */
@RestController
public class TestController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ReportService reportService;


    @GetMapping("/testIsolation")
    @Transactional
    public String testIsolation(){
        System.err.println("------");
        List<Question> allQuestion = questionService.findAllQuestion();
        System.err.println(allQuestion.size());
        System.err.println(allQuestion);
        System.err.println(questionService.findAllQuestion().size());
        return "success";
    }





    @GetMapping("/test")
    @Transactional(rollbackFor = Throwable.class)
    public String testTransaction() throws FileNotFoundException {

        questionService.insertQuestion(getQuestion());

        System.err.println(testTransaction2());

//        reportService.addReport(null);
        return "request success";
    }

    @Transactional(rollbackFor = Throwable.class)
    public String testTransaction2() throws FileNotFoundException {

//        questionService.insertQuestion(getQuestion());
        reportService.addReport(null);

        return "request success";
    }

    @Transactional(rollbackFor = NullPointerException.class)
    @GetMapping("/tx")
    public Question getQuestion() throws FileNotFoundException {
        Question question = new Question();
        question.setTitle("事物测试OOM Error");
        questionService.insertQuestion(question);
//        FileInputStream fis = new FileInputStream("D://a.txt");

        List<Byte[]> list = new ArrayList<>();
        while (true) {
            Byte[] bytes = new Byte[1024 * 32];
            list.add(bytes);
        }
    }
}