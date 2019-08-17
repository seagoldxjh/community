/**
 * FileName: CommentController
 * Author:   xjh
 * Date:     2019-08-17 12:29
 * Description: 评论回复控制类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.seagold.community.dto.CommentDTO;
import com.seagold.community.dto.CommentUserDTO;
import com.seagold.community.entity.JsonData;
import com.seagold.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈评论回复控制类〉
 *
 * @author xjh
 * @create 2019-08-17
 * @since 1.0.0
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 用户发起评论
     * @param commentDTO
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO, HttpSession session){
        Object obj = commentService.insert(commentDTO, session);
        return obj;
    }

    /**
     * 展开二级评论
     * 2表示2级评论
     * @param id 父亲id也就是一级评论中的评论id，
     * @return
     */
    @ResponseBody
    @GetMapping("/comment/{id}")
    public Object comments(@PathVariable(name = "id") Long id){
        List<CommentUserDTO> commentUserDTOS = commentService.findAllById(id, 2);
        return JsonData.buildSuccess(commentUserDTOS);
    }
}