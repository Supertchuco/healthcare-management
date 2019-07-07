package com.healthcare.healthcaremanagement.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "Exam")
@Table(name = "Exam")
@AllArgsConstructor
@NoArgsConstructor
public class Exam implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @OneToOne
    @JoinColumn(name = "name")
    @JsonManagedReference
    private Procedure procedure;

    @OneToOne
    @JoinColumn(name = "cpf")
    @JsonManagedReference
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "crm")
    @JsonManagedReference
    private Physician physician;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "cnpj")
    private Institution institution;

    private boolean retrieved;

    public Exam(final Procedure procedure, final Patient patient, final Physician physician, final Institution institution,
                final boolean retrieved) {
        this.procedure = procedure;
        this.patient = patient;
        this.physician = physician;
        this.institution = institution;
        this.retrieved = retrieved;
    }
}
