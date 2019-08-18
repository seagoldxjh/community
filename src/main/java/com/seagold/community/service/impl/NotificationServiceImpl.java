/**
 * FileName: NotificationService
 * Author:   xjh
 * Date:     2019-08-18 14:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seagold.community.dto.NotificationUserDTO;
import com.seagold.community.entity.Notification;
import com.seagold.community.entity.User;
import com.seagold.community.enums.NotificationStatusEnum;
import com.seagold.community.enums.NotificationTypeEnum;
import com.seagold.community.mapper.NotificationMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-18
 * @since 1.0.0
 */
@Service
public class NotificationServiceImpl implements com.seagold.community.service.NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public List<NotificationUserDTO> findAllById(Long id){
        List<NotificationUserDTO> notificationUserDTOS = new ArrayList<>();
        List<Notification> notifications = notificationMapper.selectList(new QueryWrapper<Notification>().eq("receiver", id).orderByAsc("status"));

        if(notifications==null || notifications.size()==0){
            return notificationUserDTOS;
        }

        for (Notification notification : notifications) {
            NotificationUserDTO notificationDTO = new NotificationUserDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationUserDTOS.add(notificationDTO);
        }

        return notificationUserDTOS;
    }

    @Override
    public Integer unreadCount(Long id) {
        return notificationMapper.selectCount(new QueryWrapper<Notification>().eq("receiver", id).eq("status", NotificationStatusEnum.UNREAD));
    }

    @Override
    public NotificationUserDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if(notification == null){
            return null;
        }
        if(!notification.getReceiver().equals(user.getId())){
            return null;
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateById(notification);

        NotificationUserDTO notificationDTO = new NotificationUserDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}