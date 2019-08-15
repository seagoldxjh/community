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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("进入拦截器");

        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return true;
        }
        for (Cookie cookie : cookies) {
            if("token".equals(cookie.getName())){
                String token = cookie.getValue();
                Object o = redisTemplate.opsForValue().get(token);
                System.out.println(o);
                if(o != null){
                    request.getSession().setAttribute("user", o);
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