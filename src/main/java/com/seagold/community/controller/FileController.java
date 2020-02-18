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
import com.seagold.community.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    @Autowired
    private RedisServiceImpl redisService;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO fileUpload(@RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request) throws IOException {

        String path = request.getSession().getServletContext().getRealPath("upload");
        String newFileName = "";
        if(file.getBytes().length > 0) {
            String fileName  = file.getOriginalFilename();
            newFileName = UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
            //在指定上传存储目录中创建新文件
            File targetFile = new File(path,newFileName);
            //找不到指定上传图片存储目录，就新创建此目录
            if(!targetFile.exists()) {
                targetFile.mkdirs();
            }
            //将文件写入硬盘
            file.transferTo(targetFile);
            //将上传后的文件路径传到view
            //model.addAttribute("fileUrl", request.getContextPath()+"/upload"+newFileName);
            System.out.println(request.getContextPath()+"/upload/"+newFileName);
        }

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        //fileDTO.setUrl("/images/default-avatar.png");
        fileDTO.setUrl(request.getContextPath()+"/upload/"+newFileName);
        return fileDTO;
    }

}