package com.spring.job_tracker.models.statuses;

import com.spring.job_tracker.models.enums.ESource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sources")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ESource source;
}
