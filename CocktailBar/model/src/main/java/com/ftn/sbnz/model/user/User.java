package com.ftn.sbnz.model.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
