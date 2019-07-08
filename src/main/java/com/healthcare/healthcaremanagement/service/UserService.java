package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.UserDto;
import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.entity.User;
import com.healthcare.healthcaremanagement.enumerator.Gender;
import com.healthcare.healthcaremanagement.enumerator.UserRole;
import com.healthcare.healthcaremanagement.exception.AccessDeniedException;
import com.healthcare.healthcaremanagement.exception.EmailAlreadyExistOnDatabaseException;
import com.healthcare.healthcaremanagement.exception.InvalidGenderException;
import com.healthcare.healthcaremanagement.exception.InvalidRoleException;
import com.healthcare.healthcaremanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private InstitutionService institutionService;

    public List<User> findAllUsers() {
        validateUserRole(UserRole.ADMIN.getRole());
        return userRepository.findAll();
    }

    public User createUser(final UserDto userDto) {
        log.info("Create user");
        validateUserRole(UserRole.ADMIN.getRole());
        validateUserAlreadyExistOnDatabase(userDto);
        validateRole(userDto.getRole());
        if (StringUtils.equals(userDto.getRole(), UserRole.ADMIN.getRole())) {
            return userRepository.save(new User(userDto.getEmail(), userDto.getPassword(), userDto.getRole()));
        }else{
            final Institution institution = institutionService.findByCNPJ(userDto.getCnpj());
            return userRepository.save(new User(userDto.getEmail(), userDto.getPassword(), userDto.getRole(), institution));
        }
    }

    public User findUserByEmail(final String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public User findUserByEmailAndPassword(final String userEmail, final String userPassword) {
        return userRepository.findByEmailAndPassword(userEmail, userPassword);
    }

    public void validateUserAccess(final Institution institution) {
        log.info("Validate user access");
        final User user = getUserInAuthenticationContext();
        if (!StringUtils.equals(institution.getCnpj(), user.getInstitution().getCnpj())) {
            log.error("Access not allowed for user with email: {} with institution with cpj: {}", user.getEmail(), institution.getCnpj());
            throw new AccessDeniedException();
        }
    }

    public void validateUserRole(final String accessRole) {
        final User user = getUserInAuthenticationContext();
        if (!StringUtils.equals(user.getRole(), accessRole)) {
            log.error("User does not have necessary role for this operation, current role: {} and role necessary for this" +
                    "operation: {}", user.getRole(), accessRole);
            throw new AccessDeniedException();
        }
    }

    private User getUserInAuthenticationContext() {
        return findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    private void validateUserAlreadyExistOnDatabase(final UserDto userDto) {
        log.info("Validate if user exist in database");
        if (Objects.nonNull(userRepository.findByEmail(userDto.getEmail()))) {
            log.error("Already exist a user in database with this e-mail: {}", userDto.getEmail());
            throw new EmailAlreadyExistOnDatabaseException();
        }
    }

    private void validateRole(final String role) {
        if (!EnumUtils.isValidEnum(Gender.class, role)) {
            log.error("Invalid role value {}", role);
            throw new InvalidRoleException();
        }
    }
}
