/**
 * FileName: FileController
 * Author:   xjh
 * Date:     2019-08-18 18:35
 * Description: 图片文件上传控制类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.seagold.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br> 
 * 〈图片文件上传控制类〉
 *
 * @author xjh
 * @create 2019-08-18
 * @since 1.0.0
 */
@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO fileUpload(/*@RequestParam("editormd-image-file") MultipartFile file*/){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/default-avatar.png");
        return fileDTO;
    }
}