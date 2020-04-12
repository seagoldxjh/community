/**
 * FileName: UserMapper
 * Author:   xjh
 * Date:     2019-08-15 14:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagold.community.entity.User;

/**
 * 〈一句话功能简述〉<br> 
 * 〈对用户实体类进行操作的接口〉
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
public interface UserMapper extends BaseMapper<User> {
    int updateReportStatus(int id);

    int queryUserViolationCount(Long id);
}