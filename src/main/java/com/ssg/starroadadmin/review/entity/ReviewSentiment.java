package com.ssg.starroadadmin.review.entity;

import com.ssg.starroadadmin.review.enums.ConfidenceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ReviewSentiment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    private String content;
    private int totalOffset;
    private int totalLength;

    @Enumerated(EnumType.STRING)
    private ConfidenceType confidence;

    private int highlightOffset;
    private int highlightLength;
}
