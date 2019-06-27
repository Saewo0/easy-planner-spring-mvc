package com.savvy.repository;

import com.savvy.domain.Event;

public interface EventDao extends CRUDDao<Event, Long> {
    Event findByIdEager(Long id);
}
