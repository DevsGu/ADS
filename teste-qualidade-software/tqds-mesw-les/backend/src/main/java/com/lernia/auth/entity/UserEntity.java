package com.lernia.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lernia.auth.entity.enums.AuthProvider;
import com.lernia.auth.entity.enums.EducationLevel;
import com.lernia.auth.entity.enums.Gender;
import com.lernia.auth.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", schema = "lernia")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String email;

    @JsonIgnore
    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider = AuthProvider.LOCAL;

    private String providerId;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "gender")
    private Gender gender;

    private String location;
    private String profilePicture;
    private String jobTitle;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "user_role")
    private UserRole userRole;

    @Column(name = "premium_start_date")
    private LocalDate premiumStartDate;

    private Integer academicGrade; // 0-200 scale (e.g., Portuguese grading)

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    private EducationLevel educationLevel;

    private String studyArea;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_bookmarked_courses",
            schema = "lernia",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<CourseEntity> bookmarkedCourses = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_bookmarked_universities",
            schema = "lernia",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "university_id")
    )
    private List<UniversityEntity> bookmarkedUniversities = new ArrayList<>();
}
