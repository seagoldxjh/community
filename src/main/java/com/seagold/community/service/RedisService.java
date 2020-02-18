package com.seagold.community.service;

import com.seagold.community.dto.LikedCountDTO;
import com.seagold.community.entity.UserLike;

import java.util.List;

public interface RedisService {

    /**
     * 点赞
     * @param likedUserId 被点赞者
     * @param likedPostId 点赞发起人
     */
    void saveLikedRedis(String likedUserId,String likedPostId);

    /**
     * 取消点赞
     * @param likedUserId 被点赞者
     * @param likedPostId 点赞发起人
     */
    void unlikeFromRedis(String likedUserId,String likedPostId);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId 被点赞者
     * @param likedPostId 点赞发起人
     */
    void deleteLikedFromRedis(String likedUserId,String likedPostId);

    /**
     * 被点赞数加一
     * @param likedUserId 被点赞者
     */
    void incrementLikedCount(String likedUserId);

    /**
     * 被点赞数减一
     * @param likedUserId 被点赞者
     */
    void decrementLikedCount(String likedUserId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<LikedCountDTO> getLikedCountFromRedis();

    /**
     * 获取两者之间是否含有点赞关系
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    Integer getLikedStatus(String likedUserId,String likedPostId);

    /**
     * 获取某个id的点赞总数量
     * @param likedUserId
     * @return
     */
    Integer getLikedCount(String likedUserId);

}
