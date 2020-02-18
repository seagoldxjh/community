/**
 * FileName: LikedController
 * Author:   xjh
 * Date:     2020-02-15 15:25
 * Description: 用户点赞控制类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.seagold.community.entity.JsonData;
import com.seagold.community.entity.User;
import com.seagold.community.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户点赞控制类〉
 *
 * @author xjh
 * @create 2020-02-15
 * @since 1.0.0
 */
@Controller
public class LikedController {

    @Autowired
    private RedisService redisService;

    /**
     * 进行点赞
     * @param likedUserId
     * @param session
     * @return
     */
    @GetMapping("/like/{likedUserId}")
    @ResponseBody
    public JsonData saveLikedRedis(@PathVariable(value = "likedUserId")String likedUserId, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return JsonData.buildError("请先登录后点赞",200);
        }

        redisService.saveLikedRedis(likedUserId, String.valueOf(user.getId()));
        redisService.incrementLikedCount(likedUserId);
        return JsonData.buildSuccess("点赞成功");
    }

    /**
     * 取消点赞
     * @param likedUserId
     * @param session
     * @return
     */
    @GetMapping("/unlike/{likedUserId}")
    @ResponseBody
    public JsonData saveUnLikedRedis(@PathVariable(value = "likedUserId")String likedUserId, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return JsonData.buildError("请先登录后取消点赞",200);
        }

        redisService.unlikeFromRedis(likedUserId, String.valueOf(user.getId()));
        redisService.decrementLikedCount(likedUserId);
        return JsonData.buildSuccess("取消点赞成功");
    }

    /**
     * 查询用户是否点赞过此ID
     * @return
     */
    @GetMapping("/getLikedStatus/{likedUserId}")
    @ResponseBody
    public Object getLikedStatus(@PathVariable(value = "likedUserId")String likedUserId, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            return JsonData.buildError("您还未登陆",200);
        }
        Integer likedStatus = redisService.getLikedStatus(likedUserId, String.valueOf(user.getId()));
        if (likedStatus == null || likedStatus.equals(0)){
            return JsonData.buildSuccess("还没有进行过点赞", 404);
        }
        return JsonData.buildSuccess("已经点赞了", 666);

    }

    @GetMapping("/getLikedData")
    @ResponseBody
    public Object getLikedData(){
        return redisService.getLikedDataFromRedis();
    }
}