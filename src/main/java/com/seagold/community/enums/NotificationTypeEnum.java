/**
 * FileName: NotificationTypeEnum
 * Author:   xjh
 * Date:     2019-08-18 13:49
 * Description: 回复的评论还是回复的问题type枚举类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.enums;

/**
 * 〈一句话功能简述〉<br> 
 * 〈回复的评论还是回复的问题type枚举类〉
 *
 * @author xjh
 * @create 2019-08-18
 * @since 1.0.0
 */
public enum  NotificationTypeEnum {
    /**
     * REPLY_QUESTION:"回复了问题"
     */
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论");
    private int type;
    private String name;


    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}