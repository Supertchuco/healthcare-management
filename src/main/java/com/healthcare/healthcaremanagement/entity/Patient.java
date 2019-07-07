package com.healthcare.healthcaremanagement.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity(name = "Patient")
@Table(name = "Patient")
public class Patient implements Serializable {

    @Id
    @Column
    private String cpf;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private String gender;

    public Patient(final String cpf, final String name, final int age, final String gender) {
        this.cpf = cpf;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
