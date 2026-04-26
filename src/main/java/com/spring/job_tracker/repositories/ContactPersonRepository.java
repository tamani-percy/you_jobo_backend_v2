package com.spring.job_tracker.repositories;

import com.spring.job_tracker.models.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {

    Optional<List<ContactPerson>> getAllByJobApplicationId(Long jobApplicationId);
}
