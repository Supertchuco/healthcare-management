package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.Institution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends CrudRepository<Institution, Integer> {

    Institution findByCnpj(String cnpj);
}
