package com.savvy.domain;

import javax.persistence.*;
import java.time.OffsetDateTime;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "photos_id_seq")
    @SequenceGenerator(name = "photos_id_seq", sequenceName = "photos_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "s3_key")
    private String s3Key;

    @Column(name = "create_date_time")
    private OffsetDateTime createDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    public Photo() {
    }

    public Photo(String name, String s3Key, User owner) {
        this.name = name;
        this.s3Key = s3Key;
        this.owner = owner;
        this.createDateTime = OffsetDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public void setCreateDateTime(OffsetDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getS3Key() {
        return this.s3Key;
    }

    public OffsetDateTime getCreateDateTime() {
        return this.createDateTime;
    }

    public User getOwner() {
        return this.owner;
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

        if (!(other instanceof Photo)) {
            return false;
        }

        Photo u = (Photo) other;

        return this.getId().equals(u.getId());
    }
}
