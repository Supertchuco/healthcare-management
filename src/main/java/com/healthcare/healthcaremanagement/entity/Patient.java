package com.healthcare.healthcaremanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "Patient")
@Table(name = "Patient")
@AllArgsConstructor
public class Patient {

    @Id
    @Column
    private String cpf;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private String gender;
}
