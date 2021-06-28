package com.example.application.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "contact")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mobile;
    private String home;
    private String job;
    private String fax;

    @OneToOne
    private Contact contact;
}
