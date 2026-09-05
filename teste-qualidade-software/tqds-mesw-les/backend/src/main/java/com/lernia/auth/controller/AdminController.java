package com.lernia.auth.controller;

import com.lernia.auth.dto.AnalyticsDTO;
import com.lernia.auth.dto.CourseLightDTO;
import com.lernia.auth.dto.LocationDTO;
import com.lernia.auth.dto.UniversityDTOLight;
import com.lernia.auth.dto.ReviewDTO;
import com.lernia.auth.dto.response.UserProfileResponse;
import com.lernia.auth.dto.UniversityDTO;
import com.lernia.auth.entity.CourseReviewEntity;
import com.lernia.auth.entity.UniversityReviewEntity;
import com.lernia.auth.entity.UniversityEntity;
import com.lernia.auth.entity.LocationEntity;
import com.lernia.auth.entity.UserEntity;
import com.lernia.auth.repository.CourseRepository;
import com.lernia.auth.repository.CourseReviewRepository;
import com.lernia.auth.repository.UniversityRepository;
import com.lernia.auth.repository.UniversityReviewRepository;
import com.lernia.auth.repository.UserRepository;
import com.lernia.auth.repository.LocationRepository;
import com.lernia.auth.service.AuthService;
import com.lernia.auth.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;
    private final CourseRepository courseRepository;
    private final LocationRepository locationRepository;
    private final AuthService authService;
    private final AnalyticsService analyticsService;
    private final CourseReviewRepository courseReviewRepository;
    private final UniversityReviewRepository universityReviewRepository;

    @GetMapping("/users")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        List<UserProfileResponse> users = userRepository.findAll().stream().map(u -> {
            UserProfileResponse r = new UserProfileResponse();
            r.setId(u.getId());
            r.setUsername(u.getUsername());
            r.setName(u.getName());
            r.setEmail(u.getEmail());
            r.setAge(u.getAge());
            r.setGender(u.getGender());
            r.setLocation(u.getLocation());
            r.setJobTitle(u.getJobTitle());
            r.setUserRole(u.getUserRole() != null ? u.getUserRole().name() : null);
            r.setPremium(u.getUserRole() != null && "PREMIUM".equals(u.getUserRole().name()));
            return r;
        }).toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/universities")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UniversityDTOLight>> getAllUniversities() {
        List<UniversityDTOLight> list = universityRepository.findAll().stream()
                .map(this::mapToUniversityDTOLight)
                .toList();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/universities")
    @Transactional
    public ResponseEntity<UniversityDTOLight> createUniversity(@RequestBody UniversityDTO dto) {
        if (dto == null || dto.getName() == null || dto.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        UniversityEntity entity = new UniversityEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setContactInfo(dto.getContactInfo());
        entity.setWebsite(dto.getWebsite());
        entity.setAddress(dto.getAddress());
        entity.setLogo(dto.getLogo());

        if (dto.getLocation() != null && dto.getLocation().getId() != null) {
            Optional<LocationEntity> loc = locationRepository.findById(dto.getLocation().getId());
            loc.ifPresent(entity::setLocation);
        } else {
            entity.setLocation(null);
        }

        UniversityEntity saved = universityRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToUniversityDTOLight(saved));
    }

    @PutMapping("/universities/{id}")
    @Transactional
    public ResponseEntity<UniversityDTOLight> updateUniversity(@PathVariable Long id, @RequestBody UniversityDTO dto) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<UniversityEntity> opt = universityRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UniversityEntity entity = opt.get();
        if (dto.getName() != null && !dto.getName().isBlank()) {
            entity.setName(dto.getName());
        }
        entity.setDescription(dto.getDescription());
        entity.setContactInfo(dto.getContactInfo());
        entity.setWebsite(dto.getWebsite());
        entity.setAddress(dto.getAddress());
        entity.setLogo(dto.getLogo());

        if (dto.getLocation() != null) {
            if (dto.getLocation().getId() != null) {
                locationRepository.findById(dto.getLocation().getId())
                        .ifPresent(entity::setLocation);
            } else {
                entity.setLocation(null);
            }
        }

        UniversityEntity saved = universityRepository.save(entity);
        return ResponseEntity.ok(mapToUniversityDTOLight(saved));
    }

    @DeleteMapping("/universities/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (!universityRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        boolean hasCourses = courseRepository.existsByUniversityId(id);
        if (hasCourses) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        universityRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/courses")
    @Transactional(readOnly = true)
    public ResponseEntity<List<CourseLightDTO>> getAllCourses() {
        List<CourseLightDTO> list = courseRepository.findAll().stream()
                .map(course -> new CourseLightDTO(
                        course.getId(),
                        course.getName(),
                        course.getCourseType(),
                        course.getUniversity() != null ? course.getUniversity().getName() : null,
                        course.getCost(),
                        course.getCredits(),
                        course.getDescription()))
                .toList();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String currentUsername = authentication.getName();
            if (currentUsername != null) {
                Optional<UserEntity> currentUser = userRepository.findByUsername(currentUsername);
                if (currentUser.isPresent() && currentUser.get().getId().equals(id)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            }
        }

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{id}/reset-password")
    @Transactional
    public ResponseEntity<Map<String, String>> resetUserPassword(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        try {
            authService.adminResetPassword(id);
            return ResponseEntity.ok(Map.of("message", "Password reset email sent successfully"));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/analytics")
    @Transactional(readOnly = true)
    public ResponseEntity<AnalyticsDTO> getAnalytics() {
        return ResponseEntity.ok(analyticsService.getAnalytics());
    }

    @GetMapping("/reviews")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> result = new ArrayList<>();

        for (CourseReviewEntity cr : courseReviewRepository.findAll()) {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(cr.getId());
            dto.setUserId(cr.getUser() != null ? cr.getUser().getId() : null);
            dto.setUserName(cr.getUser() != null ? cr.getUser().getName() : null);
            dto.setCourseId(cr.getCourse() != null ? cr.getCourse().getId() : null);
            dto.setUniversityId(null);
            dto.setRating(cr.getRating());
            dto.setTitle(cr.getTitle());
            dto.setDescription(cr.getDescription());
            dto.setReviewDate(cr.getReviewDate());
            result.add(dto);
        }

        for (UniversityReviewEntity ur : universityReviewRepository.findAll()) {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(ur.getId());
            dto.setUserId(ur.getUser() != null ? ur.getUser().getId() : null);
            dto.setUserName(ur.getUser() != null ? ur.getUser().getName() : null);
            dto.setCourseId(null);
            dto.setUniversityId(ur.getUniversity() != null ? ur.getUniversity().getId() : null);
            dto.setRating(ur.getRating());
            dto.setTitle(ur.getTitle());
            dto.setDescription(ur.getDescription());
            dto.setReviewDate(ur.getReviewDate());
            result.add(dto);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/reviews/course/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCourseReview(@PathVariable Long id) {
        if (!courseReviewRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        courseReviewRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reviews/university/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUniversityReview(@PathVariable Long id) {
        if (!universityReviewRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        universityReviewRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Auxiliar para evitar repetição do mapeamento de UniversityDTOLight
    private UniversityDTOLight mapToUniversityDTOLight(UniversityEntity university) {
        return new UniversityDTOLight(
                university.getId(),
                university.getName(),
                university.getDescription(),
                university.getLocation() != null ? new LocationDTO(
                        university.getLocation().getId(),
                        university.getLocation().getCity(),
                        university.getLocation().getCountry(),
                        university.getLocation().getCostOfLiving()) : null
        );
    }
}