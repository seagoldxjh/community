/**
 * FileName: AuthorizeController
 * Author:   xjh
 * Date:     2019-08-15 12:25
 * Description: 授权控制器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.seagold.community.dto.AccessTokenDTO;
import com.seagold.community.dto.GithubUser;
import com.seagold.community.entity.User;
import com.seagold.community.provider.GithubProvider;
import com.seagold.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br> 
 * 〈授权控制器〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.lient.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    /**
     * 1. 用户点击登陆按钮，请求github提供的authorize接口，需要提供的参数
     *    - client_id: github生成的
     *    - redirect_uri: 自己设置的回调接口地址
     *    - state=1
     *    - scope=user
     * 2. github自动跳转到我们设置的callback接口中，并且携带参数code
     * 3. 我们继续调用github提供的access_token接口，需要提供参数，将数据穿的的参数封装为AccessTokenDTO对象
     *    - Client_id: github生成的
     *    - Client_secret: github生成的
     *    - Code: github: 回调提供的参数
     *    - Redirect_uri: 自己设置的回调接口地址
     *    - State: 1
     *    注意请求url: https://github.com/login/oauth/access_token，post方式
     * 4. github响应请求返回access_token
     * 5. 利用access_token请求github提供的user接口
     *    https://api.github.com/user?access_token=xxxxx,Get方式
     * 6. 返回Git用户，存入数据，更新登陆状态
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpSession session){

        //封装github需要的参数
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //通过git返回的accessToken拿到用户信息
        GithubUser githubUser = githubProvider.getUser(accessToken);

        //拿到用户信息后封装自己的用户表用户
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setHeadImg(githubUser.getAvatar_url());
            String token = UUID.randomUUID().toString();
            user.setToken(token);

            //根据github用户id在自己的数据库中查找是否有该用户，若有则进行更新用户的name，头像信息
            //若无，则添加用户信息到数据库中
            User findUser = userService.findByAccountId(user.getAccountId());

            if(findUser == null){
                userService.insert(user);
            }else {
                user.setId(findUser.getId());
                userService.updateById(user);
            }
            session.setAttribute("user", user);
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60*60*24*3);
            response.addCookie(cookie);
            userService.autoLogin(user);
            return "redirect:/";
        } else {

            log.error("callback get github user error,{}",githubUser);
            return "redirect:/";
        }

    }

    /**
     * 用户退出登陆
     * 清除用户的本地cookie，及服务器端的session和redis中临时保存的用户数据
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        userService.logout(request, response);
        return "redirect:/";
    }
}