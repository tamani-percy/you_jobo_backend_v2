package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.enums.EContactPersonRole;
import com.spring.job_tracker.models.statuses.ContactPersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactPersonRoleRepository extends JpaRepository<ContactPersonRole, Long> {

    Optional<ContactPersonRole> findByContactPersonRole(EContactPersonRole contactPersonRole);
}
