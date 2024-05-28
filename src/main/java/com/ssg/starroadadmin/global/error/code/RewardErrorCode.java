package com.ssg.starroadadmin.global.error.code;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RewardErrorCode {
    REWARD_NOT_FOUND("해당 리워드는 존재하지 않습니다."),
    REWARD_HISTORY_NOT_FOUND("해당 리워드 히스토리는 존재하지 않습니다.");

    private final String description;

    public String getDescription() {
        return description;
    }
}
