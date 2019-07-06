package com.healthcare.healthcaremanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "HealthCareInstitution")
@Table(name = "HealthCareInstitution")
@AllArgsConstructor
public class HealthCareInstitution {

    @Id
    @Column
    private String cnpj;

    @Column
    private String name;

    @Column
    private int pixeon;

    @JsonIgnore
    @OneToMany(mappedBy = "healthCareInstitution", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Exam> exames;

    @JsonIgnore
    @OneToMany(mappedBy = "healthCareInstitution", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<User> users;

    public HealthCareInstitution(final String cnpj, final String name) {
        this.cnpj = cnpj;
        this.name = name;
        this.pixeon = 20;
    }
}
