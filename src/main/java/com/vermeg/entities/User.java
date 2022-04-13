package com.vermeg.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String role;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private long salary;
    @Column
    private int age;

}
