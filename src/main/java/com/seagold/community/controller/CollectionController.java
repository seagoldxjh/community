/**
 * FileName: CollectionController
 * Author:   xjh
 * Date:     2020-02-07 13:43
 * Description: 收藏控制
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.seagold.community.entity.Collection;
import com.seagold.community.entity.JsonData;
import com.seagold.community.entity.User;
import com.seagold.community.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 〈一句话功能简述〉<br> 
 * 〈收藏控制〉
 *
 * @author xjh
 * @create 2020-02-07
 * @since 1.0.0
 */
@Controller
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 用户收藏问题
     * @param questionId
     * @param session
     * @return
     */
    @ResponseBody
    @GetMapping("/collect")
    public JsonData collect(@RequestParam(value = "questionId", required = false) Long questionId, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return JsonData.buildError("用户未登录", 401);
        }
        if (questionId == null){
            return JsonData.buildError("问题id为空", 402);
        }
        Collection collection = new Collection();
        collection.setUserId(user.getId());
        collection.setQuestionId(questionId);
        collection.setGmtCreate(System.currentTimeMillis());
        collectionService.collect(collection);
        return JsonData.buildSuccess("收藏成功", 200);
    }

    /**
     * 查询用户是否收藏某个问题
     */
    @ResponseBody
    @GetMapping("/isCollect")
    public JsonData isCollect(@RequestParam(value = "questionId", required = false) Long questionId, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return JsonData.buildError("用户未登录", 401);
        }
        if (questionId == null){
            return JsonData.buildError("问题id为空", 402);
        }
        Collection collection = new Collection();
        collection.setUserId(user.getId());
        collection.setQuestionId(questionId);
        if(collectionService.isCollect(collection) != null){
            return JsonData.buildSuccess("您已经收藏过此问题了", 200);
        }
        return JsonData.buildSuccess("还未收藏此问题",201);
    }


}