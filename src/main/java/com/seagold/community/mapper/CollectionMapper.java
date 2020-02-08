package com.seagold.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagold.community.dto.CollectsDTO;
import com.seagold.community.entity.Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionMapper extends BaseMapper<Collection> {
    public List<CollectsDTO> collects(@Param(value = "userId") Long userId);
}
