package com.lernia.auth.service;

import com.lernia.auth.dto.SuggestionResponseDTO;
import com.lernia.auth.entity.*;
import com.lernia.auth.repository.CourseRepository;
import com.lernia.auth.repository.UniversityRepository;
import com.lernia.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UniversityRepository universityRepository;

    @Transactional(readOnly = true)
    public List<SuggestionResponseDTO> getRecommendations(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        List<CourseEntity> bookmarkedCourses = Optional.ofNullable(user.getBookmarkedCourses()).orElseGet(Collections::emptyList);
        List<UniversityEntity> bookmarkedUniversities = Optional.ofNullable(user.getBookmarkedUniversities()).orElseGet(Collections::emptyList);
        String userLocation = user.getLocation();

        // Mapeamento das preferências do usuário em memória
        Set<Long> bookmarkedCourseIds = bookmarkedCourses.stream()
                .map(CourseEntity::getId)
                .collect(Collectors.toSet());

        Set<Long> bookmarkedUniversityIds = bookmarkedUniversities.stream()
                .map(UniversityEntity::getId)
                .collect(Collectors.toSet());

        Set<Long> preferredAreaIds = bookmarkedCourses.stream()
                .filter(c -> c.getAreasOfStudy() != null)
                .flatMap(c -> c.getAreasOfStudy().stream())
                .map(AreaOfStudyEntity::getId)
                .collect(Collectors.toSet());

        Set<String> preferredCourseTypes = bookmarkedCourses.stream()
                .map(CourseEntity::getCourseType)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<Long> preferredUniversityIds = bookmarkedCourses.stream()
                .filter(c -> c.getUniversity() != null)
                .map(c -> c.getUniversity().getId())
                .collect(Collectors.toSet());

        // Prepara os países das universidades favoritadas em memória (Elimina o problema N+1)
        Set<String> preferredCountries = bookmarkedUniversities.stream()
                .map(UniversityEntity::getLocation)
                .filter(Objects::nonNull)
                .map(LocationEntity::getCountry)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<ScoredItem> scoredItems = new ArrayList<>();

        // Avalia cursos não favoritados
        for (CourseEntity course : courseRepository.findAll()) {
            if (bookmarkedCourseIds.contains(course.getId())) {
                continue;
            }
            double score = calculateCourseScore(course, preferredAreaIds, preferredCourseTypes, preferredUniversityIds, userLocation);
            if (score > 0) {
                scoredItems.add(new ScoredItem(course.getId(), "course", score, course));
            }
        }

        // Avalia universidades não favoritadas
        for (UniversityEntity university : universityRepository.findAll()) {
            if (bookmarkedUniversityIds.contains(university.getId())) {
                continue;
            }
            double score = calculateUniversityScore(university, preferredCountries, userLocation);
            if (score > 0) {
                scoredItems.add(new ScoredItem(university.getId(), "university", score, university));
            }
        }

        // Fallback para itens populares caso o usuário não possua recomendações por score
        if (scoredItems.isEmpty() || preferredAreaIds.isEmpty()) {
            return getPopularItems();
        }

        // Ordena por score decrescente e limita às 10 melhores sugestões
        return scoredItems.stream()
                .sorted(Comparator.comparingDouble((ScoredItem item) -> item.score).reversed())
                .limit(10)
                .map(this::toDTO)
                .toList();
    }

    private double calculateCourseScore(CourseEntity course,
                                       Set<Long> preferredAreaIds,
                                       Set<String> preferredCourseTypes,
                                       Set<Long> preferredUniversityIds,
                                       String userLocation) {
        double score = 0;

        // Correspondência de Área de Estudo (+30)
        if (course.getAreasOfStudy() != null) {
            Set<Long> courseAreaIds = course.getAreasOfStudy().stream()
                    .map(AreaOfStudyEntity::getId)
                    .collect(Collectors.toSet());
            if (!Collections.disjoint(courseAreaIds, preferredAreaIds)) {
                score += 30;
            }
        }

        // Correspondência de Localização/País (+25)
        if (userLocation != null && course.getUniversity() != null && course.getUniversity().getLocation() != null) {
            String courseCountry = course.getUniversity().getLocation().getCountry();
            if (courseCountry != null && isLocationMatching(userLocation, courseCountry)) {
                score += 25;
            }
        }

        // Correspondência do Tipo de Curso (+20)
        if (course.getCourseType() != null && preferredCourseTypes.contains(course.getCourseType())) {
            score += 20;
        }

        // Conexão com Universidade preferida (+15)
        if (course.getUniversity() != null && preferredUniversityIds.contains(course.getUniversity().getId())) {
            score += 15;
        }

        // Bônus para cursos remotos/EAD (+5)
        if (Boolean.TRUE.equals(course.getIsRemote())) {
            score += 5;
        }

        return score;
    }

    private double calculateUniversityScore(UniversityEntity university,
                                          Set<String> preferredCountries,
                                          String userLocation) {
        double score = 0;

        if (university.getLocation() != null && university.getLocation().getCountry() != null) {
            String country = university.getLocation().getCountry();

            // Match de localização com o perfil do usuário (+25)
            if (userLocation != null && isLocationMatching(userLocation, country)) {
                score += 25;
            }

            // Match com países de universidades previamente favoritadas (+20)
            if (preferredCountries.contains(country)) {
                score += 20;
            }
        }

        return score;
    }

    private boolean isLocationMatching(String loc1, String loc2) {
        String l1 = loc1.toLowerCase();
        String l2 = loc2.toLowerCase();
        return l1.contains(l2) || l2.contains(l1);
    }

    private List<SuggestionResponseDTO> getPopularItems() {
        List<SuggestionResponseDTO> popular = new ArrayList<>();

        courseRepository.findAll().stream()
                .limit(5)
                .map(course -> mapToDTO(course.getId(), course.getName(), course.getDescription(),
                        "course", 50.0, course.getCourseType(), course.getUniversity()))
                .forEach(popular::add);

        universityRepository.findAll().stream()
                .limit(5)
                .map(uni -> mapToDTO(uni.getId(), uni.getName(), uni.getDescription(),
                        "university", 50.0, null, uni))
                .forEach(popular::add);

        return popular;
    }

    private SuggestionResponseDTO toDTO(ScoredItem item) {
        if ("course".equals(item.type)) {
            CourseEntity course = (CourseEntity) item.entity;
            return mapToDTO(course.getId(), course.getName(), course.getDescription(),
                    "course", Math.min(item.score, 100), course.getCourseType(), course.getUniversity());
        } else {
            UniversityEntity university = (UniversityEntity) item.entity;
            return mapToDTO(university.getId(), university.getName(), university.getDescription(),
                    "university", Math.min(item.score, 100), null, university);
        }
    }

    private SuggestionResponseDTO mapToDTO(Long id, String title, String description, String type,
                                          double matchScore, String courseType, Object entity) {
        SuggestionResponseDTO dto = new SuggestionResponseDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setType(type);
        dto.setMatchScore(matchScore);
        dto.setCourseType(courseType);

        if (entity instanceof UniversityEntity uni) {
            dto.setUniversityName(uni.getName());
            if (uni.getLocation() != null) {
                dto.setLocation(uni.getLocation().getCountry());
            }
        } else if (entity instanceof CourseEntity course && course.getUniversity() != null) {
            dto.setUniversityName(course.getUniversity().getName());
            if (course.getUniversity().getLocation() != null) {
                dto.setLocation(course.getUniversity().getLocation().getCountry());
            }
        }

        return dto;
    }

    private record ScoredItem(Long id, String type, double score, Object entity) {}
}