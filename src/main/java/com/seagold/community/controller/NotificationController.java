/**
 * FileName: NotificationController
 * Author:   xjh
 * Date:     2019-08-18 15:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.seagold.community.dto.NotificationUserDTO;
import com.seagold.community.entity.User;
import com.seagold.community.enums.NotificationTypeEnum;
import com.seagold.community.mapper.QuestionMapper;
import com.seagold.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-18
 * @since 1.0.0
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationUserDTO notificationDTO = notificationService.read(id, user);
        System.out.println(notificationDTO);

        if(notificationDTO == null){
            return "redirect:/";
        }

        if(notificationDTO.getType() == NotificationTypeEnum.REPLY_QUESTION.getType() ||
                NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()){
            return "redirect:/question/" + notificationDTO.getOuterid();
        }


        return null;
    }
}