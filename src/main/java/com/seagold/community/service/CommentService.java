/**
 * FileName: CommentService
 * Author:   xjh
 * Date:     2019-08-17 12:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.service;

import com.seagold.community.dto.CommentDTO;
import com.seagold.community.dto.CommentUserDTO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-17
 * @since 1.0.0
 */
public interface CommentService {

    Object insert(CommentDTO commentDTO, HttpSession session);

    List<CommentUserDTO> findAllById(Long id, int val);
}