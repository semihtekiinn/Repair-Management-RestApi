package com.works.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rid;

    private String name;

    @ManyToMany(mappedBy = "roles")
    List<User> users ;

}
