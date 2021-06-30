package com.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"user"})
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String company;
    private String mobilePhone;
    private String homePhone;
    private String jobPhone;
    private String faxPhone;
    private String homeAddress;
    private String jobAddress;
    private String otherAddress;
    private String facebook;
    private String twitter;

    @ManyToOne
    private User user;

}
