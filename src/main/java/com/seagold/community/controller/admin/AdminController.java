/**
 * FileName: adminController
 * Author:   xjh
 * Date:     2020-03-01 19:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2020-03-01
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/report")
    public String page(){
        return "admin";
    }
}