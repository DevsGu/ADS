package com.lernia.auth.dto.response;

import com.lernia.auth.entity.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String name;
    private String username;
    private String email;
    private Integer age;
    private Gender gender;
    private String location;
    private String jobTitle;
    private String userRole;
    private String provider;
    private String profilePicture;
    private LocalDate premiumStartDate;
    private boolean isPremium;

    // Academic profile
    private Integer academicGrade;
    private String educationLevel;
    private String studyArea;
}
