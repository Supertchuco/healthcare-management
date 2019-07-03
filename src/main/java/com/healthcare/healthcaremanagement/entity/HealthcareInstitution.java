package com.healthcare.healthcaremanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "HealthcareInstitution")
@Table(name = "HealthcareInstitution")
@AllArgsConstructor
public class HealthcareInstitution {

    @Id
    @Column
    private String cnpj;

    @Column
    private String name;

    @Column
    private int pixeon;

    @OneToMany(mappedBy = "healthcareInstitution", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Exam> exames;

    @OneToMany(mappedBy = "healthcareInstitution", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<User> users;

    public HealthcareInstitution(final String cnpj, final String name) {
        this.cnpj = cnpj;
        this.name = name;
        this.pixeon = 20;
    }
}
