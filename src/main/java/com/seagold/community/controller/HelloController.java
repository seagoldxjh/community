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

import com.seagold.community.entity.User;
import com.seagold.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-14
 * @since 1.0.0
 */
@Controller
public class HelloController {
    @Autowired
    UserMapper userMapper;



    @RequestMapping("/test")
    @ResponseBody
    public void add(){
        User user = new User();
        user.setAccountId("1610501340");
        user.setName("admin");
        userMapper.insert(user);
    }
}