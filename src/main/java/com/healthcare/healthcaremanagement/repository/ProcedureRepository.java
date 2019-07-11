package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends CrudRepository<Procedure, Integer> {

    Procedure findByName(String name);
}
