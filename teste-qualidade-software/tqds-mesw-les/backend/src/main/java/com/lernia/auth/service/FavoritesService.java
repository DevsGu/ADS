package com.lernia.auth.service;

import com.lernia.auth.dto.CourseLightDTO;
import com.lernia.auth.dto.LocationDTO;
import com.lernia.auth.dto.UniversityDTOLight;
import com.lernia.auth.dto.response.FavoritesResponse;
import com.lernia.auth.entity.CourseEntity;
import com.lernia.auth.entity.LocationEntity;
import com.lernia.auth.entity.UniversityEntity;
import com.lernia.auth.entity.UserEntity;
import com.lernia.auth.repository.CourseRepository;
import com.lernia.auth.repository.UniversityRepository;
import com.lernia.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UniversityRepository universityRepository;

    // ------------- COURSES -------------

    @Transactional
    public void addCourseToFavorites(Long userId, Long courseId) {
        UserEntity user = findUserById(userId);
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if (!user.getBookmarkedCourses().contains(course)) {
            user.getBookmarkedCourses().add(course);
        }
    }

    @Transactional
    public void removeCourseFromFavorites(Long userId, Long courseId) {
        UserEntity user = findUserById(userId);
        user.getBookmarkedCourses().removeIf(c -> c.getId().equals(courseId));
    }

    // ------------- UNIVERSITIES -------------

    @Transactional
    public void addUniversityToFavorites(Long userId, Long universityId) {
        UserEntity user = findUserById(userId);
        UniversityEntity uni = universityRepository.findById(universityId)
                .orElseThrow(() -> new EntityNotFoundException("University not found with id: " + universityId));

        if (!user.getBookmarkedUniversities().contains(uni)) {
            user.getBookmarkedUniversities().add(uni);
        }
    }

    @Transactional
    public void removeUniversityFromFavorites(Long userId, Long universityId) {
        UserEntity user = findUserById(userId);
        user.getBookmarkedUniversities().removeIf(u -> u.getId().equals(universityId));
    }

    // ------------- LISTAR FAVORITOS DO USER -------------

    @Transactional(readOnly = true)
    public FavoritesResponse getFavoritesForUser(Long userId) {
        UserEntity user = findUserById(userId);

        List<UniversityDTOLight> uniDtos = user.getBookmarkedUniversities().stream()
                .sorted(Comparator.comparing(UniversityEntity::getId).reversed())
                .map(this::toUniversityLight)
                .toList();

        List<CourseLightDTO> courseDtos = user.getBookmarkedCourses().stream()
                .sorted(Comparator.comparing(CourseEntity::getId).reversed())
                .map(this::toCourseLight)
                .toList();

        return new FavoritesResponse(uniDtos, courseDtos);
    }

    // ------------- HELPERS PRIVADOS DE DOMÍNIO E MAPEAMENTO -------------

    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    private UniversityDTOLight toUniversityLight(UniversityEntity university) {
        LocationDTO locationDTO = Optional.ofNullable(university.getLocation())
                .map(loc -> new LocationDTO(
                        loc.getId(),
                        loc.getCity(),
                        loc.getCountry(),
                        loc.getCostOfLiving()))
                .orElse(null);

        return new UniversityDTOLight(
                university.getId(),
                university.getName(),
                university.getDescription(),
                locationDTO);
    }

    private CourseLightDTO toCourseLight(CourseEntity course) {
        String universityName = Optional.ofNullable(course.getUniversity())
                .map(UniversityEntity::getName)
                .orElse(null);

        return new CourseLightDTO(
                course.getId(),
                course.getName(),
                course.getCourseType(),
                universityName,
                course.getCost(),
                course.getCredits(),
                course.getDescription());
    }
}