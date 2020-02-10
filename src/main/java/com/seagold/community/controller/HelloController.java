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


    @RequestMapping("/test")
    @ResponseBody
    public Object add(Long id){
        return null;


    }

}