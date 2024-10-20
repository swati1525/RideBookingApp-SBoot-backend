package com.swati.project.uber.uberApp.entities;

import com.swati.project.uber.uberApp.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="app_user",  //user table already is being used by postgresql db so we have to change the table name
       indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
@Getter
@Setter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;

//    @Embedded      //"@Embedded" is a JPA annotation used in Spring Boot to map a composite object within another entity.
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
