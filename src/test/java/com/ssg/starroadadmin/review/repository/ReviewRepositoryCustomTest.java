package com.ssg.starroadadmin.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.global.dto.BetweenDate;
import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.dto.ReviewListWithOutImagesAndFeedbacksResponse;
import com.ssg.starroadadmin.review.enums.ConfidenceType;
import com.ssg.starroadadmin.review.enums.ReviewSortType;
import com.ssg.starroadadmin.shop.enums.Floor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.ssg.starroadadmin.review.entity.QReview.review;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Review Repository: QUERYDSL")
@SpringBootTest
class ReviewRepositoryCustomTest {

    @Autowired
    private ReviewRepositoryCustom reviewRepositoryCustom;

    @DisplayName("매장별 리뷰 리스트 조회")
    @Test
    public void givenStoreIdAndBetweenDate_whenFindAllByStoreIdAndBetweenDate_thenSuccess() {
        // Given
        Long storeId = 1L;
        BetweenDate betweenDate = BetweenDate.builder()
                .startDate(LocalDate.of(2024, 5, 1))
                .endDate(LocalDate.now())
                .build();
        ReviewSortType reviewSortType = ReviewSortType.CREATED_AT_ASC;
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<ReviewListResponse> reviewListResponses = reviewRepositoryCustom.findAllByStoreIdAndBetweenDate(storeId, betweenDate, reviewSortType, pageable);

        // Then
        assertNotNull(reviewListResponses);
        List<ReviewListResponse> reviewListResponseList = reviewListResponses.getContent();

        System.out.println("=====================================");
        for (ReviewListResponse response : reviewListResponseList) {
            System.out.println("response = " + response.reviewId() + ", " + response.createdAt() + response);
        }
        //response = ReviewListResponse[mallId=1, mallName=스타필드 하남, storeId=1, storeName=자라, floor=SECOND, storeImagePath=/images/store1.jpg, reviewId=1, visible=true, likeCount=12, contents=이 옷 정말 마음에 듭니다. 색상도 예쁘고 편하게 입을 수 있어요. 다음에 또 구매하고 싶네요., confidence=POSITIVE, reviewImagePaths=[], reviewFeedbackSelection=[시설 및 청결도가 만족스러웠습니다, 매장이 넓어요, 스타일 추천을 잘해줘요, 품질이 좋아요], userId=1, userName=이한강, userImagePath=https://kr.object.ncloudstorage.com/ssg-starroad/ssg/user/profile/faca60c6-69d4-4f87-9dd2-611d3f8783fe_4YSL4YW14YSS4YWh4Yar4YSA4YWh4Ya8IOGEkuGFp+GGvOGEguGFteGGtw==.jpg]
        System.out.println("=====================================");

        // 쇼핑몰
        assertThat(reviewListResponseList.get(0).mallId()).isEqualTo(1L);
        assertThat(reviewListResponseList.get(0).mallName()).isEqualTo("스타필드 하남");
        // 매장
        assertThat(reviewListResponseList.get(0).storeId()).isEqualTo(1L);
        assertThat(reviewListResponseList.get(0).storeName()).isEqualTo("자라_더미");
        assertThat(reviewListResponseList.get(0).floor()).isEqualTo(Floor.SECOND);
        assertThat(reviewListResponseList.get(0).storeImagePath()).isEqualTo("/images/store1.jpg");
        // 리뷰
        assertThat(reviewListResponseList.get(0).reviewId()).isEqualTo(1L);
        assertThat(reviewListResponseList.get(0).visible()).isTrue();
        assertThat(reviewListResponseList.get(0).likeCount()).isEqualTo(12);
        assertThat(reviewListResponseList.get(0).contents()).isEqualTo("이 옷 정말 마음에 듭니다. 색상도 예쁘고 편하게 입을 수 있어요. 다음에 또 구매하고 싶네요.");
        assertThat(reviewListResponseList.get(0).confidence()).isEqualTo(ConfidenceType.POSITIVE);
        // 리뷰 이미지
        assertThat(reviewListResponseList.get(0).reviewImagePaths()).isEmpty();
        // 리뷰 피드백
        assertThat(reviewListResponseList.get(0).reviewFeedbackSelection()).contains("시설 및 청결도가 만족스러웠습니다", "매장이 넓어요", "스타일 추천을 잘해줘요", "품질이 좋아요");
        // 유저
        assertThat(reviewListResponseList.get(0).userId()).isEqualTo(1L);
        assertThat(reviewListResponseList.get(0).userName()).isEqualTo("이한강");
        assertThat(reviewListResponseList.get(0).userImagePath()).isEqualTo("https://kr.object.ncloudstorage.com/ssg-starroad/ssg/user/profile/faca60c6-69d4-4f87-9dd2-611d3f8783fe_4YSL4YW14YSS4YWh4Yar4YSA4YWh4Ya8IOGEkuGFp+GGvOGEguGFteGGtw==.jpg");
    }

    @DisplayName("사용자별 리뷰 리스트 조회")
    @Test
    public void givenUserIdAndBetweenDate_whenFindAllByUserIdAndBetweenDate_thenSuccess() {

        // Given
        Long userId = 1L;
        BetweenDate betweenDate = BetweenDate.builder()
                .startDate(LocalDate.of(2024, 5, 1))
                .endDate(LocalDate.now())
                .build();
        ReviewSortType reviewSortType = ReviewSortType.CREATED_AT_ASC;
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<ReviewListResponse> reviewListResponses = reviewRepositoryCustom.findAllByUserIdAndBetweenDate(userId, betweenDate, reviewSortType, pageable);

        // Then
        assertNotNull(reviewListResponses);
        List<ReviewListResponse> reviewListResponseList = reviewListResponses.getContent();

        System.out.println("=====================================");
        for (ReviewListResponse response : reviewListResponseList) {
            System.out.println("response = " + response.reviewId() + ", " + response.createdAt() + response);
        }
        //response = ReviewListResponse[mallId=1, mallName=스타필드 하남, storeId=1, storeName=자라, floor=SECOND, storeImagePath=/images/store1.jpg, reviewId=1, visible=true, likeCount=12, contents=이 옷 정말 마음에 듭니다. 색상도 예쁘고 편하게 입을 수 있어요. 다음에 또 구매하고 싶네요., confidence=POSITIVE, reviewImagePaths=[], reviewFeedbackSelection=[시설 및 청결도가 만족스러웠습니다, 매장이 넓어요, 스타일 추천을 잘해줘요, 품질이 좋아요], userId=1, userName=이한강, userImagePath=https://kr.object.ncloudstorage.com/ssg-starroad/ssg/user/profile/faca60c6-69d4-4f87-9dd2-611d3f8783fe_4YSL4YW14YSS4YWh4Yar4YSA4YWh4Ya8IOGEkuGFp+GGvOGEguGFteGGtw==.jpg]
        System.out.println("=====================================");

        // 쇼핑몰
        assertThat(reviewListResponseList.get(0).mallId()).isEqualTo(1L);
        assertThat(reviewListResponseList.get(0).mallName()).isEqualTo("스타필드 하남");
        // 매장
        assertThat(reviewListResponseList.get(0).storeId()).isEqualTo(1L);
        assertThat(reviewListResponseList.get(0).storeName()).isEqualTo("자라_더미");
        assertThat(reviewListResponseList.get(0).floor()).isEqualTo(Floor.SECOND);
        assertThat(reviewListResponseList.get(0).storeImagePath()).isEqualTo("/images/store1.jpg");
        // 리뷰
        assertThat(reviewListResponseList.get(0).reviewId()).isEqualTo(1L);
        assertThat(reviewListResponseList.get(0).visible()).isTrue();
        assertThat(reviewListResponseList.get(0).likeCount()).isEqualTo(12);
        assertThat(reviewListResponseList.get(0).contents()).isEqualTo("이 옷 정말 마음에 듭니다. 색상도 예쁘고 편하게 입을 수 있어요. 다음에 또 구매하고 싶네요.");
        assertThat(reviewListResponseList.get(0).confidence()).isEqualTo(ConfidenceType.POSITIVE);
        // 리뷰 이미지
        assertThat(reviewListResponseList.get(0).reviewImagePaths()).isEmpty();
        // 리뷰 피드백
        assertThat(reviewListResponseList.get(0).reviewFeedbackSelection()).contains("시설 및 청결도가 만족스러웠습니다", "매장이 넓어요", "스타일 추천을 잘해줘요", "품질이 좋아요");
        // 유저
        assertThat(reviewListResponseList.get(0).userId()).isEqualTo(1L);
        assertThat(reviewListResponseList.get(0).userName()).isEqualTo("이한강");
        assertThat(reviewListResponseList.get(0).userImagePath()).isEqualTo("https://kr.object.ncloudstorage.com/ssg-starroad/ssg/user/profile/faca60c6-69d4-4f87-9dd2-611d3f8783fe_4YSL4YW14YSS4YWh4Yar4YSA4YWh4Ya8IOGEkuGFp+GGvOGEguGFteGGtw==.jpg");
    }
}