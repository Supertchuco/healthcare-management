package com.healthcare.healthcaremanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity(name = "Patient")
@Table(name = "Patient")
@NoArgsConstructor
@AllArgsConstructor
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
}
