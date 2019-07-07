package com.healthcare.healthcaremanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity(name = "Procedure")
@Table(name = "Procedure")
@AllArgsConstructor
public class Procedure implements Serializable {

    @Id
    @Column
    private String name;
}
