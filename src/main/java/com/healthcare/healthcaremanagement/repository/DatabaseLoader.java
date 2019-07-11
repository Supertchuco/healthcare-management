package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.entity.User;
import com.healthcare.healthcaremanagement.enumerator.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final InstitutionRepository institutionRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepo, InstitutionRepository institutionRepo) {
        this.userRepository = userRepo;
        this.institutionRepository = institutionRepo;
    }

    @Override
    public void run(String... strings) {

        final Institution institution = new Institution("25690845632", "Balada Institution");
        institutionRepository.save(institution);

        final User user = new User("test", "test", UserRole.ADMIN.getRole());
        institution.setUsers(Arrays.asList(user));
        user.setInstitution(institution);

        this.userRepository.save(user);
    }

}
