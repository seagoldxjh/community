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
import com.seagold.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
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
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("进入拦截器");

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length==0){
            return true;
        }
        for (Cookie cookie : cookies) {
            if("token".equals(cookie.getName())){
                String token = cookie.getValue();
                User o = (User)redisTemplate.opsForValue().get(token);
                System.out.println(o);
                if(o != null){
                    request.getSession().setAttribute("user", o);
                    Integer unreadCount = notificationService.unreadCount(o.getId());
                    request.getSession().setAttribute("unreadCount",unreadCount);
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}