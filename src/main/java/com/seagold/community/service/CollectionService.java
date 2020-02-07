package com.seagold.community.service;

import com.seagold.community.entity.Collection;

public interface CollectionService {
    void collect(Collection collection);

    Collection isCollect(Collection collection);
}
