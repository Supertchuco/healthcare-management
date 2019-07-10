package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    Patient findByCpf(String cpf);
}
