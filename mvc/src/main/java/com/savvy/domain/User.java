package com.savvy.domain;


import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;
    @Column
    private String email;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "username")
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "host", cascade = CascadeType.ALL)
    private List<Event> events;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "src_id", cascade = CascadeType.DETACH)
//    private List<User> friends;

    public Long getId() {
        return this.id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public List<Event> getEvents() {
        return this.events;
    }
}
