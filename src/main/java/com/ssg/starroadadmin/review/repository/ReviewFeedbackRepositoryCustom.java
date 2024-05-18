package com.ssg.starroadadmin.review.repository;

import com.ssg.starroadadmin.review.entity.ReviewFeedback;
import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewFeedbackRepositoryCustom {

    List<StoreFeedbackResponse> getStoreFeedback(Long storeId);
}
