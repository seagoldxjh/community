/**
 * FileName: RedisKeyUtils
 * Author:   xjh
 * Date:     2020-02-15 14:57
 * Description: 根据规则生成Key
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.utils;

/**
 * 〈一句话功能简述〉<br> 
 * 〈根据规则生成Key〉
 *
 * @author xjh
 * @create 2020-02-15
 * @since 1.0.0
 */
public class RedisKeyUtils {
    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    //保存用户被点赞数量的key
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param likedUserId 被点赞的人id
     * @param likedPostId 点赞的人的id
     * @return
     */
    public static String getLikedKey(String likedUserId, String likedPostId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(likedPostId);
        return builder.toString();
    }
}