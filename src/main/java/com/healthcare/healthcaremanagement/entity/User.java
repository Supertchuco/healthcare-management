package com.healthcare.healthcaremanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "User")
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String role;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "cnpj", nullable = true)
    private Institution institution;

    public User(final String email, final String password, final String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
