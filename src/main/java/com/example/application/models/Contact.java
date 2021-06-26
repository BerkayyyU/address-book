package com.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String company;

    @OneToOne(cascade = CascadeType.ALL)// değişiklik olduğunda tüm alt değişiklikleri düzenle
    private Phone phone;


}
