package com.savvy.repository;

import com.savvy.domain.Event;

import java.util.List;

public interface EventDao extends CRUDDao<Event, Long> {
    List<Event> findByUserId(Long userId);
}
