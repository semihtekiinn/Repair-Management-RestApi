package com.works.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;

    private String name;
    private String surname;
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "rid"))
    private List<Role> roles;
}
