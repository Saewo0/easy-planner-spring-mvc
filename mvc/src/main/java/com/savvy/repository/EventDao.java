package com.savvy.repository;

import com.savvy.domain.Event;

import java.util.List;

public interface EventDao {
    Event save(Event event);

    List<Event> findAll();

    Event findByIdEager(Long id);

    Event findById(Long id);
}
