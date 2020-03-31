/**
 * FileName: LoginInterceptor
 * Author:   xjh
 * Date:     2019-08-15 15:23
 * Description: 实现三天免登陆效果拦截器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.interceptor;

import com.seagold.community.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉<br> 
 * 〈实现三天免登陆效果拦截器〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private  RedisTemplate<Object,Object> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User)request.getSession().getAttribute("user");

        if (user != null){
            Long id = user.getId();
            System.out.println("登录用户id"+id);
        }

        /**
         *  redis封禁账号列表查询
         */

        request.getSession().setAttribute("error", "账号已被封禁");
        response.sendRedirect("error");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}