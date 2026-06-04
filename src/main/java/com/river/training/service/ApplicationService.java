package com.river.training.service;

import com.river.training.dto.ApplicationRequest;
import com.river.training.dto.ApplicationResponse;
import com.river.training.dto.PagedResponse;
import com.river.training.dto.ReviewRequest;
import com.river.training.entity.Application;
import com.river.training.entity.ApplicationStatus;
import com.river.training.entity.Course;
import com.river.training.entity.Review;
import com.river.training.entity.User;
import com.river.training.exception.ApiException;
import com.river.training.repository.ApplicationRepository;
import com.river.training.repository.CourseRepository;
import com.river.training.repository.ReviewRepository;
import com.river.training.security.UserPrincipal;
import com.river.training.util.DateParser;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            CourseRepository courseRepository,
            ReviewRepository reviewRepository) {
        this.applicationRepository = applicationRepository;
        this.courseRepository = courseRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getMyApplications() {
        User user = currentUser();
        return applicationRepository.findByUserIdWithCourse(user.getId()).stream()
                .map(app -> ApplicationResponse.forUser(app, reviewRepository.existsByApplicationId(app.getId())))
                .toList();
    }

    @Transactional
    public ApplicationResponse createApplication(ApplicationRequest request) {
        User user = currentUser();
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Вид транспорта не найден"));

        Application application = new Application();
        application.setUser(user);
        application.setCourse(course);
        application.setStartDate(DateParser.parseDdMmYyyy(request.startDate()));
        application.setPaymentMethod(request.paymentMethod().trim());
        application.setStatus(ApplicationStatus.NEW);

        Application saved = applicationRepository.save(application);
        return ApplicationResponse.forUser(saved, false);
    }

    @Transactional
    public ApplicationResponse addReview(Long applicationId, ReviewRequest request) {
        User user = currentUser();
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Заявка не найдена"));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Нельзя оставить отзыв к чужой заявке");
        }
        if (!ApplicationStatus.COMPLETED.equals(application.getStatus())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Отзыв доступен только для завершённого обучения");
        }
        if (reviewRepository.existsByApplicationId(applicationId)) {
            throw new ApiException(HttpStatus.CONFLICT, "Отзыв по этой заявке уже оставлен");
        }

        Review review = new Review();
        review.setUser(user);
        review.setApplication(application);
        review.setReviewText(request.text().trim());
        reviewRepository.save(review);

        return ApplicationResponse.forUser(application, true);
    }

    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> getAllApplications(String status, int page, int size, String sort) {
        PageRequest pageable = buildPageRequest(page, size, sort);
        Page<Application> result = (status == null || status.isBlank())
                ? applicationRepository.findAllByOrderByCreatedAtDesc(pageable)
                : applicationRepository.findByStatusOrderByCreatedAtDesc(status.trim(), pageable);

        List<ApplicationResponse> content = result.getContent().stream()
                .map(app -> ApplicationResponse.from(app, reviewRepository.existsByApplicationId(app.getId())))
                .toList();

        return new PagedResponse<>(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages());
    }

    @Transactional
    public ApplicationResponse updateStatus(Long id, String status) {
        if (!ApplicationStatus.isValid(status)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Недопустимый статус");
        }
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Заявка не найдена"));
        application.setStatus(status);
        Application saved = applicationRepository.save(application);
        return ApplicationResponse.from(saved, reviewRepository.existsByApplicationId(saved.getId()));
    }

    private PageRequest buildPageRequest(int page, int size, String sort) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);
        Sort sorting = Sort.by(Sort.Direction.DESC, "createdAt");
        if ("startDate".equalsIgnoreCase(sort)) {
            sorting = Sort.by(Sort.Direction.ASC, "startDate");
        } else if ("status".equalsIgnoreCase(sort)) {
            sorting = Sort.by(Sort.Direction.ASC, "status");
        }
        return PageRequest.of(safePage, safeSize, sorting);
    }

    private User currentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal principal)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Требуется авторизация");
        }
        return principal.getUser();
    }
}
