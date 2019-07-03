package com.healthcare.healthcaremanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Exam")
@Table(name = "Exam")
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "name", nullable = false)
    private Procedure procedure;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "cpf", nullable = false)
    private Patient patient;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "crm", nullable = false)
    private Physician physician;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "cnpj", nullable = false)
    private HealthcareInstitution healthcareInstitution;

    private boolean retrieved;

    public Exam(final Procedure procedure, final Patient patient, final Physician physician, final HealthcareInstitution healthcareInstitution,
                final boolean retrieved) {
        this.procedure = procedure;
        this.patient = patient;
        this.physician = physician;
        this.healthcareInstitution = healthcareInstitution;
        this.retrieved = retrieved;
    }
}
