/**
 * FileName: NotificationStatusEnum
 * Author:   xjh
 * Date:     2019-08-18 13:51
 * Description: 回复的状态吗，已读还是未读
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.enums;

/**
 * 〈一句话功能简述〉<br> 
 * 〈回复的状态吗，已读还是未读〉
 *
 * @author xjh
 * @create 2019-08-18
 * @since 1.0.0
 */
public enum  NotificationStatusEnum {
    /**
     * 0表示未读
     * 1表示已读
     */
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}