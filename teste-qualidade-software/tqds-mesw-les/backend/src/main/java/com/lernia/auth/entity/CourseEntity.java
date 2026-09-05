package com.lernia.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses", schema = "lernia")
@Getter
@Setter
@NoArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String courseType;

    private Boolean isRemote;
    private Integer minAdmissionGrade;
    private Integer cost;

    private Integer duration;
    private Integer credits;
    private String language;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    @Column(name = "contact_email")
    private String contactEmail;

    private String website;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private UniversityEntity university;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurricularUnitEntity> curricularUnits = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "course_area_of_study",
            schema = "lernia",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "area_of_study_id"))
    private List<AreaOfStudyEntity> areasOfStudy = new ArrayList<>();
}
