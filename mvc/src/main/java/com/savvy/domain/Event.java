package com.savvy.domain;

import javax.persistence.*;
import java.time.OffsetDateTime;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "events_id_seq")
    @SequenceGenerator(name = "events_id_seq", sequenceName = "events_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "event_name")
    private String eventName;
    @Column
    private String dest;
    @Column(name = "dest_id")
    private String destId;
    @Column(name = "start_date_time")
    private OffsetDateTime startDateTime;
    @Column(name = "end_date_time")
    private OffsetDateTime endDateTime;
//    @Column(name = "participants")
//    private String[] participants;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private User host;
    // must we store all the host information in every event??

    //TODO: event constructor
    public Event() {

    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(OffsetDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Long getId () {
        return this.id;
    }
    public OffsetDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public OffsetDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getDest() {
        return this.dest;
    }

    public String getDestId() {
        return this.destId;
    }

    public User getHost() {
        return this.host;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + this.id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Event e = (Event) other;

        return this.getId().equals(e.getId());
    }
}
