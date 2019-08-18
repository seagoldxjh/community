package com.seagold.community.service;

import com.seagold.community.dto.NotificationUserDTO;
import com.seagold.community.entity.User;

import java.util.List;

public interface NotificationService {
    /**
     * 获取id用户的所有通知
     * @param id
     * @return
     */
    List<NotificationUserDTO> findAllById(Long id);

    /**
     * 获取用的所有未读通知数
     * @param id
     * @return
     */
    Integer unreadCount(Long id);

    NotificationUserDTO read(Long id, User user);
}
