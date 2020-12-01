package ru.cipherapp.models;


import lombok.Data;

import javax.persistence.*;

/*
model for storing and working with catalog items with persistence to database
 */
@Data
@Entity
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "operatingSystem")
    private String operatingSystem;

    @Column(name = "ip")
    private String ip;

    @Column(name = "password")
    private String password;

    @OneToOne
    private User user;

}
