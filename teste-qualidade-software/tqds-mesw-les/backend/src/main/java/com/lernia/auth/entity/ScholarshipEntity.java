package com.lernia.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lernia.auth.entity.enums.CourseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scholarships", schema = "lernia")
@Getter
@Setter
@NoArgsConstructor
public class ScholarshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private Integer amount;

    @Column(name = "course_type")
    private String courseType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private UniversityEntity university;

    @PostLoad
    @PrePersist
    @PreUpdate
    private void validateCourseType() {
        if (!CourseType.contains(courseType)) {
            throw new IllegalArgumentException("Invalid courseType: " + courseType);
        }
    }
}