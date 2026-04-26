package com.spring.job_tracker.models.statuses;

import com.spring.job_tracker.models.enums.EContactPersonRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact_person_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactPersonRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EContactPersonRole contactPersonRole;
}
