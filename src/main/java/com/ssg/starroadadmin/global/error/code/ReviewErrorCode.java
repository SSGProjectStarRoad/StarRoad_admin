package com.ssg.starroadadmin.global.error.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReviewErrorCode {
    REVIEW_NOT_FOUND("리뷰가 존재하지 않습니다."),
    REVIEW_ALREADY_DELETED("이미 삭제된 리뷰입니다."),
    REVIEW_ALREADY_REPORTED("이미 신고된 리뷰입니다."),
    REVIEW_NOT_REPORTED("신고되지 않은 리뷰입니다."),
    REVIEW_NOT_DELETED("삭제되지 않은 리뷰입니다."),
    REVIEW_NOT_WRITTEN_BY_USER("해당 리뷰는 사용자가 작성한 리뷰가 아닙니다."),
    REVIEW_NOT_WRITTEN_BY_ADMIN("해당 리뷰는 관리자가 작성한 리뷰가 아닙니다."),
    REVIEW_NOT_WRITTEN_BY_USER_OR_ADMIN("해당 리뷰는 사용자 또는 관리자가 작성한 리뷰가 아닙니다."),
    REVIEW_NOT_WRITTEN_BY_USER_OR_ADMIN_OR_NOT_FOUND("해당 리뷰는 사용자 또는 관리자가 작성한 리뷰가 아니거나 존재하지 않습니다."),
    REVIEW_NOT_WRITTEN_BY_USER_OR_NOT_FOUND("해당 리뷰는 사용자가 작성한 리뷰가 아니거나 존재하지 않습니다."),
    REVIEW_NOT_WRITTEN_BY_ADMIN_OR_NOT_FOUND("해당 리뷰는 관리자가 작성한 리뷰가 아니거나 존재하지 않습니다."),
    REVIEW_NOT_WRITTEN_BY_USER_OR_ADMIN_OR_ALREADY_DELETED("해당 리뷰는 사용자 또는 관리자가 작성한 리뷰가 아니거나 이미 삭제된 리뷰입니다."),
    REVIEW_NOT_WRITTEN_BY_USER_OR_ALREADY_DELETED("해당 리뷰는 사용자가 작성한 리뷰가 아니거나 이미 삭제된 리뷰입니다."),
    REVIEW_NOT_WRITTEN_BY_ADMIN_OR_ALREADY_DELETED("해당 리뷰는 관리자가 작성한 리뷰가 아니거나 이미 삭제된 리뷰입니다."),
    REVIEW_NOT_WRITTEN_BY_USER_OR_ADMIN_OR_NOT_FOUND_OR_ALREADY_DELETED("해당 리뷰는 사용자 또는 관리자가 작성한 리뷰가 아니거나 존재하지 않거나 이미 삭제된 리뷰입니다."),
    REVIEW_NOT_WRITTEN_BY_USER_OR_NOT_FOUND_OR_ALREADY_DELETED("해당 리뷰는 사용자가 작성한 리뷰가 아니거나 존재하지 않거나 이미 삭제된 리뷰입니다."),
    ACCESS_DENIED("해당 리뷰에 접근할 권한이 없습니다."),
    REVIEW_IMAGE_NOT_FOUND("해당 리뷰 이미지는 존재하지 않습니다."),
    REVIEW_FEEDBACK_NOT_FOUND("해당 리뷰 피드백은 존재하지 않습니다."),
    REVIEW_SENTIMENT_NOT_FOUND("해당 리뷰 감정분석은 존재하지 않습니다.");
    private final String description;
}
