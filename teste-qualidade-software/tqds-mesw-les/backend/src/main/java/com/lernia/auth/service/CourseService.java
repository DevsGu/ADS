package com.lernia.auth.service;

import com.lernia.auth.dto.AreaOfStudyDTO;
import com.lernia.auth.dto.CourseDTO;
import com.lernia.auth.dto.LocationDTO;
import com.lernia.auth.dto.UniversityDTOLight;
import com.lernia.auth.dto.filter.CourseFilter;
import com.lernia.auth.entity.AreaOfStudyEntity;
import com.lernia.auth.entity.CourseEntity;
import com.lernia.auth.entity.CurricularUnitEntity;
import com.lernia.auth.entity.LocationEntity;
import com.lernia.auth.entity.UniversityEntity;
import com.lernia.auth.repository.CourseRepository;
import com.lernia.auth.repository.CourseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    public List<String> getAllLanguages() {
        return courseRepository.findDistinctLanguages();
    }

    public Optional<CourseDTO> getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Page<CourseDTO> getCourses(CourseFilter filter, Pageable pageable) {
        Specification<CourseEntity> spec = CourseSpecification.filter(filter);
        return courseRepository.findAll(spec, pageable)
                .map(this::convertToDTO);
    }

    private CourseDTO convertToDTO(CourseEntity course) {
        UniversityDTOLight universityDTO = getUniversityDTOLight(course.getUniversity());

        List<AreaOfStudyDTO> areasOfStudy = Optional.ofNullable(course.getAreasOfStudy())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::getAreaOfStudyDTO)
                .toList();

        List<String> topics = Optional.ofNullable(course.getCurricularUnits())
                .orElse(Collections.emptyList())
                .stream()
                .map(CurricularUnitEntity::getName)
                .distinct()
                .toList();

        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getCourseType(),
                course.getIsRemote(),
                course.getMinAdmissionGrade(),
                course.getCost(),
                course.getDuration(),
                course.getCredits(),
                course.getLanguage(),
                course.getStartDate(),
                course.getApplicationDeadline(),
                course.getWebsite(),
                course.getContactEmail(),
                universityDTO,
                areasOfStudy,
                topics
        );
    }

    private UniversityDTOLight getUniversityDTOLight(UniversityEntity university) {
        if (university == null) {
            return null;
        }

        LocationEntity location = university.getLocation();
        LocationDTO locationDTO = location != null ? new LocationDTO(
                location.getId(),
                location.getCity(),
                location.getCountry(),
                location.getCostOfLiving()
        ) : null;

        return new UniversityDTOLight(
                university.getId(),
                university.getName(),
                university.getDescription(),
                locationDTO
        );
    }

    private AreaOfStudyDTO getAreaOfStudyDTO(AreaOfStudyEntity areaOfStudy) {
        if (areaOfStudy == null) {
            return null;
        }
        return new AreaOfStudyDTO(
                areaOfStudy.getId(),
                areaOfStudy.getName()
        );
    }
}