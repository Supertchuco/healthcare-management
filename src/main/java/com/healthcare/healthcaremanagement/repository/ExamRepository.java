package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.Exam;
import org.springframework.data.repository.CrudRepository;

public interface ExamRepository extends CrudRepository<Exam, Integer> {

    Exam findById(int id);
}
