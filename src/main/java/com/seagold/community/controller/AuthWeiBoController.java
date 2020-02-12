/**
 * FileName: HelloController
 * Author:   xjh
 * Date:     2019-08-14 18:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.alibaba.fastjson.JSON;
import com.seagold.community.dto.WeiBoUser;
import com.seagold.community.service.UserService;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeiboRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈微博授权登陆〉
 *
 * @author xjh
 * @create 2019-08-14
 * @since 1.0.0
 */
@Controller
@RequestMapping("/oauth")
public class AuthWeiBoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public String login(AuthCallback callback,
                        HttpServletResponse response,
                        HttpSession session) {
        AuthRequest authRequest = getAuthRequest();
        AuthResponse login = authRequest.login(callback);
        String sinaUser  = JSON.toJSON(login.getData()).toString();
        WeiBoUser weiBoUser = JSON.parseObject(sinaUser, WeiBoUser.class);
        int code = userService.weiboUser(weiBoUser, response, session);
        if (200 == code){
            return "redirect:/";
        }
        session.setAttribute("message", "微博授权登陆失败请重试");
        return "error";
    }

    @RequestMapping("/revoke/{token}")
    public Object revokeAuth(@PathVariable("token") String token) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.revoke(AuthToken.builder().accessToken(token).build());
    }

    private AuthRequest getAuthRequest() {
        return new AuthWeiboRequest(AuthConfig.builder()
                .clientId("4090079937")
                .clientSecret("b526d88ccacdfddf64bdec0195b035d0")
                .redirectUri("http://seagold.top/oauth/callback")
                .build());
    }

}