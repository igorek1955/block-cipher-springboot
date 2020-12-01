package ru.cipherapp.models;

import lombok.Data;

import javax.persistence.*;


/*
model for storing and working with user items with persistence to database
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Catalog catalog;

    private String name;
}
