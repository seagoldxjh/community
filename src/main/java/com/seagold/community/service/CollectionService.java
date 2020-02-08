package com.seagold.community.service;

import com.seagold.community.dto.CollectsDTO;
import com.seagold.community.entity.Collection;

import java.util.List;

public interface CollectionService {
    void collect(Collection collection);

    Collection isCollect(Collection collection);

    List<CollectsDTO> collects(Long userId);
}
