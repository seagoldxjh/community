/**
 * FileName: UserService
 * Author:   xjh
 * Date:     2019-08-15 14:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.service;

import com.seagold.community.dto.QQUser;
import com.seagold.community.dto.WeiBoUser;
import com.seagold.community.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
public interface UserService {
    void insert(User user);
    List<User> findAll();
    void autoLogin(User user);
    User findByAccountId(String id);
    void updateById(User user);
    void logout(HttpServletRequest request, HttpServletResponse response);
    int weiboUser(WeiBoUser weiBoUser,HttpServletResponse response,
                  HttpSession session);
    int qqUser(QQUser qqUser, HttpServletResponse response,
               HttpSession session);
}