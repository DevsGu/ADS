package com.lernia.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "campuses", schema = "lernia")
@Getter
@Setter
@NoArgsConstructor
public class CampusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private UniversityEntity university;

    @Column(nullable = false)
    private String name;

    private String description;
    private String country;
    private String city;
    private Integer capacity;
}