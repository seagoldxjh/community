/**
 * FileName: CollectionServiceImpl
 * Author:   xjh
 * Date:     2020-02-07 13:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seagold.community.entity.Collection;
import com.seagold.community.mapper.CollectionMapper;
import com.seagold.community.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2020-02-07
 * @since 1.0.0
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public void collect(Collection collection) {
        collectionMapper.insert(collection);
    }

    @Override
    public Collection isCollect(Collection collection) {
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id",collection.getQuestionId());
        wrapper.eq("user_id", collection.getUserId());
        return collectionMapper.selectOne(wrapper);
    }
}