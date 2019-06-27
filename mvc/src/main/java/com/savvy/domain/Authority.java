package com.savvy.domain;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import static javax.persistence.GenerationType.SEQUENCE;


@Entity
@Table(name="authorities")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="authorities_id_seq")
    @SequenceGenerator(name="authorities_id_seq", sequenceName="authorities_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "role_type")
    private String authorityRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Authority(User user, String authorityRole){
        this.user = user;
        this.authorityRole = authorityRole;
    }

    public Long getId() {
        return id;
    }

    public String getAuthorityRole() {
        return this.authorityRole;
    }

    public User getUser() {
        return this.user;
    }

    public void setAuthorityRole(String authorityRole) {
        this.authorityRole = authorityRole;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Authority e = (Authority) other;

        return this.getId().equals(e.getId());
    }

    @Override
    public String getAuthority() {
        return this.authorityRole;
    }
}
