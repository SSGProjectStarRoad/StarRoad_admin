package com.ssg.starroadadmin.reward.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum RewardSortType {
    NAME_ASC("이름순", Sort.Direction.ASC),
    NAME_DESC("이름 역순", Sort.Direction.DESC),
    CREATED_AT_ASC("오래된순", Sort.Direction.ASC),
    CREATED_AT_DESC("새로운순", Sort.Direction.DESC);

    private final String field;
    private final Sort.Direction direction;

    RewardSortType(String field, Sort.Direction direction) {
        this.field = field;
        this.direction = direction;
    }
}
