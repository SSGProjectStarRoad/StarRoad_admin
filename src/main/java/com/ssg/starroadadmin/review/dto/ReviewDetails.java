package com.ssg.starroadadmin.review.dto;

import java.util.List;

public record ReviewDetails(
        List<String> images,
        List<String> feedbacks) {
}