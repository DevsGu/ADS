package com.lernia.auth.service;

import com.lernia.auth.dto.ReviewDTO;
import com.lernia.auth.entity.CourseEntity;
import com.lernia.auth.entity.CourseReviewEntity;
import com.lernia.auth.entity.UniversityEntity;
import com.lernia.auth.entity.UniversityReviewEntity;
import com.lernia.auth.entity.UserEntity;
import com.lernia.auth.repository.CourseRepository;
import com.lernia.auth.repository.CourseReviewRepository;
import com.lernia.auth.repository.UniversityRepository;
import com.lernia.auth.repository.UniversityReviewRepository;
import com.lernia.auth.repository.UserCourseRepository;
import com.lernia.auth.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final UniversityReviewRepository reviewRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final UniversityRepository universityRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;

    public boolean canUserReview(Long userId, Long universityId) {
        return userCourseRepository.existsByUserIdAndCourse_UniversityId(userId, universityId);
    }

    public boolean canUserReviewCourse(Long userId, Long courseId) {
        return userCourseRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    public List<ReviewDTO> getReviewsByUniversity(Long universityId) {
        return reviewRepository.findByUniversityIdOrderByReviewDateDesc(universityId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByCourse(Long courseId) {
        return courseReviewRepository.findByCourseIdOrderByReviewDateDesc(courseId).stream()
                .map(this::convertCourseReviewToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        if (!canUserReview(reviewDTO.getUserId(), reviewDTO.getUniversityId())) {
            throw new IllegalStateException("User is not eligible to review this university.");
        }

        UniversityEntity university = universityRepository.findById(reviewDTO.getUniversityId())
                .orElseThrow(() -> new EntityNotFoundException("University not found with ID: " + reviewDTO.getUniversityId()));
        UserEntity user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + reviewDTO.getUserId()));

        UniversityReviewEntity review = new UniversityReviewEntity();
        review.setRating(reviewDTO.getRating());
        review.setTitle(reviewDTO.getTitle());
        review.setDescription(reviewDTO.getDescription());
        review.setReviewDate(LocalDate.now());
        review.setUniversity(university);
        review.setUser(user);

        UniversityReviewEntity savedReview = reviewRepository.save(review);
        return convertToDto(savedReview);
    }

    @Transactional
    public ReviewDTO addCourseReview(ReviewDTO reviewDTO) {
        if (!canUserReviewCourse(reviewDTO.getUserId(), reviewDTO.getCourseId())) {
            throw new IllegalStateException("User is not eligible to review this course.");
        }

        CourseEntity course = courseRepository.findById(reviewDTO.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + reviewDTO.getCourseId()));
        UserEntity user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + reviewDTO.getUserId()));

        CourseReviewEntity review = new CourseReviewEntity();
        review.setRating(reviewDTO.getRating());
        review.setTitle(reviewDTO.getTitle());
        review.setDescription(reviewDTO.getDescription());
        review.setReviewDate(LocalDate.now());
        review.setCourse(course);
        review.setUser(user);

        CourseReviewEntity savedReview = courseReviewRepository.save(review);
        return convertCourseReviewToDto(savedReview);
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        UniversityReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("University review not found with ID: " + reviewId));

        validateOwnership(review.getUser(), userId);

        reviewRepository.delete(review);
    }

    @Transactional
    public void deleteCourseReview(Long reviewId, Long userId) {
        CourseReviewEntity review = courseReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Course review not found with ID: " + reviewId));

        validateOwnership(review.getUser(), userId);

        courseReviewRepository.delete(review);
    }

    @Transactional
    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO, Long userId) {
        UniversityReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("University review not found with ID: " + reviewId));

        validateOwnership(review.getUser(), userId);

        review.setRating(reviewDTO.getRating());
        review.setTitle(reviewDTO.getTitle());
        review.setDescription(reviewDTO.getDescription());

        return convertToDto(review);
    }

    @Transactional
    public ReviewDTO updateCourseReview(Long reviewId, ReviewDTO reviewDTO, Long userId) {
        CourseReviewEntity review = courseReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Course review not found with ID: " + reviewId));

        validateOwnership(review.getUser(), userId);

        review.setRating(reviewDTO.getRating());
        review.setTitle(reviewDTO.getTitle());
        review.setDescription(reviewDTO.getDescription());

        return convertCourseReviewToDto(review);
    }

    private void validateOwnership(UserEntity reviewUser, Long userId) {
        if (reviewUser == null || !reviewUser.getId().equals(userId)) {
            throw new SecurityException("You are not authorized to modify this review.");
        }
    }

    private ReviewDTO convertToDto(UniversityReviewEntity review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setTitle(review.getTitle());
        dto.setDescription(review.getDescription());
        dto.setReviewDate(review.getReviewDate());

        Optional.ofNullable(review.getUser()).ifPresent(user -> {
            dto.setUserId(user.getId());
            dto.setUserName(user.getName());
        });

        Optional.ofNullable(review.getUniversity()).ifPresent(university -> 
            dto.setUniversityId(university.getId())
        );

        return dto;
    }

    private ReviewDTO convertCourseReviewToDto(CourseReviewEntity review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setTitle(review.getTitle());
        dto.setDescription(review.getDescription());
        dto.setReviewDate(review.getReviewDate());

        Optional.ofNullable(review.getUser()).ifPresent(user -> {
            dto.setUserId(user.getId());
            dto.setUserName(user.getName());
        });

        Optional.ofNullable(review.getCourse()).ifPresent(course -> 
            dto.setCourseId(course.getId())
        );

        return dto;
    }
}