package com.healthcare.healthcaremanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "Procedure")
@Table(name = "Procedure")
@AllArgsConstructor
public class Procedure {

    @Id
    @Column
    private String name;
}
